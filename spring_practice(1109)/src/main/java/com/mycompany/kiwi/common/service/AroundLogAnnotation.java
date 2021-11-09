package com.mycompany.kiwi.common.service;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class AroundLogAnnotation {
	// 1.
	// pointcut만 작성을 따로 해준다.(method 형테로 만들어야한다.)
	
	// class 이름이 Service로 끝나는 class에만 Pointcut을 적용한다.
	@Pointcut("execution(public * com.mycompany.kiwi..*Service.*(..))")
	public void commonServicePointCut() {} // 안에 아무것도 넣지 않는다.
	// commonServicePointCut() method에 아래의 pointcut 조건을 설정하는 것이다.
	// @Pointcut("execution(public * com.mycompany.myapp06..*Service.*(..))")

	// class 이름이 Controller로 끝나는 class에만 Pointcut을 적용한다.
	@Pointcut("execution(public * com.mycompany.kiwi..*Controller.*(..))")
	public void commonControllerPointCut() {} // 안에 아무것도 넣지 않는다.

	// class 이름이 Dao로 끝나는 class에만 Pointcut을 적용한다.
	@Pointcut("execution(public * com.mycompany.kiwi..*Dao.*(..))")
	public void commonDaoPointCut() {} // 안에 아무것도 넣지 않는다.
// =======================================================================================================================	
	// 2. 위에서 만든 pointcut을 이용해 AOP log를 등록한다.
	@Before("commonServicePointCut()")
	// @Before은 commonServicePointCut() pointcut 조건이면 발동한다.
	// 이때 @Before에서 실행할 내용을 method로 정의한다.
	public void beforeLog(JoinPoint jp) {
		System.out.println("=========================================================================");
		System.out.println("beforeLog method : " + jp.getThis() + "target name : " + jp.getSignature().getName());
		Object[] args = jp.getArgs();
		for (int i = 0; i < args.length; i++) {
			System.out.println("target args : " + args[i].toString());
		}
		System.out.println("=========================================================================");
	}
	// XML의 아래의 부분과 같다.
	// <aop:before method="beforeLog" pointcut="execution(public * com.mycompany.myapp06..*Service.*(..))"/>
// =======================================================================================================================
// =======================================================================================================================
// =======================================================================================================================
	// 등록한 하나의 PointCut method는 여러 log에서 재사용이 가능하다.(XML은 일일이 하나하나 작성해야한다.)
	
	// @AfterReturning도 commonServicePointCut() pointcut 조건이면 발동한다.
	// 이때 @AfterReturning에서 실행할 내용을 method로 정의한다.
	// @AfterReturning은 returning 속성을 설정해야한다.
	@AfterReturning(pointcut= "commonServicePointCut()", returning = "ro")
	public void afterReturningLog(JoinPoint jp, Object ro) {
		System.out.println("=========================================================================");
		System.out.println("afterReturningLog method : " + jp.getThis() + "target name : " + jp.getSignature().getName());
		
		if (ro != null) {
				System.out.println("target return : " + ro.toString());
		}
		System.out.println("=========================================================================");
	}
// =======================================================================================================================
// =======================================================================================================================
// =======================================================================================================================
	// @Around은 commonControllerPointCut() pointcut 조건이면 발동한다.
	// 이때 @Around에서 실행할 내용을 method로 정의한다.
	@Around("commonControllerPointCut()")
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("=========================================================================");
		System.out.println("aroundLog method : " + pjp.getThis());
		System.out.println("target name : " + pjp.getSignature().getName()); // 메소드 이름을 알 수 있다.
		
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object ro = pjp.proceed();
		
		sw.stop();
		System.out.println("메소드 수행시간 : " + sw.getTotalTimeMillis() + "ms");
		
		if (ro != null) {
			System.out.println("target return : " + ro.toString());
		}
		System.out.println("=========================================================================");
		return ro;
	}
}
