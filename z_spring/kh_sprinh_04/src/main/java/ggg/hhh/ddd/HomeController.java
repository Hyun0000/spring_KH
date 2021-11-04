package ggg.hhh.ddd;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
// @Controller을 <annotation-driven />으로 훑어서 기억한다.
// (@RequestMapping + @Controller = @WebServlet)이라고 생각하면 된다.
public class HomeController {
	
	@Autowired MemberService memberService; 
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
// ============================================================================================================================
	// 1.
	// 기본 생성 코드
	@RequestMapping(value = "/", method = RequestMethod.GET) // HandlerMapping에 해당되는 부분이다.
	// value가 "/" 이고 get 방식이면 아래의 method를 실행해라
	// 이것 또한 <annotation-driven />으로 훑어서 기억한다.
	public String home(Locale locale, Model model) { // method 이름은 아무 의미가 없다.
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
		// "home"이 사용자에게 보여줄 jsp 파일의 이름이다. (.jsp)는 앞으로 작성하지 않는다.
		// DispatcherServlet는 "home"이라는 이름에 (prefix = "/WEB-INF/views/") / (suffix = ".jsp")를 붙여서 ViewResolver에게 전달한다.
		// ViewResolver는 ("/WEB-INF/views/home.jsp")에 해당하는 jsp 파일을 선택해 '다시 DispatcherServlet에게 보내준다.'
		// DispatcherServlet는 해당 page를 가지고 사용자에게 응답(response)한다.
	}
// ============================================================================================================================
	// 1-1. 여러개 만들수도 있다.
	@RequestMapping(value = "/aaa", method = RequestMethod.GET)
	public String orange(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = "아무 의미 없는 메소드명";
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
		// URL에 (value = "/aaa")라고 들어와도 열리는 페이지는 1번의 경우와 동일하게 home이다.
	}
// ============================================================================================================================
	// 2.
	// ﻿controller --> DispatcherServlet : view 정보 & data 보내기(페이지 + 데이터 전달 - 값을 들고 이동)
	@RequestMapping(value = "hello", method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mv) {
		// 사용자가 URL값을 hello라고 치면 DispatcherServlet가 (ModelAndView mv)를 들고 ﻿controller로 온다. 
		// DispatcherServlet가 (ModelAndView mv)를 들고오는 것이기에 아래의 코드를 ﻿controller에서 작성할 필요가 없다.
		// ModelAndView mv = new ModelAndView();
		
		// view 정보 담기(hello.jsp를 열어달라는 의미)
		// 더이상 코드에서 (.jsp)는 없다. page의 이름만 전달하면 된다.
		mv.setViewName("hello");
		
		// DB에서 조회한 data 담기
		mv.addObject("k1","aaaaa");
		// 기존 servlet으로 치면 request.setAttribute("k1","aaaaa"); 같은 코드이다.
		// 그러나 spring은 model 객체를 사용하기에 setAttribute 개념을 쓰지 않는다. 
		
		return mv;
		// DispatcherServlet이 전달해준 (ModelAndView mv)에 (view 정보 & data)를 담아 다시 DispatcherServlet에게 반환한다.
		// 결과 : data가 화면에 뿌려진 (hello.jsp)가 열린다.
	}
	// 상세설명
	// DispatcherServlet이 갖고있는 ModelAndView를 controller에서 받는다.
	// controller에서는 (view 정보 & data)를 ModelAndView에 '추가해서 담는 것'이다.
	// 즉, ModelAndView에 이미 들어있는 각종 정보들을 그대로 유지한채(형태를 그대로 유지한채) 새로운 정보를 추가로 담는 것이다.
	// 당연히 처음에 DispatcherServlet --> controller로 ModelAndView를 전달할 때도 이미 '기존의 각종 정보들이 들어있는 상태로 전달된 것'이다.
// ============================================================================================================================
	// 3. 비즈니스 로직 없이 바로 view page 열기
	// ﻿controller --> DispatcherServlet : view 정보만 전달(페이지만 전달)
	@RequestMapping(value = "hello2", method = RequestMethod.GET)
	public String home2(ModelAndView mv) { // 누군가 hello라고 치면 mv를 들고들어온다.
		
		// hello.jsp가 열린다.
		return "hello";
		// 어떤 view page가 열린것인지만 정한다.
		// DB를 갔다오는 작업없이 only view page만 여는 것이다.
		// "data 안 들고 가도되니까 view page만 열어줘"
		
		// return을 string으로 하면 spring이 현재 이 작업은 비즈니스 로직없이 오직 view page만 열려는 작업이라는 것을 인지한다.
		// 따라서 DB를 갈 필요도 없다.
		// 단, (return "hello")처럼 'view에 대한 정보는 반드시 있어야'한다.(view page가 없을수는 없으니까)
	}
	// 상세설명
	// 'DispatcherServlet에게 page 정보만 전달하는 것'이기에 DB의 data를 전달하는 코드는 필요없다.
	// 사용자의 '요청 URL에 맞는 페이지 값만 전달'하면 되는 것이다.
// ============================================================================================================================
	// 4.
	// ﻿controller --> DispatcherServlet : view 정보 & data 보내기(페이지 + 데이터 전달 - 값을 들고 이동)
	@RequestMapping(value = "hello3", method = RequestMethod.GET)
	public String home3(Model m) {
		// 2번 방식과는 다른 방식으로 (view 정보 & data)를 DispatcherServlet에게 전달해보자
		// 2번 방식에서는 DispatcherServlet로부터 (ModelAndView mv)를 전달받았다.
		// 이번에는 'DispatcherServlet로부터 (Model m)를 전달받는다.'
		
		// 2번과 마찬가지로 아래의 코드를 ﻿controller에서 작성할 필요가 없다.
		// why? (Model m)을  DispatcherServlet가 전달해주니까 굳이 만들 필요가 없다.
		// Model m = new Model();
		// 더군다나 Model은 interface이기에 Model의 instance를 만들 수도 없다.
		
		// 주의!!!
		// 2번에서는 (ModelAndView mv)를 전달받았기에 반환타입을 ModelAndView로 했다.
		// 그러나 현재 method의 '반환 타입은 Model이 아니라 String'이여야한다.
		// why? Model은 DB에서 조회한 data만을 담을 수 있고 view page에 대한 정보는 담을 수 없다.
		// 이때 위에서 말했듯이 설사 DB에서 조회한 data가 없어도 'view page에 대한 정보는 무조건 있어야'하기에
		// 반환 타입을 Model로 해버리면 view page에 대한 정보를 DispatcherServlet에게 전달할 수가 없는 것이다.
		
		// 반환 타입이 string이 되면 Model에 담은 DB의 data를 전달 못하는 것이 아니냐?
		// NO, spring은 아래와 같은 로직을 가지고 있다.
		
		// DispatcherServlet가 controller에게 (Model m)을 전달하면,
		// 즉, 아예 parameter로 Model을 적어주면 현재 controller에서 DB의 data를 담을 Model 객체가 필요하다는 것을 인지하는 것이다.
		// 그러면 DispatcherServlet가 controller에게 (Model m)을 전달하게 해주고 해당 Model 객체에 data를 저장하게 해준다.
		// "사용할 수 있는 Model 객체를 너한테(controller한테) 줄게, 그럼 거기에다가 return할 data를 적어"
		
		// 즉, parameter에 Model을 적음으로 인해서
		// DispatcherServlet이 controller에게 DB data를 담을 수 있는 Model 객체를 주도록 정해져있는 것이다.
		// 그러면 method의 반환 타입이 string이라고 하더라도 'DispatcherServlet이 DB에서 가져온 data가 있다는 것을 인지할 수 있다는 것'이다.
		// 그리고 그 data를 view에도 전달하는 것이다.
		
		// 정리
		// return에 Model을 명시하지 않아도 controller에게 Model을 건네준 DispatcherServlet이
		// controller가 자신에게 Model을 다시 return할 것이라는 사실을 인지하고 있는 것이다.
		// 따라서 반환 타입에서 Model을 따로 적을 필요가 없다.
		// 대신 Model에는 view 정보를 담을 수 없기에 반환 타입을 String으로 지정해 view에 대한 정보도 같이 받는 것이다.
		
		m.addAttribute("k1", "2번째 예시");
		// 2번의 ModelAndView와 마찬가지로 이미 다른 정보들도 들어있는 Model한테 새로운 data를 추가하는 것이다.
		// (이미 만들어저 있는 Model 객체를 받아서 새로운 값만 추가한 것)
		
		// hello라는 view page 정보 전달 (이때 data도 같이 가지고 간다.)
		return "hello";
	}
// ============================================================================================================================
	// 5.
	// ﻿controller --> DispatcherServlet : view 정보 & data 보내기(페이지 + 데이터 전달 - 값을 들고 이동)
	@RequestMapping(value = "hello4", method = RequestMethod.GET)
	public ModelAndView home4() {
		// 2번의 경우와 달리 controller 단계에서 아예 새로운 ModelAndView 객체를 만드는 것이다.
		// DispatcherServlet으로부터 전달받는 것은 아무것도 없다.
		ModelAndView mv = new ModelAndView();
		// 위의 ModelAndView는 DispatcherServlet의 것이 아니다.
		mv.setViewName("hello");
		mv.addObject("k1", "3번째 방법");
		
		// DispatcherServlet에게 ModelAndView return
		return mv;
		// controller에서 만든 ModelAndView에 담긴 (view 정보 & data)를 DispatcherServlet는 '자신의 ModelAndView에 추가'한다.
		// 그후 자신의 ModelAndView에 담긴 정보를 가지고 ViewResolver로 이동한다.
	}
}