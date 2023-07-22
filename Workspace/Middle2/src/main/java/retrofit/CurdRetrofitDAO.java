package retrofit;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CurdRetrofitDAO {
	@Autowired @Qualifier("hanul") SqlSession sql;
	
	public List<CrudRetrofitVO> select(){
		return sql.selectList("retrofit.select");
	}
	
	public int insert(CrudRetrofitVO vo){
		return sql.insert("retrofit.insert" , vo);
	}
	public int update(CrudRetrofitVO vo){
		return sql.update("retrofit.update" , vo);
	}
	public int delete(CrudRetrofitVO vo){
		return sql.delete("retrofit.delete" , vo);
	}
	
}
