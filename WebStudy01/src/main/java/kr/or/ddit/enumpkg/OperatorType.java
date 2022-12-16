package kr.or.ddit.enumpkg;

import kr.or.ddit.servlet04.RealOperator;

public enum OperatorType {
	// OperatorType의 객체다 enum앞에 class가 붙어있다고 생각하면 된다.
	// 차이점은 외부에 생성자를 사용할 수 없다.
	// name이라는 프로퍼티가 숨어져 있다.
	// 상수에는 똑같은 name값이 들어가있다.
	// PLUS, MINUS, MULTIPLY, DIVIDE;
	// 아래처럼 생성자를 생성하면 이렇게 생성할 때 아규먼트를 넣을 수 있다.
	PLUS('+', (l, r)->{return l+r;}),
	MINUS('-', (l, r)->l-r), 
	MULTIPLY('*', (l, r)->l*r), 
	DIVIDE('/', (l, r)->l/r);
	
	private char sign;
	private RealOperator realOperator;

	// 기본 생성자가 없어졌다. 아래 생성자를 생성하니깐..
	private OperatorType(char sign, RealOperator realOperator) {
		this.sign = sign;
		this.realOperator = realOperator;
	}
	
	public char getSign() {
		return sign;
	}
	
	public int operate(int leftOp, int rightOp) {
		return realOperator.operater(leftOp, rightOp);
	}
	
	public String getExpression(int leftOp, int rightOp) {
		// 2 + 2 = 4
		return String.format("%d %s %d = %d", leftOp, sign, rightOp, realOperator.operater(leftOp, rightOp));
	}
	
}
