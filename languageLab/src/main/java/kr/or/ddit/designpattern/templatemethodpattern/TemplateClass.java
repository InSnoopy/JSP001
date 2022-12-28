package kr.or.ddit.designpattern.templatemethodpattern;

public abstract class TemplateClass {
//	template method
	public final void template() {
		// 이 순서가 바뀌지 않는다. 이를 템플릿 메소드라고 한다.
		// 순서를 결정 (템플릿 메소드)
		stepOne();
		stepTwo();
		stepThree();
	}
//	hook method
	private void stepOne() {
		System.out.println("1단계");
	}
	protected abstract void stepTwo();	
	private void stepThree() {
		System.out.println("3단계");
	}
}
