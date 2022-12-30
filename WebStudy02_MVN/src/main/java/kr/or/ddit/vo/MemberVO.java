package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * VO(Value Object), DTO(Data Transfer Object), JavaBean, Model 
 *
 * JavaBean 규약
 * 1. 값을 담을 수 있는 property 정의
 * 2. property 캡슐화
 * 3. 캡슐화된 프로퍼티에 접근할 수 있는 인터페이스 제공 (get,set)
 * 		get[set] + 프로퍼티명의 첫문자를 대문자로 -> camel case
 * 4. 객체의 상태 비교 방법 제공 : equals
 * 		==, equals
 * 5. 객체의 상태 확인 방법 제공 : toString() 
 * 		객체의 상태에서 노출되면 안되는 데이터도 있다.
 * 6. 객체 직렬화 가능 ( implements Serializable)
 * 
 * 회원관리를 위한 Domain Layer
 *  : 한사람의 회원 정보(구매기록 포함)를 담기 위한 객체.
 *    MEMBER(1) : PROD(N) -> HAS MANY
 *    1 : 1 -> HAS A
 *    
 * ** 데이터매퍼나 ORM을 이용한 테이블 조인 방법
 * 	  ex)회원 정보 상세 조회시 구매 상품 기록을 함게 조회함.
 * 1. 대상 테이블 선택
 * 	  MEMBER, CART(CART_MEMBER, CART_PROD) ,PROD
 * 2. 각테이블로부터 데이터를 바인딩할 VO 설계
 * 	  MemberVO, ProdVO
 * 3. 각테이블의 relation을 VO 사이에 has 관계로 반영
 * 	  1(main):N -> has many , MemberVO has many ProdVO(collection)
 * 	  1(main):1 -> has a    , ProdVO has a BuyerVO
 * 4. resultType 대신 resultMap으로 바인딩 설정.
 *    has many : collection
 *    has a    : association
 */
//@Getter
//@Setter
//@ToString(exclude= {"memPass", "memRegno1", "memRegno2"}) // 빼고싶은 String
//@EqualsAndHashCode(of="memId") // of를 설정안하면 모든 필드가 같아야지 같은 객체로 취급
@NoArgsConstructor // 기본 생성자를 만들어준다.
@Data // 이걸로 다 된다.
@ToString(exclude= {"memPass", "memRegno1", "memRegno2"}) // 빼고싶은 String
@EqualsAndHashCode(of="memId") // of를 설정안하면 모든 필드가 같아야지 같은 객체로 취급
public class MemberVO implements Serializable{
	
	public MemberVO(@NotBlank(groups = { Default.class, DeleteGroup.class }, message = "아이디는 필수") String memId,
			@NotBlank(groups = { Default.class, DeleteGroup.class }) @Size(min = 4, max = 8, groups = { Default.class,
					DeleteGroup.class }) String memPass) {
		super();
		this.memId = memId;
		this.memPass = memPass;
	}
	
	// memId가 아니라 MemId라면??
	// 무조건 소문자로 만들면 기본 get,set이랑 차이가 없다.
	// DB의 구조를 확인하고 넣어야한다.
	@NotBlank(groups= {Default.class, DeleteGroup.class}, message="아이디는 필수")
	private String memId;
	@NotBlank(groups= {Default.class, DeleteGroup.class})
	@Size(min=4, max=8, groups= {Default.class, DeleteGroup.class})
	@JsonIgnore // 직렬화, 마샬링할 떄 다 대상이 안된다.
	private transient String memPass;
	@NotBlank
	private String memName;
	@JsonIgnore
	private transient String memRegno1;
	@JsonIgnore
	private transient String memRegno2;
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}", groups=InsertGroup.class)
	@NotBlank(groups=InsertGroup.class)
	private String memBir;
	@NotBlank
	private String memZip;
	@NotBlank
	private String memAdd1;
	@NotBlank
	private String memAdd2;
	private String memHometel;
	private String memComtel;
	private String memHp;
	@Email
	private String memMail;
	private String memJob;
	private String memLike;
	private String memMemorial;
	@Pattern(regexp="\\d{4}-\\d{2}-\\d{2}")
	private String memMemorialday;
	@Min(0)
	private Integer memMileage;
	private boolean memDelete; // null은 알아서 false로 값이 있으면 true로 
//	private String memDelete;
//	Integer vs int 차이는 Integer는 null이 있는 경우 int는 오는 값이 0이라도 오는경우
	private int cartCount;
	
	// 구매 기록을 담는
	private List<ProdVO> prodList; // has many 관계 (1:N)
	
}
