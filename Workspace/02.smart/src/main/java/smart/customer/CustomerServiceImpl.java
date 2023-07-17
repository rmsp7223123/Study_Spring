package smart.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {
<<<<<<< HEAD:Workspace/02.smart/src/main/java/customer/CustomerServiceImpl.java

	@Autowired
	private CustomerDAO dao;

=======
	@Autowired private CustomerDAO dao;
	
>>>>>>> 9b815261d53cc4f2093b795c56c82240c9f7e960:Workspace/02.smart/src/main/java/smart/customer/CustomerServiceImpl.java
	@Override
	public void customer_insert(CustomerVO vo) {
		dao.customer_insert(vo);
	}

	@Override
	public List<CustomerVO> customer_list() {
<<<<<<< HEAD:Workspace/02.smart/src/main/java/customer/CustomerServiceImpl.java
		// TODO Auto-generated method stub
=======
>>>>>>> 9b815261d53cc4f2093b795c56c82240c9f7e960:Workspace/02.smart/src/main/java/smart/customer/CustomerServiceImpl.java
		return dao.customer_list();
	}

	@Override
	public CustomerVO customer_info(int id) {
		return dao.customer_info(id);
	}

	@Override
	public void customer_update(CustomerVO vo) {
		dao.customer_update(vo);
	}

	@Override
	public void customer_delete(int id) {
		dao.customer_delete(id);
	}

	@Override
	public List<CustomerVO> customer_list(String name) {
		return dao.customer_list(name);
	}

}
