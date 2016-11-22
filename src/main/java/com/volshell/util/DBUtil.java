package com.volshell.util;

import lombok.Setter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author volshell
 * @version 1.0
 * @date 2016年4月22日
 * 
 *       使用c3p0连接池，获取connection对象
 */
public class DBUtil {
	public static Connection connection = null;
	@Setter
	private static DataSource dataSource = null;

	/**
	 * @return
	 * 
	 * 		获取Connection
	 */
	public static Connection getConnection() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-hibernate.xml");
		dataSource = (DataSource) context.getBean("dataSource");
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void createTable(Connection connection) {
		Statement statement = null;

		String clean = "drop table if exists wechat";
		try {
			statement = connection.createStatement();
			statement.execute("use crawler");
			statement.executeUpdate(clean);
			clean = "drop table if exists category";
			statement.executeUpdate(clean);

			String sql = " create table category ( id int not null auto_increment,c_id int not null,c_desc varchar(255), primary key (id))engine=innodb default charset=utf8 auto_increment=1";
			statement.executeUpdate(sql);
			sql = " create table wechat ( w_id integer not null auto_increment,c_id int not null,w_code varchar(255),w_name varchar(255),w_png varchar(255), text_long text, text_short text,primary key (w_id))engine=innodb default charset=utf8 auto_increment=1";
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void write(File file, String content) {
		FileWriter fileWriter = null;
		BufferedWriter buffer = null;
		try {
			fileWriter = new FileWriter(file, true);
			buffer = new BufferedWriter(fileWriter);
			buffer.write(content);
			buffer.newLine();
			buffer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
