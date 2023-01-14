package kr.or.ddit.member.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kr.or.ddit.validate.DeleteGroup;
import kr.or.ddit.validate.InsertGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ToString(exclude= {"memPass", "memRegno1", "memRegno2"})
@EqualsAndHashCode(of="memId")
@Data
public class MemberVO implements Serializable{

//	MEM_ID          NOT NULL VARCHAR2(15)  
//	MEM_PASS        NOT NULL VARCHAR2(200) 
//	MEM_NAME        NOT NULL VARCHAR2(20)  
//	MEM_REGNO1               CHAR(6)       
//	MEM_REGNO2               CHAR(7)       
//	MEM_BIR         NOT NULL DATE          
//	MEM_ZIP         NOT NULL CHAR(7)       
//	MEM_ADD1        NOT NULL VARCHAR2(100) 
//	MEM_ADD2        NOT NULL VARCHAR2(80)  
//	MEM_HOMETEL              VARCHAR2(14)  
//	MEM_COMTEL               VARCHAR2(14)  
//	MEM_HP                   VARCHAR2(15)  
//	MEM_MAIL                 VARCHAR2(40)  
//	MEM_JOB                  VARCHAR2(40)  
//	MEM_LIKE                 VARCHAR2(40)  
//	MEM_MEMORIAL             VARCHAR2(40)  
//	MEM_MEMORIALDAY          DATE          
//	MEM_MILEAGE              NUMBER(10)    
//	MEM_DELETE               VARCHAR2(1)   
//	MEM_ROLE                 VARCHAR2(20)  
//	MEM_IMG                  BLOB          
	
	private String rnum;
	
	@NotBlank(groups= {Default.class, DeleteGroup.class})
	private String memId;
	@NotBlank(groups= {Default.class, DeleteGroup.class})
	@Size(min=4, max=8, groups= {Default.class, DeleteGroup.class})
	@JsonIgnore
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
	private String memMemorialday;
	@Min(0)
	private Integer memMileage;
	private String memDelete;
	private String memRole;
	
	private String memImg;
	
}
