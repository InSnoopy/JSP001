package kr.or.ddit.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import kr.or.ddit.dao.SampleDAO;



@Service
public class SampleService {
	private SampleDAO dao;

	// inject가 생략되어도 있는것처럼 된다.
	// 버전에 따라 다르다. 
	@Inject
	public SampleService(SampleDAO dao) {
		super();
		this.dao = dao;
	}
	
	public String retrieveInfo() {
		return "info "+dao.selectData();
	}
	
}
