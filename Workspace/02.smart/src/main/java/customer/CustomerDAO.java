package customer;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO implements CustomerService {

	private SqlSessionTemplate sql;

//	생성된 객체(빈으로 등록된 객체들)의 주소를 스프링 container에 관리가 됨

//	DI(Dependency Injection) 객체에 주소를 담아주는(주입) 처리
//	필드에 데이터를 담는 방법 2가지
	public CustomerDAO(SqlSessionTemplate sql) {
		this.sql = sql;
	}

	@Override
	public void customer_insert(CustomerVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CustomerVO> customer_list() {
		// TODO Auto-generated method stub
		return sql.selectList("customer.list");
	}

	@Override
	public CustomerVO customer_info(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void customer_update(CustomerVO vo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void customer_delete(int id) {
		// TODO Auto-generated method stub

	}

}
