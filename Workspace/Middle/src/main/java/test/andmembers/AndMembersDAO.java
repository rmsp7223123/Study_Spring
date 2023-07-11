package test.andmembers;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AndMembersDAO {
	@Autowired
	@Qualifier("hanul")
	private SqlSession sql;
	
	public AndMembersVO and_info(AndMembersVO vo) {
//		1. AndMemberVO(Object) : VO 내부에는 key값(변수이름)을 가지고 값을 여러개 할당 할 수 있음
//								 파라미터가 여러개라면 vo구조로 묶어서 SQL.SELECT에 파라미터로 전송
//		2. HashMap<String, Object> : HaspMap은 key와 Value를 가지는 가장 vo에 가까운 객체
//									파라미터로 보낼 정보가 vo타입과 맞지 않는다면 hashmap을 이용하면 됨
		return sql.selectOne("and.info", vo);
	}
	
	public AndMembersVO and_info2(HashMap<String, String> params) {
		return sql.selectOne("and.info", params);
	}
}
