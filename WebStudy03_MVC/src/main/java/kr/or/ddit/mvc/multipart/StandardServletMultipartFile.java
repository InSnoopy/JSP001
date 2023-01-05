package kr.or.ddit.mvc.multipart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class StandardServletMultipartFile implements MultipartFile {
	
	private Part adaptee;
	
	//	adaptee가 된다. 기본 생성자를 없앤다.
	public StandardServletMultipartFile(Part adaptee) {
		super();
		this.adaptee = adaptee;
	}

	@Override
	public byte[] getBytes() throws IOException{
//		ByteArrayOutputStream os = new ByteArrayOutputStream();
//		InputStream is = getInputStream();
//		os.toByteArray();
		// 밑에 라이브러리 쓰면 위에껄 다 해준다.
		return IOUtils.toByteArray(getInputStream());
	}

	@Override
	public String getContentType() {
		return adaptee.getContentType();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return adaptee.getInputStream();
	}

	@Override
	public String getName() {
		return adaptee.getName();
	}

	@Override
	public String getOriginalFilename() {
		return adaptee.getSubmittedFileName();
	}

	@Override
	public long getSize() {
		return adaptee.getSize();
	}

	@Override
	public boolean isEmpty() {
		return StringUtils.isBlank(getOriginalFilename());
	}

	@Override
	public void transferTo(File dest) throws IOException {
		adaptee.write(dest.getCanonicalPath());
	}

}
