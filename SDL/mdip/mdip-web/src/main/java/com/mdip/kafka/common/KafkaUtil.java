package com.mdip.kafka.common;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.protocol.ApiKeys;
import org.apache.kafka.common.requests.AbstractRequest;
import org.apache.kafka.common.requests.CreateTopicsRequest;
import org.apache.kafka.common.requests.CreateTopicsResponse;
import org.apache.kafka.common.requests.OffsetFetchRequest;
import org.apache.kafka.common.requests.OffsetFetchResponse;
import org.apache.kafka.common.requests.RequestHeader;
import org.apache.kafka.common.requests.ResponseHeader;

import com.mdip.common.util.PropertiesUtil;

/**
 * 多线程安全单例模式
 * 
 * @author alvinsun
 *
 */
public class KafkaUtil {
	
	private static final String KAFKA_CONFIG = "kafka.properties";
	private static final String bootstrapServers = PropertiesUtil.getValueByKey("bootstrap.servers",KAFKA_CONFIG);
	private static final String acks = PropertiesUtil.getValueByKey("acks",KAFKA_CONFIG);
	//private static final int retries = Integer.parseInt(PropertiesUtil.getValueByKey("retries",KAFKA_CONFIG));
	private static final int batchSize = Integer.parseInt(PropertiesUtil.getValueByKey("batch.size",KAFKA_CONFIG));
	private static final int lingerMs = Integer.parseInt(PropertiesUtil.getValueByKey("linger.ms",KAFKA_CONFIG));
	private static final long bufferMemory = Long.parseLong(PropertiesUtil.getValueByKey("buffer.memory",KAFKA_CONFIG));
	private static final String keySerializer = PropertiesUtil.getValueByKey("key.serializer",KAFKA_CONFIG);
	private static final String valueSerializer = PropertiesUtil.getValueByKey("value.serializer",KAFKA_CONFIG);
	private static final String keyDeserializer = PropertiesUtil.getValueByKey("key.deserializer",KAFKA_CONFIG);
	private static final String valueDeserializer = PropertiesUtil.getValueByKey("value.deserializer",KAFKA_CONFIG);
	private static final String autoOffsetReset = PropertiesUtil.getValueByKey("auto.offset.reset",KAFKA_CONFIG);
	private static final String sessionTimeoutMs = PropertiesUtil.getValueByKey("session.timeout.ms",KAFKA_CONFIG);
	private static final String enableAutoCommit = PropertiesUtil.getValueByKey("enable.auto.commit",KAFKA_CONFIG);
	private static final String autoCommitIntervalMs = PropertiesUtil.getValueByKey("auto.commit.interval.ms",KAFKA_CONFIG);
	private static final String groupId = PropertiesUtil.getValueByKey("group.id",KAFKA_CONFIG);

	/**
	 * 静态私有变量
	 * <p>
	 * 不初始化，不使用final关键字
	 * <p>
	 * 使用volatile保证了多线程访问时instance变量的可见性，避免了instance初始化时其他变量属性还没赋值完时，被另外线程调用
	 */
	private static volatile KafkaUtil instance;

	/**
	 * 定义一个私有构造方法
	 */
	private KafkaUtil() {
	}

	/**
	 * 定义一个共有的静态方法，返回该类型实例
	 * 
	 * @return
	 */
	public static KafkaUtil getInstance() {
		// 对象实例化时与否判断（不使用同步代码块，instance不等于null时，直接返回对象，提高运行效率）
		if (instance == null) {
			// 同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
			synchronized (KafkaUtil.class) {
				// 未初始化，则初始instance变量
				if (instance == null) {
					instance = new KafkaUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 发送创建、删除Topic等请求
	 * 
	 * @param host
	 *            目标broker的IP
	 * @param port
	 *            目标broker的端口
	 * @param request
	 *            请求对象
	 * @param apiKey
	 *            请求类型
	 * @return 序列化后的response
	 * @throws IOException
	 */
	public ByteBuffer send(String host, int port, AbstractRequest request, ApiKeys apiKey) throws IOException {
		Socket socket = connect(host, port);// 创建Socket连接
		try {
			return send(request, apiKey, socket);// 向给定socket发送请求
		} finally {
			if (socket != null) {
				socket.close();
				socket = null;
			}
		}
	}

	/**
	 * 发送序列化请求并等待response返回
	 * 
	 * @param socket
	 *            连向目标broker的socket
	 * @param request
	 *            序列化后的请求
	 * @return 序列化后的response
	 * @throws IOException
	 */
	private byte[] issueRequestAndWaitForResponse(Socket socket, byte[] request) throws IOException {
		sendRequest(socket, request);// 发送序列化请求给socket
		return getResponse(socket);// 从给定socket处获取response
	}

	/**
	 * 发送序列化请求给socket
	 * 
	 * @param socket
	 *            连向目标broker的socket
	 * @param request
	 *            序列化后的请求
	 * @throws IOException
	 */
	private void sendRequest(Socket socket, byte[] request) throws IOException {
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		dos.writeInt(request.length);
		dos.write(request);
		dos.flush();
		dos.close();
	}

	/**
	 * 从给定socket处获取response
	 * 
	 * @param socket
	 *            连向目标broker的socket
	 * @return 获取到的序列化后的response
	 * @throws IOException
	 */
	private byte[] getResponse(Socket socket) throws IOException {
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(socket.getInputStream());
			byte[] response = new byte[dis.readInt()];
			dis.readFully(response);
			return response;
		} finally {
			if (dis != null) {
				dis.close();
			}
		}
	}

	/**
	 * 创建Socket连接
	 * 
	 * @param hostName
	 *            目标broker主机名
	 * @param port
	 *            目标broker服务端口, 比如9092
	 * @return 创建的Socket连接
	 * @throws IOException
	 */
	private Socket connect(String hostName, int port) throws IOException {
		return new Socket(hostName, port);
	}

	/**
	 * 向给定socket发送创建、删除Topic等请求
	 * 
	 * @param abstractRequest
	 *            请求对象
	 * @param apiKey
	 *            请求类型, 即属于哪种请求
	 * @param socket
	 *            连向目标broker的socket
	 * @return 序列化后的response
	 * @throws IOException
	 */
	private ByteBuffer send(AbstractRequest abstractRequest, ApiKeys apiKey, Socket socket) throws IOException {
		RequestHeader requestHeader = new RequestHeader(apiKey.id, abstractRequest.version(), "client-id", 0);

		ByteBuffer byteBuffer = ByteBuffer.allocate(requestHeader.sizeOf() + abstractRequest.sizeOf());
		requestHeader.writeTo(byteBuffer);
		abstractRequest.writeTo(byteBuffer);

		byte[] serializedRequest = byteBuffer.array();
		byte[] response = issueRequestAndWaitForResponse(socket, serializedRequest);
		ByteBuffer responseBuffer = ByteBuffer.wrap(response);
		ResponseHeader.parse(responseBuffer);
		return responseBuffer;
	}

	/**
	 * 创建topic
	 * 
	 * @param topicName
	 *            topic名
	 * @param partitions
	 *            分区数
	 * @param replicationFactor
	 *            副本数
	 * @throws IOException
	 */
	public void createTopics(String topicName, int partitions, short replicationFactor) throws IOException {
		Map<String, CreateTopicsRequest.TopicDetails> topics = new HashMap<>();

		// 插入多个元素便可同时创建多个topic
		topics.put(topicName, new CreateTopicsRequest.TopicDetails(partitions, replicationFactor));

		int creationTimeoutMs = 60000;
		CreateTopicsRequest createTopicsRequest = new CreateTopicsRequest.Builder(topics, creationTimeoutMs).build();

		ByteBuffer response = send("localhost", 9092, createTopicsRequest, ApiKeys.CREATE_TOPICS);
		CreateTopicsResponse.parse(response, createTopicsRequest.version());
	}

	/**
	 * 获取某个consumer group下的某个topic分区的位移
	 * 
	 * @param groupID
	 *            group id
	 * @param topic
	 *            topic名
	 * @param parititon
	 *            分区号
	 * @throws IOException
	 */
	public void getOffsetForPartition(String groupID, String topic, int parititon) throws IOException {
		TopicPartition topicPartition = new TopicPartition(topic, parititon);
		OffsetFetchRequest offsetFetchRequest = new OffsetFetchRequest.Builder(groupID, singletonList(topicPartition))
				.setVersion((short) 2).build();
		ByteBuffer response = send("localhost", 9092, offsetFetchRequest, ApiKeys.OFFSET_FETCH);
		OffsetFetchResponse resp = OffsetFetchResponse.parse(response, offsetFetchRequest.version());
		OffsetFetchResponse.PartitionData partitionData = resp.responseData().get(topicPartition);
		System.out.println(partitionData.offset);
	}

	private List<TopicPartition> singletonList(TopicPartition topicPartition) {
		List<TopicPartition> list = new ArrayList<TopicPartition>();
		list.add(topicPartition);
		return list;
	}

	/**
	 * 获取某个consumer group下所有topic分区的位移信息
	 * 
	 * @param groupID
	 *            group id
	 * @return (topic分区 --> 分区信息)的map
	 * @throws IOException
	 */
	public Map<TopicPartition, OffsetFetchResponse.PartitionData> getAllOffsetsForGroup(String groupID)
			throws IOException {
		OffsetFetchRequest offsetFetchRequest = new OffsetFetchRequest.Builder(groupID, null).setVersion((short) 2)
				.build();
		ByteBuffer byteBuffer = send("localhost", 9092, offsetFetchRequest, ApiKeys.OFFSET_FETCH);
		OffsetFetchResponse offsetFetchResponse = OffsetFetchResponse.parse(byteBuffer, offsetFetchRequest.version());
		return offsetFetchResponse.responseData();
	}

	/**
	 * 设置producer properties对象
	 * 
	 * @return
	 */
	public Properties getProp4Producer() {
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServers);
		props.put("acks", acks);
		//props.put("retries", retries);
		props.put("batch.size", batchSize);
		props.put("linger.ms", lingerMs);
		props.put("buffer.memory", bufferMemory);
		props.put("key.serializer", keySerializer);
		props.put("value.serializer", valueSerializer);
		return props;
	}

	/**
	 * 设置consumer properties对象
	 * 
	 * @return
	 */
	public Properties getProp4Consumer() {
		Properties props = new Properties();
		//props.put("zookeeper.connect", zookeeperConnect);
		props.put("bootstrap.servers", bootstrapServers);
		props.put("group.id", groupId);
		//props.put("retries", retries);
		props.put("enable.auto.commit", enableAutoCommit);
		props.put("auto.commit.interval.ms", autoCommitIntervalMs);
		props.put("auto.offset.reset", autoOffsetReset);
		props.put("session.timeout.ms", sessionTimeoutMs);
		props.put("key.deserializer", keyDeserializer);
		props.put("value.deserializer", valueDeserializer);
		return props;
	}

	/**
	 * 关闭producer
	 * 
	 * @param producer
	 * @return
	 */
	public boolean closeProducer(Producer<?, ?> producer) {
		boolean flag = false;
		if (producer != null) {
			producer.close();
			producer = null;
			flag = true;
		}
		return flag;
	}
}
