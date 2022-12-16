package kr.or.ddit.servlet04;
// functional 인터페이스 이런 경우 람다식으로 가능

// 아래처럼 어노테이션이 있어야지 람다식 가능
@FunctionalInterface
public interface RealOperator {
	public int operater(int leftOp, int rightOp);
}
