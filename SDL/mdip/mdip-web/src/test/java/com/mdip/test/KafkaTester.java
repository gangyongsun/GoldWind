package com.mdip.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import com.mdip.common.util.PropertiesUtil;
import com.mdip.kafka.logic.consumer.MyKafkaConsumer;
import com.mdip.kafka.logic.producer.MyKafkaProducer;

public class KafkaTester {
//	private static final String topicName = PropertiesUtil.getValueByKey("topic","kafka.properties");
//
//	@Test
//	public void testProducer() {
//		MyKafkaProducer kafkaProducer = new MyKafkaProducer();// 创建发送者管理对象
//		kafkaProducer.produceMessageFromFile(topicName, "c:/cityWeather_130104.data");
//	}
//	
//	@Test
//	public void testProducerA() {
//		MyKafkaProducer kafkaProducer = new MyKafkaProducer();// 创建发送者管理对象
//		ProducerRecord<String, String> record=kafkaProducer.createRecord(topicName, "helloWorld!"+System.currentTimeMillis());
//		kafkaProducer.ProduceRecord(record);
//	}
//	
//	@Test
//	public void testProducerB() {
//		MyKafkaProducer kafkaProducer = new MyKafkaProducer();// 创建发送者管理对象
//		List<ProducerRecord<String, String>> recordList=new ArrayList<ProducerRecord<String, String>>();
//		for (int i = 0; i < 300; i++) {
//			ProducerRecord<String, String> record=kafkaProducer.createRecord(topicName, i+"helloWorld!"+System.currentTimeMillis());
//			recordList.add(record);
//		}
//		kafkaProducer.ProduceRecordList(recordList);
//	}
//
//	@Test
//	public void testConsumer() {
//		MyKafkaConsumer kafkaConsumer = new MyKafkaConsumer();// 创建消费者管理对象
//		kafkaConsumer.consumeMessages(topicName, 100);
//	}

}
