package smart.hr;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class HrDAO implements HrService {
	@Autowired
	@Qualifier("hr")
	private SqlSession sql;

	@Override
	public void employee_insert() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EmployeeVO> employee_list() {
		// TODO Auto-generated method stub
		return sql.selectList("hr.list");
	}

	@Override
	public EmployeeVO employee_info(int employee_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int employee_update(EmployeeVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int employee_delete(int employee_id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
