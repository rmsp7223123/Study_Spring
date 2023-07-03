package com.hanul.middle;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class TestDAO {

	@Autowired
	@Qualifier("hanul")
	SqlSession sql;

	public void select() {
		int result = sql.selectOne("test.dual");
		System.out.println(result);
	}
}
