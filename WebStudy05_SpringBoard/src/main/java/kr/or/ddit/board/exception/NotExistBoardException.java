package kr.or.ddit.board.exception;

//언체크드로 만들고 시작
public class NotExistBoardException extends RuntimeException{

	public NotExistBoardException(int boNo) {
		super(String.format("%d 번의 글은 존재하지 않습니다.", boNo));
	} 
	
}
