package smart.notice;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

// @Component를 구분 - 저장소(DB) : Repository, 서비스 : Service, 컨트롤러 : Controller
// 화면연결응답이 아니라 이 처리 자체가 응답 : @ResponseBody : ajax 통신요청 결과
// 요청 URL에 파라미터로 데이터를 전달 : ajax 통신시 json 데이터를 파라미터로 전달시 @RequestBody
@Repository
public class NoticeDAO implements NoticeService {
	@Autowired
	@Qualifier("hanul")
	private SqlSession sql;

	@Override
	public int notice_regist(NoticeVO vo) {
		return sql.insert("notice.insert", vo);
	}

	@Override
	public List<NoticeVO> notice_list() {
		return sql.selectList("notice.list");
	}

	@Override
	public NoticeVO notice_info(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int notice_update(NoticeVO vo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int notice_delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
