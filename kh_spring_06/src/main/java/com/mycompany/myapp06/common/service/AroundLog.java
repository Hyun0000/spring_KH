package com.mycompany.myapp06.common.service;

import org.aspectj.lang.JoinPoint;

public class AroundLog {
//	public void aroundLog() {
//		System.out.println("aroundLog 메소드!!");
//	}
// ====================================================================================
	public void beforeLog(JoinPoint jp) {
		// JoinPoint --> import org.aspectj.lang.JoinPoint
		System.out.println("beforeLog method");
		System.out.println("target name : " + jp.getSignature().getName());
		Object[] args = jp.getArgs();
		// getArgs() : Object 배열 모양으로 반환한다.
		// getSignature() : 대상 객체 method의 설명(메소드명, 리턴타입 등)을 반환
		for (int i = 0; i < args.length; i++) {
			System.out.println("target args : " + args[i].toString());
		}
		// advice가 적용되는 method의 argument를 출력한다.(있는 만큼 보여주겠다.)
		// 즉, argument의 개수만큼 뿌려준다.(argument가 0개라고 해서 오류가 나지는 않는다.)
		// ex)
		// public String selectAllBoard(Model model) {...}의 (Model model)를 뿌리는 것이다. 
		// method의 parameter를 뿌리는 것이다.
	}
// ====================================================================================	
	public void afterLog(JoinPoint jp) {
		System.out.println("afterLog method");
		System.out.println("target name : " + jp.getSignature().getName());
	}
// ====================================================================================
	// <aop:after-returning>
	// advice가 적용되는 method의 return 값을 알 수 있다.
	// ex)
	// Controller의 [ return boardService.getBoardList(vo) ]가 어떤 값을 가지고 있는지 출력한다.
	// <aop:after>는 method의 return 후에 실행되는 것이기에 return 값이 무엇인지 알 수 없다.
	public void afterReturningLog(JoinPoint jp, Object ro) {
		System.out.println("afterLog method");
		System.out.println("target name : " + jp.getSignature().getName());
		
		// method의 return 값 출력
		// 반환 타입이 void이면 반환하는 값이 없기에 ro가 null이 된다.
		// null에게 toString()을 하면 NullPointException이 발생하기 때문에
		// 이를 방지하기 위해 아래와 같은 조건문을 작성한다.
		if (ro != null) {
			System.out.println("target return : " + ro.toString());
		}
	}
}