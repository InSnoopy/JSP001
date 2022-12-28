package kr.or.ddit.object;

public class Parent {
	private String code="default"; //
	
	public void method() {
		System.out.println("부모메서드");
	}
	
	public void template() {
		method(); // 앞에 this가 생략되어 있다. this는 인스턴스를 가리킨다.
	}
	
	//두개의 객체를 비교할때 주소가 아닌, 객체가 가지고 있는 code를 통해서 비교하겠다. 

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parent other = (Parent) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
	

}
