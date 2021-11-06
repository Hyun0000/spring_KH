package com.mycompany.myapp05;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.inject.Qualifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.myapp05.person.model.vo.Person;
import com.mycompany.myapp05.person.model.vo.PersonAnn;

/**
 * Handles requests for the application home page.
 */
@Controller
// 얘는 왜 뒤에 ("이름")이 없을까 --> 컨트롤러는 호출돼서 갖다쓸일이 없으니까
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired private Person person;
	// 대소문자 구분한다.
	// Person이 필요할때마다 매번 new Person을 하지 않는다는 것이다.
	// @Autowired : 타입과 이름이 모두 같아야 해당하는 객체를 가져온다.
	
	@Autowired private Person person2;
	
	// 두개의 @Autowired를 이용해서 각각의 bean을 찾아오는게 맞을까?
	// 아님 한개의 @Autowired만 사용?
	// 두개의 @Autowired를 사용한다.
	// 어노테이션은 나 바로 밑의 한 줄까지만 포함한다. 그게 한계이다.
	// 따라서 어노테이션을 각각 써야한다.
	
	@Autowired private PersonAnn p;
	// @Component("personAnn") 이렇게 했지만 p로 해도 출력이 된다.
	// 타입만 맞으면 문제가 없다.
	
	@Resource private Person person3;
	// @Autowired와 동일하게 동작
	
	// @Autowired private Person person100;
	// 오류
	
	// 빈으로 만들었는데 이름이 없을수도 있다.
	// 단 VO를 컴포넌트로 만들면 이름이 있을수도 있고 없을수도 있다.
	
	@Resource private PersonAnn p2;
	// 예는 나올까?
	// 잘 나온다.
	// 가장 가까운 이름을 찾는다???
	
	
//	@Autowired
//	@Qualifier("P")
//	private PersonAnn p3;
	// private PersonAnn p3;에 2개의 Annotation 설정이 먹히는 것이다.
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	// 컨트롤러는 @RequestMapping이 중요하다. HomeController의 이름과 method 이름은 필요없다.
	// @RequestMapping은 없으면 안 된다.
	public String home(Locale locale, Model model) {
		// 톰캣을 실행하면 
		
		System.out.println("person : " + person);
		System.out.println("person2 : " + person2);
		System.out.println("person3 : " + person3);
		System.out.println("p : " + p);
		System.out.println("p2 : " + p2);
		// System.out.println("person100 : " + person100);
		
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "home";
		
		// Job j2 = new Job();
		// 이렇게는 생성 핧 수 없다.
		
		// Job j2 = new Developer();
		// 이렇게는 생성할 수 있다.
		
	}
}