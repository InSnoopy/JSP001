package kr.or.ddit.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 페이징과 관련된 모든 데이터를 가진 객체
 *
 */
@Getter
@NoArgsConstructor
@ToString
public class PagingVO<T> {
	
	public PagingVO(int screenSize, int blockSize) {
		super();
		this.screenSize = screenSize;
		this.blockSize = blockSize;
	}

	// 아래 3개는 서버에서 결정된다.
	private int totalRecord; // DB 조회
	private int screenSize=10; // 임의 설정
	private int blockSize=5; // 임의 설정
	
	private int currentPage; // 클라이언트 파라미터
	
	private int totalPage;	
	// 여기 밑에 4개는 currentPage가 결정되면 결정된다.
	private int startRow; 
	private int endRow;
	private int startPage;
	private int endPage;
	
	private List<T> dataList;
	
	// 단순 검색으로만 사용
	private SearchVO simpleCondition;
	
	public void setSimpleCondition(SearchVO simpleCondition) {
		this.simpleCondition = simpleCondition;
	}
	
	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
	
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		totalPage = (totalRecord + (screenSize - 1)) / screenSize;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		endRow = currentPage*screenSize;
		startRow = endRow - (screenSize - 1);
		endPage = ((currentPage + (blockSize-1)) / blockSize) * blockSize;
		startPage = endPage - (blockSize - 1);
	}
	
	private final String APATTERN = "<a class='paging' href='#' data-page='%d'>%s</a>";
	
	public String getPagingHTML() {
		StringBuffer html = new StringBuffer();
		if(startPage > blockSize) {
			html.append(
				String.format(APATTERN, startPage-blockSize, "이전")
			);
		}
		endPage = endPage > totalPage ? totalPage : endPage;
		for(int page=startPage; page<=endPage; page++) {
			if(page==currentPage) {
				html.append(
					"<a href='#'>"+page+"</a>"
				);
			}else {
				
				html.append(
					String.format(APATTERN, page, page+"")
				);
			}
		}
		if(endPage<totalPage) {
			html.append(
				String.format(APATTERN, endPage+1, "다음")
			);
		}
		return html.toString();
	}
}
