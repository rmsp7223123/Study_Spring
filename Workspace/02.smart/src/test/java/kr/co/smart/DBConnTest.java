package kr.co.smart;

import java.sql.Connection;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import smart.member.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class DBConnTest {
	Scanner sc = new Scanner(System.in);
	private BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
	MemberVO vo = new MemberVO();
//	@Autowired
//	private DataSource ds;

	@Autowired
	@Qualifier("hanul")
	private SqlSession sql;

	@Test
	public void join() {
//		키보드로 입력받아야 할 항목 : 이름, 아이디, 비밀번호, 이메일
		System.out.println("이름을 입력해주세요.");
		vo.setName(sc.next());
		System.out.println("아이디를 입력해주세요.");
		vo.setUserid(sc.next());
		System.out.println("비밀번호를 입력해주세요.");
		vo.setUserpw(pe.encode(sc.next()));
		System.out.println("이메일을 입력해주세요.");
		vo.setEmail(sc.next());

//		DML(Date Manipulation Language) : select, insert, update, delete
//		Query Language : select
//		DML : insert, update, delete

		int dml = sql.insert("member.joinTest", vo);

		System.out.println(dml == 1 ? "가입성공" : "가입실패");

		sc.close();
	}

	@Test
	public void login() {
		System.out.println("로그인 할 아이디를 입력해주세요.");
		String userid = sc.next();
		System.out.println("로그인 할 비밀번호를 입력해주세요.");
		String userpw = sc.next();

		vo = sql.selectOne("member.loginTest", userid);
		if (vo == null) {
			System.out.println("아이디가 없음");
		} else {
			boolean match = pe.matches(userpw, vo.getUserpw());
			if (match) {
				System.out.println(vo.getName() + " 로그인 되었습니다.");
			} else {
				System.out.println("비밀번호가 틀렸습니다.");
			}
		}
		sc.close();
	}

//	@Test
//	public void connect() throws Exception {
//		Connection conn = null;
//		try {
//			conn = ds.getConnection();
//			System.out.println("DB 연결 성공: " + conn);
//		} catch (Exception e) {
//			System.out.println("DB 연결 실패");
//		} finally {
//			conn.close();
//			System.out.println();
//		}
//	}

}