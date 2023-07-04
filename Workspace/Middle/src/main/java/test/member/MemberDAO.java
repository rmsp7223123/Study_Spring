package test.member;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO implements MemberService {

	@Autowired
	SqlSession sql;

	@Override
	public MemberVO member_info(String user_id) {
		return sql.selectOne(user_id);
	}

}
