package kr.or.ddit.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class StreamCopyTest {
   private File targetFile;
   private File destFile;
   
   @Before
   public void setUp() {
	   // 원본 File, 복사할 File 준비
	   targetFile = new File("D:/contents/movies/target.mp4");
	   System.out.println(targetFile.length());
	   destFile = new File("d:/target.mp4");
   }
   
//   @Test // 2~4초
   public void copyTest1() throws IOException {
   
	   
	try(
			// FileInputStream, FileOutputStream 준비 -> 동영상이기에 이진수이기 때문에 이걸로 선택
			FileInputStream fis = new FileInputStream(targetFile);
			FileOutputStream fos = new FileOutputStream(destFile);
		){
			int tmp = -1;
			while ((tmp = fis.read()) != -1) {
			fos.write(tmp);
		}
			fis.close();
			fos.close();
		}
	}
//   @Test // 0.004초
   public void copyTest2() throws IOException {

	try(	
			// 1차 스트림
			FileInputStream fis = new FileInputStream(targetFile);
			FileOutputStream fos = new FileOutputStream(destFile);
		){
			// byte가 딱 떨어지지 않는다면 남은 찌꺼기 데이터가 그 다음 데이터에 담길 수도 있다.
			byte[] buffer = new byte[1024];
			int length = -1;
// 			int count = 1;
			while ((length = fis.read(buffer)) != -1) {
//				if(count++==1) {
//					Arrays.fill(buffer,(byte)0); // 영상 앞부분이 짤린다.?
//				}
				fos.write(buffer, 0, length);
				// buffer가 읽어들인 데이터를 가지고 있다.
				// 2번째 데이터는 딱 자기가 쓰는 데이터만 가지고 있다.
		}
			fis.close();
			fos.close();
		}
	}
   
   
   @Test // 0.004초
   public void copyTest3() throws IOException {
 
	try(
			FileInputStream fis = new FileInputStream(targetFile);
			FileOutputStream fos = new FileOutputStream(destFile);
			
			// 2차 스트림
			BufferedInputStream bis = new BufferedInputStream(fis);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
		){
			int tmp = -1;
			while ((tmp = bis.read()) != -1) {
				bos.write(tmp);
				// fis, fos가 아니라 
				// bos, bis로..
		}
			fis.close();
			fos.close();
		}
	}
   
   
   
}

