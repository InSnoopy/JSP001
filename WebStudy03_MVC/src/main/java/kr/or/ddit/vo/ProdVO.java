package kr.or.ddit.vo;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import kr.or.ddit.validate.UpdateGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 *	상품 하나의 정보(분류명, 거래처 정보)를 담기 위한 객체 
 *	PROD(1) : BUYER(1) -> has a 관계
 */
@Data
@EqualsAndHashCode(of="prodId")
@ToString(exclude="predDetail") // predDetail은 길 수 있기에.. 빼논다.
public class ProdVO implements Serializable{
	  
	
	// 총 prod갯수
	// null수가 없기 때문에 int 
	// null이 나올 수 있는 경우에는 integer
	private int rnum;
	@NotBlank(groups=UpdateGroup.class)
	private String prodId;
	@NotBlank
	private String prodName;
	@NotBlank
	private String prodLgu;
	private String lprodNm;
	@NotBlank
	private String prodBuyer;
	private BuyerVO buyer; // has a
	@NotNull
	@Min(0)
	private Integer prodCost;
	@NotNull
	@Min(0)
	private Integer prodPrice;
	@NotNull
	@Min(0)
	private Integer prodSale;
	@NotBlank
	private String prodOutline;
	private String prodDetail;
	@NotBlank
	private String prodImg;
	@NotNull
	@Min(0)
	private Integer prodTotalstock;
	private String prodInsdate;
	@NotNull
	@Min(0)
	private Integer prodProperstock;
	private String prodSize;
	private String prodColor;
	private String prodDelivery;
	private String prodUnit;
	private Integer prodQtyin;
	private Integer prodQtysale;
	private Integer prodMileage;
	
	// 구매한 회원의 아이디가 중복되지 않게 set사용
	private Set<MemberVO> memberSet; // has many
	
	// 스칼라 쿼리같은 경우 has관계를 형성할 필요가 없다. (값만)가지고 있다. 스칼라 쿼리는
	private int cartCount;

}
