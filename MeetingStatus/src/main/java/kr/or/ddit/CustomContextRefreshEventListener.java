package kr.or.ddit;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomContextRefreshEventListener {
	private static final Logger log = LoggerFactory.getLogger(CustomContextRefreshEventListener.class);
	@Inject
	private ServletContext application;
	
	@EventListener(ContextRefreshedEvent.class)
	public void eventListener(ContextRefreshedEvent event) {
		application.setAttribute("cPath", application.getContextPath());
		log.info("contextPath to cPath : {}", application.getContextPath());
	}
}
