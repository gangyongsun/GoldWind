package com.mdip.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.pentaho.di.job.Job;

public class MyTest {
//	private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//
//	@Test
//	public void testQueryAll() {
//		Connection connection = null;
//		Statement statement = null;
//		ResultSet resultSet = null;
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			connection = DriverManager.getConnection("jdbc:mysql://192.168.1.46:3306/mdip", "root", "root");
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery("select * from nankuang_product");
//			ResultSetMetaData md = resultSet.getMetaData(); // 获得结果集结构信息,元数据
//			int columnCount = md.getColumnCount(); // 获得列数
//			while (resultSet.next()) {
//				Map<String, Object> rowData = new HashMap<String, Object>();
//				for (int i = 1; i <= columnCount; i++) {
//					rowData.put(md.getColumnName(i), resultSet.getObject(i));
//					System.out.println(md.getColumnName(i) + "============" + resultSet.getObject(i));
//				}
//				System.out.println("...........................................");
//				list.add(rowData);
//			}
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testHashTable() {
//		Map<String, Job> jobMap = new Hashtable<String, Job>();// 作业id,作业map
//		Job job = jobMap.get("123");
//		if (job != null) {
//			System.out.println(job.getId());
//		}
//	}

}
