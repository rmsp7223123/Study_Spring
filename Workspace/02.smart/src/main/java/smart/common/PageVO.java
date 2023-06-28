package smart.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageVO {
	private int totalList, totalPage, totalBlock, beginList, endList, curBlock, beginPage, endPage;
	private int pageList = 10;
	private int blockPage = 10;
	private int curPage = 1;
	private List<Object> list; // 현재페이지에서의 글목록

	public void setTotalList(int totalList) {
		this.totalList = totalList;
//		총 페이지수 : 8페이지 = 212 / 10 = 21 ...2 ==> 22P
		totalPage = totalList / pageList;
		if (totalList % pageList > 0) {
			++totalPage;
		}
//		총 블록수 : 3블록 = 22 / 10 = 2...2 ==>3B
		totalBlock = totalPage / blockPage;
		if (totalPage % blockPage > 0) {
			++totalBlock;
		}
		endList = totalList - (curPage - 1) * pageList;
		beginList = endList - (pageList - 1);

		// 블록번호 : 페이지번호 / 블록당 보여질 페이지 수
		curBlock = curPage / blockPage;
		if (curPage % blockPage > 0) {
			++curBlock;
		}
		endPage = curBlock * blockPage;
		beginPage = endPage - (blockPage - 1);

		if (totalPage < endPage) {
			endPage = totalPage;
		}
	}
}
