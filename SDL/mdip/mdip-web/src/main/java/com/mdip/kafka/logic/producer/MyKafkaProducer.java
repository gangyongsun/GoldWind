package com.mdip.kafka.logic.producer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.mdip.kafka.common.KafkaUtil;

/**
 * Kafka生产者类
 * 
 * @author alvinsun
 *
 */
public class MyKafkaProducer {
	private Log log = LogFactory.getLog(this.getClass());
	private KafkaUtil kafkaUtil = KafkaUtil.getInstance();

	/**
	 * 获取Producer
	 * 
	 * @return producer对象
	 */
	private Producer<String, String> getProducer() {
		Properties props = kafkaUtil.getProp4Producer();// 发送者相关properties设置
		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		log.info("Create Kafka producer: " + producer.toString());
		return producer;
	}

	/**
	 * 用于producer发送的ProducerRecord对象
	 * 
	 * @param topic
	 *            topic名字
	 * @param key
	 *            消息key
	 * @param message
	 *            消息内容
	 * @return ProducerRecord对象
	 */
	public ProducerRecord<String, String> createRecord(String topic, String key, String message) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, message);
		return record;
	}

	/**
	 * 用于producer发送的ProducerRecord对象
	 * 
	 * @param topic
	 *            topic名字
	 * @param message
	 *            消息内容
	 * @return ProducerRecord对象
	 */
	public ProducerRecord<String, String> createRecord(String topic, String message) {
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, message);
		return record;
	}

	/**
	 * 发送多条消息到topic
	 * 
	 * @param recordList
	 *            消息记录
	 */
	public void ProduceRecordList(List<ProducerRecord<String, String>> recordList) {
		Producer<String, String> producer = getProducer();
		for (ProducerRecord<String, String> record : recordList) {
			producer.send(record);
		}
		kafkaUtil.closeProducer(producer);
	}

	/**
	 * 发送一条消息到topic
	 * 
	 * @param record
	 *            消息记录
	 */
	public void ProduceRecord(ProducerRecord<String, String> record) {
		Producer<String, String> producer = getProducer();
		producer.send(record);
		kafkaUtil.closeProducer(producer);
	}

	/**
	 * 从文件读取内容发送到topic
	 * 
	 * @param producer
	 *            生产者
	 * @param topic
	 *            目的topic
	 * @param filename
	 *            文件名
	 */
	public void produceMessageFromFile(String topic, String filename) {
		FileReader fr = null;
		BufferedReader br = null;
		Producer<String, String> producer = null;
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				ProducerRecord<String, String> record = createRecord(topic, line);
				producer = getProducer();
				producer.send(record);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				kafkaUtil.closeProducer(producer);
				if (br != null) {
					br.close();
					br = null;
				}
				if (fr != null) {
					fr.close();
					fr = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
