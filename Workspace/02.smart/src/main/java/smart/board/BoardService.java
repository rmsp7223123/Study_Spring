package smart.board;

import java.util.List;

import smart.common.PageVO;

public interface BoardService {
	int board_register(BoardVO vo);// 신규 방명록 글 저장

	PageVO board_list(PageVO page);// 방명록 목록 조회(페이지처리)

	BoardVO board_info(int id);// 방명록 글 안내 조회

	int board_update(BoardVO vo);// 방명록 변경 저장

	int board_read(int id);// 조회수 증가 처리

	int board_delete(int id);// 방명록 글 삭제

	FileVO board_file_info(int id);// 선택한 파일 정보 조회
	List<FileVO> board_file_removed(String removed); // 삭제할 파일들 정보 조회
}
