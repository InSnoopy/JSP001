package kr.or.ddit.vo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

public class FileTestVO {
	private String textParam;
	private String dateParam;
	private MultipartFile file1;
	private String file1Savename; // metadata 추출해서 DB에 넣기
	public void file1SaveTo(File saveFolder) throws IllegalStateException, IOException {
		if(file1==null) return;
		file1.transferTo(new File(saveFolder, file1Savename));
	}
	
	private MultipartFile[] file2;
	private List<String> file2Savenames; // DB랑 대화하기 위해서 List로
	public void file2SaveTo(File saveFolder) throws IllegalStateException, IOException {
		if(file2==null || file2.length==0) return;
		// stream API는 index를 가져올 수 없다.
		for(int idx=0; idx<file2.length; idx++) {
			file2[idx].transferTo(new File(saveFolder, file2Savenames.get(idx)));
		}
	}
	
	public String getTextParam() {
		return textParam;
	}
	public void setTextParam(String textParam) {
		this.textParam = textParam;
	}
	public String getDateParam() {
		return dateParam;
	}
	public void setDateParam(String dateParam) {
		this.dateParam = dateParam;
	}
	public MultipartFile getFile1() {
		return file1;
	}
	public void setFile1(MultipartFile file1) {
		if(!file1.isEmpty()) {
			this.file1 = file1;
			this.file1Savename = UUID.randomUUID().toString();
		}
	}
	public MultipartFile[] getFile2() {
		return file2;
	}
	public void setFile2(MultipartFile[] file2) {
		this.file2Savenames = Arrays.stream(file2)
								.filter((f)->!f.isEmpty())
								.map((f)->{
									return UUID.randomUUID().toString();
								}).collect(Collectors.toList());
		this.file2 = Arrays.stream(file2)
			.filter((f)->!f.isEmpty())
			.toArray(MultipartFile[]::new); // 이러면 새로운 배열이 만들어진다.
	}
	public String getFile1Savename() {
		return file1Savename;
	}
	public void setFile1Savename(String file1Savename) {
		this.file1Savename = file1Savename;
	}
	public List<String> getFile2Savenames() {
		return file2Savenames;
	}
	public void setFile2Savenames(List<String> file2Savenames) {
		this.file2Savenames = file2Savenames;
	}
	
	
}

