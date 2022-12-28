package kr.or.ddit.object;

import org.openjdk.jol.vm.VM;
import org.openjdk.jol.vm.VirtualMachine;

public class ObjectPlayground {
	public static void main(String[] args) throws Exception {
		
		String qualifiedName = "kr.or.ddit.object.Parent";
		//클래스(상수메모리)와 인스턴스는 적재되는 메모리가 다름 
		//같은 공간에 적재되는 하나의 클래스임 t1와 t2주소가 같음
		Class<?> type1 = Parent.class; //
		Class<?> type2 = Class.forName(qualifiedName); //t1와 t2는 같다
		VirtualMachine vm = VM.current();
		
		System.out.printf("type1 주소 : %d \n",vm.addressOf(type1)); 
		System.out.printf("type2 주소 : %d \n",vm.addressOf(type2)); 
		
		
		//2개의 상태를 코드로 비교함. 결과값은 트루임 
		//자바빈 5번. 상태를 비교할 수 있는 ???
		Parent parent1 = new Parent();
		Object parent2 = type1.newInstance();
		System.out.printf("parent1 주소 : %d \n",vm.addressOf(parent1)); 
		System.out.printf("parent2 주소 : %d \n",vm.addressOf(parent2));
		
		System.out.printf("parent1 ==parent2 %b \n",parent1==parent2 );
		System.out.printf("parent1.equals(parent) %b \n",parent1.equals(parent2));
		//equals 오버라이딩안했을때 p1.equals(p2)는 false임 
		
		
		
		//다른변수인데 주소값이 같다 기본형데이터가 적재되는 공간 : 상수 
		//몇번생성을 하더라도  값이 같으면 같은 공간을 바라보고 있다. 
		int number1 = 20; 
		int number2 = 20; 
		System.out.printf("number1 주소 : %d \n",vm.addressOf(number1));
		System.out.printf("number2 주소 : %d \n",vm.addressOf(number2));
		
		
		//같은값은데 주소값이 다르다.힙메모리적재되기 때문에 
		StringBuffer sb1  = new StringBuffer("ORIGINAL"); //힙메모리적재
		StringBuffer sb2  = new StringBuffer("ORIGINAL");
		
		System.out.printf("sb1 주소 : %d \n",vm.addressOf(sb1));
		System.out.printf("sb2 주소 : %d \n",vm.addressOf(sb2));
		
		//
		sample(number1, sb1);
		System.out.printf("number1 : %d \n", number1); //call by value
		System.out.println(sb1); //인스턴스 객체임 값과 주소가 별도로 관리됨 call by refernce
		
		Child child = new Child();
		child.template();
		
	}
	//메소드 호출
	//파라미터 : 기본형, 객체참조형  
/*	private static void sample(int number, StringBuffer sb) {
		number = number+1; // 
		System.out.println("sample: "+number);
		sb.append(" APPEND ");
	}
	*/
	private static int sample(int number, StringBuffer sb) {
		number = number+1; // 
		System.out.println("sample: "+number);
		sb.append(" APPEND ");
		return number;
	}
	
}

//call by reference 