package kr.or.ddit.memo.dao;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.vo.MemoVO;

public class FileSystemMemoDAOImplTest {
	
	private MemoDAO dao = FileSystemMemoDAOImpl.getInstance();
	private MemoVO memo;
	
	
	@Before
	public void setUp() throws Exception {
		memo = new MemoVO();
		memo.setWriter("작성자1");
		memo.setContent("내용1");
		String date = String.format("%1$ty-%1$tm-%1$td %1$tH:%1$tM:%1$tS",LocalDateTime.now());
		memo.setDate(date);
	}

	@Test
	public void testSelectMemoList() {
		List<MemoVO> memoList =  dao.selectMemoList();
		// stream()을 사용하면 List안에 한건 한건 컨트롤 가능
		
// 		옛날 버전
//		memoList.stream()
//				.forEach(singleMemo->{
//					System.out.println(singleMemo);
//				});
		
		// Method 레퍼런스 : 요즘 버전
		memoList.stream()
				.forEach(System.out::println);
	}

//	@Test
	public void testInsert() {
		dao.insertMemo(memo);
	}

}
