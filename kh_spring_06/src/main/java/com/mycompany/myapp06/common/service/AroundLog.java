package com.mycompany.myapp06.common.service;

import org.aspectj.lang.JoinPoint;
import org.springframework.ui.Model;

public class AroundLog {
//	public void aroundLog() {
//		System.out.println("aroundLog 메소드!!");
//	}
	
	public void beforeLog(JoinPoint jp) {
		// import org.aspectj.lang.JoinPoint
		System.out.println("beforeLog 메소드!!");
		System.out.println("target name : " + jp.getSignature().getName());
		Object[] args = jp.getArgs();
		for (int i = 0; i < args.length; i++) {
			System.out.println("target args : " + args[i].toString());
		}
		// 아규먼트가 있는만큼 뿌린다. 없으면 안 뿌린다.
		
		// getArgs : 컨트롤러의 메소드 실행하기 전에 지금이 메소드를 먼저 가는데
		// 이때 컨트롤러의 public String selectAllBoard(Model model)의 (Model model)를 뿌린다.
		// 컨트롤러 메소드의 파라미터를 뿌린다. 오브젝트 배열 모양으로 토한다.
		
	}
	/*
	 * [결과값]
	 * beforeLog 메소드!!
		target name : selectAllBoard
		aroundLog 메소드!!
		URL에 값을 입력했을때
		컨트롤러의 selectAllBoard 메소드가 실행되기전에 beforeLog 메소드를 먼저 갔다온다는 의미
		
		그후 컨트롤러의 selectAllBoard메소드가 실행되고 afterLog메소드가 실행된다.
		
		(원래는 바로 selectAllBoard 메소드만 실행되고 끝)
		
	 */
//	public void beforeLog(JoinPoint jp) {
//		System.out.println("beforeLog 메소드!!!");
//		System.out.println("target name: "+ jp.getSignature().getName());
//	}
	
	public void afterLog(JoinPoint jp) {
		// import org.aspectj.lang.JoinPoint
		System.out.println("afterLog 메소드!!");
		System.out.println("target name : " + jp.getSignature().getName());
	}
	// 애프터 리터닝
	// 리턴을 그래서 뭐를 하느냐 --> boardService.getBoardList(vo) 이게 도대체 뭔데
	// 이것을 자동으로 찍히게 해준다.(리턴 되는 값을 알아올 수 있다.)
	// after는 리턴한 후에 뿌린다. 그레서 뭘 리턴하는지 값을 읽을 수가 없다.
	
	public void afterReturningLog(JoinPoint jp, Object ro) {
		System.out.println("afterLog 메소드!!");
		System.out.println("target name : " + jp.getSignature().getName());
		// 어떻게 리턴값을 받아서 뿌릴 수 있을까?
		
		// 널포인트익셉션방지
		if (ro != null) {
			System.out.println("target return : " + ro.toString());
		}
		// 리턴은 딱 한개만 한다.(파라미터, 아규먼트는 여러개도 가능 )
	}
}








