package com.mdip.kafka.logic.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import com.mdip.common.util.FileUtil;
import com.mdip.kafka.common.KafkaUtil;

/**
 * kafka 消费者类
 * 
 * @author alvinsun
 *
 */
public class MyKafkaConsumer {
	private Log log = LogFactory.getLog(this.getClass());
	private KafkaUtil kafkaUtil = KafkaUtil.getInstance();

	/**
	 * 获取KafKaConsumer
	 * 
	 * @return
	 */
	public KafkaConsumer<String, String> getKafkaConsumer() {
		Properties props = kafkaUtil.getProp4Consumer();// 消费者properties相关设置
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		log.info("Create Kafka consumer: " + consumer.toString());
		return consumer;
	}

	/**
	 * 消费消息
	 * 
	 * @param consumer
	 *            消费者
	 * @param topic
	 *            topic名字
	 * @param number
	 *            每次取多少条信息
	 */
	public void consumeMessages(String topic, int number) {
		KafkaConsumer<String, String> consumer = getKafkaConsumer();
		consumer.subscribe(Arrays.asList(topic));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(number);
			System.out.println(records.count());
			for (ConsumerRecord<String, String> record : records) {
				// TODO 具体逻辑具体实现
				System.err.println(record.offset() + "," + record.key() + "," + record.value() + "\n");
				FileUtil.writeFile("D:/file.txt", record.offset() + "," + record.key() + "," + record.value() + "\n",true);
			}
		}
	}

	/**
	 * 手动批量提交偏移量消费消息
	 * 
	 * @param topic
	 *            topic名字
	 * @param minBatchSize
	 *            批量提交数量
	 * @param number
	 *            每次取多少条信息
	 * @param consumer
	 */
	public void manualOffsetConsumeMessage(String topic, int minBatchSize, int number) {
		KafkaConsumer<String, String> consumer = getKafkaConsumer();
		consumer.subscribe(Arrays.asList(topic));
		List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(number);
			for (ConsumerRecord<String, String> record : records) {
				log.info("message values is " + record.value() + ",the offset is " + record.offset());
				buffer.add(record);
			}
			if (buffer.size() >= minBatchSize) {
				log.info("now commit offset");
				consumer.commitSync();
				buffer.clear();
			}
		}
	}

	/**
	 * 消费完一个分区后手动提交偏移量消费消息
	 * 
	 * @param consumer
	 */
	public void manualCommitPartionConsumeMessage(String topic) {
		KafkaConsumer<String, String> consumer = getKafkaConsumer();
		consumer.subscribe(Arrays.asList(topic));
		while (true) {
			ConsumerRecords<String, String> consumerRecords = consumer.poll(Long.MAX_VALUE);
			for (TopicPartition topicPartition : consumerRecords.partitions()) {
				List<ConsumerRecord<String, String>> partitionRecordList = consumerRecords.records(topicPartition);
				for (ConsumerRecord<String, String> record : partitionRecordList) {
					log.info("message  offset is :" + record.offset() + ",value is :" + record.value());
				}
				long lastOffset = partitionRecordList.get(partitionRecordList.size() - 1).offset();
				log.info("now commit the partition[ " + topicPartition.partition() + "] offset");
				consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(lastOffset + 1)));
			}
		}
	}

	/**
	 * 指定消费某个分区的消息
	 * 
	 * @param consumer
	 */
	public void consumeMessagesOfPartions(String topic, int partition) {
		KafkaConsumer<String, String> consumer = getKafkaConsumer();
		TopicPartition partition0 = new TopicPartition(topic, partition);
		TopicPartition partition1 = new TopicPartition(topic, partition + 1);
		consumer.assign(Arrays.asList(partition0, partition1));
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
			for (ConsumerRecord<String, String> record : records) {
				log.info("offset:" + record.offset() + ",key" + record.key() + ",value" + record.value());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
