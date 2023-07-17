package test.hr;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class HrDAO {
	
	@Autowired
	@Qualifier("hr")
	SqlSession sql;
	
	public void list() {
		sql.selectList("hr.list");
	}
}
