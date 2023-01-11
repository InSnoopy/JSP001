package kr.or.ddit.vo;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import kr.or.ddit.validate.InsertGroup;
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
	@NotBlank(groups=InsertGroup.class)
	private String prodName;
	@NotBlank(groups=InsertGroup.class)
	private String prodLgu;
	private String lprodNm;
	@NotBlank(groups=InsertGroup.class)
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
	@NotBlank(groups=InsertGroup.class)
	private String prodImg; // PROD 테이블 조회용 프로퍼티
	
	// 버전에 따라 타입이 달라진다 그러면 결합력이 발생!
	// 이 두개의 타입을 공통으로 가질 부모를 만들어준다. MultipartFile (interface)
//	private Part prodImage; // part로 받기 때문에
//	private FileItem prodImage;
	private MultipartFile prodImage;
	
	public void setProdImage(MultipartFile prodImage) {
		if(prodImage!=null && !prodImage.isEmpty() && prodImage.getContentType().startsWith("image/")) {			
			this.prodImage = prodImage;
			this.prodImg = UUID.randomUUID().toString();
		}
	}
	public void saveTo(File saveFolder) throws IOException {
		if(prodImage==null || prodImg==null) return;
		File saveFile = new File(saveFolder, prodImg);
		prodImage.transferTo(saveFile);
	}
	
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
