package com.mycompany.myapp06.common.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;

public class AroundLog {
	// before와 after 모두 걸린다.
	// afterReturning이랑은 다르다. = return값을 알수 없다.
	// 반드시 반환 타입이 Object여야한다.
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
		// Around advice는 JoinPoint의 하위 클래스인 ProceedingJoinPoint 타입의 parameter를 필수적으로 선언해야한다.
		
		// <aop:around method="aroundLog" pointcut="execution(public * com.mycompany.myapp06..*Dao.*(..))"/>
		
		// dao의 method가 현재 'throws Throwable'로 Exception을 throws하고 있기에
		// target 객체(dao class)의 method에서 Exception이 발생하면 현재 이 method(aroundLog)도 자신을 호출한 곳으로 throws Throwable 해야한다.
		// 이곳에서 try-catch는 잘 쓰지 않는다.
		
		System.out.println("aroundLog method : " + pjp.getThis());
		System.out.println("target name : " + pjp.getSignature().getName()); // 메소드 이름을 알 수 있다.
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object ro = pjp.proceed();
		// proceed() : targat 객체의 method를 실행한다.(원래 가서 동작해야되는 객체의 method를 실행한다.)
		// ex)
		// Service class의 method가 Dao class의 method를 호출하기 전에 이곳으로 먼저 온다. 그러면
		// pjp.proceed(); 로 인해 Dao의 method로 가고 Dao에서 해당 method를 실행하여 값을 받아온 후 다시
		// 해당 method의 Object ro에 넣어준다.
		// 그럼 ro를 호출한 service class의 method에게 전달한다.
		
		sw.stop();
		System.out.println("메소드 수행시간 : " + sw.getTotalTimeMillis() + "ms");
		// DAO, Service에 많이 건다.
		// 시간 체크 목적
		
		if (ro != null) {
			System.out.println("target return : " + ro.toString());
		}
		return ro;
	}
// ====================================================================================
	public void beforeLog(JoinPoint jp) {
		// JoinPoint --> import org.aspectj.lang.JoinPoint
		System.out.println("beforeLog method : " + jp.getThis() + "target name : " + jp.getSignature().getName());
		System.out.println("beforeLog method");
		
		// System.out.println("beforeLog method : " + jp.getThis()); // 어느 객체의 무슨 메소드인지 알 수 있다.
		// System.out.println("target name : " + jp.getSignature().getName()); // 메소드명
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
		System.out.println("afterLog method : " + jp.getThis() + "target name : " + jp.getSignature().getName());
	}
// ====================================================================================
	// <aop:after-returning>
	// advice가 적용되는 method의 return 값을 알 수 있다.
	// ex)
	// Controller의 [ return boardService.getBoardList(vo) ]가 어떤 값을 가지고 있는지 출력한다.
	// <aop:after>는 method의 return 후에 실행되는 것이기에 return 값이 무엇인지 알 수 없다.
	public void afterReturningLog(JoinPoint jp, Object ro) {
		System.out.println("afterReturningLog method : " + jp.getThis() + "target name : " + jp.getSignature().getName());
		
		// method의 return 값 출력
		// 반환 타입이 void이면 반환하는 값이 없기에 ro가 null이 된다.
		// null에게 toString()을 하면 NullPointException이 발생하기 때문에
		// 이를 방지하기 위해 아래와 같은 조건문을 작성한다.
		if (ro != null) {
			System.out.println("target return : " + ro.toString());
		}
	}
	
//	public void afterReturningDaoLog(JoinPoint jp, Object ro) {
//		System.out.println("afterReturningLog method : " + jp.getThis());
//		System.out.println("target name : " + jp.getSignature().getName());
//		
//		// method의 return 값 출력
//		// 반환 타입이 void이면 반환하는 값이 없기에 ro가 null이 된다.
//		// null에게 toString()을 하면 NullPointException이 발생하기 때문에
//		// 이를 방지하기 위해 아래와 같은 조건문을 작성한다.
//		if (ro != null) {
//			System.out.println("target return : " + ro.toString());
//		}
//	}
	
}