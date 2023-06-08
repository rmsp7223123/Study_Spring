package com.my.app;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class DBTest {
	@Autowired
	private SqlSession sql;

	@Autowired
	private DataSource ds;

	@Test
	public void connect() throws Exception {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			System.out.println("연결성공 : " + conn);
		} catch (Exception e) {
			System.out.println("연결실패");
		} finally {
			conn.close();
		}
	}

	@Test
	public void today() {
		String today = sql.selectOne("test.today");
		System.out.println("오늘은 " + today);

	}
}
