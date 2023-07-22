package member;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AndMemberDAO {
	@Autowired @Qualifier("hanul") SqlSession sql;
	public AndMemberVO login(HashMap<String, String> params) {
		// 1. AndMemberVO(Object) : VO내부에는 key값(변수이름)을 가지고 값을 여러개 할당해놓을수가있음. 파라메터가 여러개라면
		//							VO구조로 묶어서 sql.selectone에 파라메터로 전송한다.
		// 2. HashMap<String , Object> : HashMap은 key와Value를 가지는 가장 VO에 가까운 객체다.
		//								파라메터로 보낼 정보가 VO타입과 맞지 않다면 HashMap을 이용하면됨.
		
		AndMemberVO vo = sql.selectOne("member.login", params);
		return vo;
	}
	
}
