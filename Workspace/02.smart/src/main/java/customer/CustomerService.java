package customer;

import java.util.List;

public interface CustomerService {
//	DTO(Data Transfer Object), VO(Value Object)
//	crud(Create, Read, Update, Delete)
	void customer_insert(CustomerVO vo); // 신규 고객정보 저장

	List<CustomerVO> customer_list(); // 고객목록 조회

	CustomerVO customer_info(int id); // 고객 상세정보 조회

	void customer_update(CustomerVO vo);// 고객정보 수정후 저장

	void customer_delete(int id); // 고객정보 삭제
}
