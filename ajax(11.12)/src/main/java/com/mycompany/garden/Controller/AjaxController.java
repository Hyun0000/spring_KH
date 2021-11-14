package com.mycompany.garden.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.garden.ajax.model.vo.Sample;
import com.mycompany.garden.ajax.model.vo.User;

@Controller
public class AjaxController {
// =========================================================================================================
// =========================================================================================================
	// view 역할
	@RequestMapping("ajaxtest")
	public String test1Method() {
		return "ajaxtest";
	}
// =========================================================================================================
	// ajax1(옛날 방식)
	@RequestMapping(value = "test1.do", method = RequestMethod.POST)
	public void test1Method(Sample vo, HttpServletResponse response, HttpSession session) throws IOException {
		// Sample vo에 (name : "신사임당", age : 47)이라는 data가 담겨들어온다.
		// 따라서 해당 class에서는 request로 사용자가 전달한 값을 꺼내지 않는다.
		
		// data가 제대로 들어왔는지 확인
		System.out.println("[radder] test1Method vo : " + vo);
		
		// PrintWriter 객체를 생성하여 통신에 대한 응답 결과를 전달
		PrintWriter out = response.getWriter();
		
		if (vo.getName().equals("신사임당")) {
			// session에 data 담기
			session.setAttribute("samp", vo);
			
			// ajax에 보낼 data 담기
			out.append("ok");
			out.flush();
		} else {
			// logger.error("44");
			// 진짜 심각한 부분이라고 생각되면 뿌린다.
			
			// ajax에 보낼 data 담기
			out.append("fail");
			out.flush();
		}
		out.close();
		// out로 채워진 값이 ajax 코드 부분의 [ success : function (result) {...} ]의 (result)에 채워진다.
	}
// =========================================================================================================	
	// ajax2
	@RequestMapping(value = "test2.do", method = RequestMethod.POST)
	@ResponseBody // 결과를 response 객체에 담아서 보내는 Annotation
	// @ResponseBody가 out.append("ok")의 역할을 하는 것이다. --> data를 채워서 보내준다.
	// @ResponseBody를 json이랑 연동해서 생각하지 말것, 서로 다른 영역이다.
	// JSONObject / job.toJSONString();
	// --> 이런건 view단으로 data를 json 형태로 보낼 것이기에 작성한 것이다. @ResponseBody랑은 상관없다.
	
	// 반환타입이 void가 아니라 String이다.
	public String test2Method(HttpServletResponse response) throws IOException {
		// 현재는 view에서 가지고 들어오는 data가 없다. 그러면 parameter가 없어도 된다.
		// (HttpServletResponse response)를 쓴 이유는 아래의 코드 때문이다.
		response.setContentType("application/json;charset=UTF-8");
		// (web.xml)에 Encoding filter를 만들어도 얘는 써줘야한다.
		
		// Map 형식의 JSONObject 객체를 생성, view단에 보내줄 값을 Key와 Value 형태로 담는다.
		JSONObject job = new JSONObject();
		
		job.put("no", 123);
		job.put("title", "garden 보고싶다.");
		// 한글 전송 시 깨질 우려가 있으므로, URLEncoder로 UTF-8 방식의 인코딩을 처리한다
		job.put("writer", URLEncoder.encode("홍길동", "utf-8"));
		job.put("content", URLEncoder.encode("json 객체를 뷰로 리턴하는 테스트", "utf-8"));
		
		// JSONObject를 string 형태로 리턴한다.
		// 그래서 해당 method의 반환타입이 String인 것이다.
		return job.toJSONString();
	}
// =========================================================================================================
	// logger
	
	// 근데 아래의 코드를 매 class마다 복붙할 수는 없다. --> web.xml로 가자(Bean으로 만들어보자)
	// 왜 하필 web.xml인가?
	// logger를 bean으로 만들기 위해서는 <listener>가 필요한데 servlet-context.xml과 root-context.xml에는
	// <listener> namaspace가 없기 때문이다.
	
	// <listener>
	// - 서버가 계속 돌아가다가 특정 이벤트가 발생하면 내가 그때 미리 설정한 동작을 하겠다.
	// - 어느 타이밍에 어떤 이벤트가 발생할지 모르니 계속 대기타고 있는 것이다.
	
	private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);
	// Logger --> import org.slf4j.Logger;(인터페이스)
	// LoggerFactory --> import org.slf4j.LoggerFactory;
	// getLogger()의 (괄호) 자리에는 현재 class 이름을 넣어준다.
	// "내 class 모양을 전부 너한테 줄테니 logger 너는 내가 준 모양을 보고 내 class에 대한 log를 걸어줘"
	
	// LoggerFactory class에는 getLogger() method가 아래와 같이 정의돼 있다.
	
//	  public static Logger getLogger(String name) { -- 오버로딩
//		    ILoggerFactory iLoggerFactory = getILoggerFactory();
//		    return iLoggerFactory.getLogger(name);
//		  }
//
//	  public static Logger getLogger(Class clazz) { -- 오버로딩
//	    return getLogger(clazz.getName());
//	  }
	
	// 따라서 Logger의 instance를 생성할때는 반환타입이 Logger인 LoggerFactory class의 getLogger() method를 이용한다.
	// static이므로 'new'해서 인스턴스를 생성하지 않는다.
	// 한 개의 instance만 만들어 놓고 그것을 계속 돌려쓰는 것이다.(싱글톤)
// =========================================================================================================	
	// ajax3
	@RequestMapping(value = "test3.do", method = RequestMethod.POST)
	public void test3Method(HttpServletResponse response) throws IOException {
		logger.info("test3Method() run...");
		// logger은 instance명이다.
		/*
		 * 순위(레벨) : FATAL - ERROR - WARN - INFO - DEBUG - TRACE
		 * DEBUG : (디버깅 용도)정도만 쓰고 TRACE는 보통 잘 안 쓴다.
		 * INFO : 상태 변화 정보, 메세지(순수한 정보를 나타낼때)
		 * WARN : 오류의 원인이 될 수 있는 부분(오류가 우려되는 부분, 에러가 아닌 경고문구를 나타낼때)
		 * ERROR : 오류(진짜 ERROR를 나타낸다.)
		 * FATAL : 심각할때, 프로그램이 아예 죽어버릴때
		 * 
		 * - 모든 log 관련 Tool이 반드시 위의 이름은 아닐 것이다.
		 * - DEBUG, TRACE : 개발자가 디버그 할 때만 쓰는것, 실상품으로 내놓을때는 없애버린다.
		 * - 즉, log로 찍을 메세지의 성격에 맞는 '레벨을 선택'하는 것이다.
		 * 
		 * ex)
		 * logger.info("test3Method() run...");를
		 * logger.error("test3Method() run...");라고 뿌리면 안 된다는 것이다.
		 */
		System.out.println("===============================================");
		logger.info("info 메세지");
		logger.error("에러 메세지");
		logger.warn("warn 메세지");
		logger.debug("debug 메세지");
		System.out.println("===============================================");
		// INFO : com.mycompany.garden.Controller.AjaxController - test3Method() run...
		// ERROR: com.mycompany.garden.Controller.AjaxController - 에러 메세지
		// WARN : com.mycompany.garden.Controller.AjaxController - warn 메세지
		/*
		 * debug는 안 나오고 있다. 설정 level을 만져야한다. log4j.xml로 가자(src/main/resources package에 있다.)
		 */
// ######################################################
// ######################################################
		// java의 List를 json 배열로 만들어 view단으로 반환(return)한다.
		// List에 저장할 data 넣기
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("u1234", "p1234", "홍길동", 25, "h1234@kh.org"));
		list.add(new User("u2345", "p2345", "박문수", 33, "p2345@kh.org"));
		list.add(new User("u3456", "p3456", "장영실", 45, "j3456@kh.org"));
		
		// 전송용 json 객체
		JSONObject sendJson = new JSONObject();
		
		// JSONArray 객체를 생성하여 JSONObject 객체를 하나씩 담는다.
		JSONArray jarr = new JSONArray();
		
		// List에 담긴 data를 jarr(JSONArray 객체)에 저장한다.
		for (User user : list) {
			// user 정보를 저장할 json 객체 선언
			JSONObject juser = new JSONObject();
			juser.put("userId", user.getUserId());
			juser.put("userPwd", user.getUserPwd());
			juser.put("userName", URLEncoder.encode(user.getUserName(), "utf-8"));
			juser.put("age", user.getAge());
			juser.put("email", user.getEmail());
			jarr.add(juser);
		}
		
		// 전송할 json 객체 배열을 JSONObject에 담아 한 번에 전달한다.
		sendJson.put("list", jarr);
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("sendJson.toJSONString() : " + sendJson.toJSONString());
		out.println(sendJson.toJSONString());
		out.flush();
		out.close();
	}
// =========================================================================================================
	// ajax5
	@RequestMapping(value = "test5.do", method = RequestMethod.POST)
	public ResponseEntity<String> test5Method(HttpServletRequest request, @RequestBody String param) throws Exception {
		logger.info("test5Method() run...");
		request.setCharacterEncoding("utf-8");
		
		//전송된 문자열을 json 객체로 변환 처리
		JSONParser parser = new JSONParser();
		JSONObject job = (JSONObject)parser.parse(param);
		System.out.println(job);
		// job = {"name":"강감찬","age":33}
		
		String name = (String)job.get("name");
		int age = ((Long)job.get("age")).intValue();
		// Object java.util.HashMap.get(Object arg0)
		
		System.out.println("name : " + name + ", age : " + age);
		
		//정상 완료됨을 클라이언트로 성공값을 보내야 한다.
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
// =========================================================================================================
	// ajax6
	@RequestMapping(value = "test6.do", method = RequestMethod.POST)
	public ResponseEntity<String> test6Method(HttpServletRequest request, @RequestBody String param) throws Exception {
		logger.info("test6Method() run...");
		request.setCharacterEncoding("utf-8");
		
		System.out.println("param : " + param);
		JSONArray jarr = (JSONArray)new JSONParser().parse(param);
		System.out.println("jarr.size() : " + jarr.size());
		System.out.println("jarr : " + jarr);
		// jarr.size() : 3
		// jarr : [{"name":"이 이","age":30},{"name":"신사임당","age":47},{"name":"황진이","age":25}]
		
		for (int i = 0; i < jarr.size(); i++) {
			JSONObject job = (JSONObject)jarr.get(i);
			// Object java.util.ArrayList.get(int index)
			
			String name = (String)job.get("name");
			// Object java.util.HashMap.get(Object key)
			
			int age = ((Long)job.get("age")).intValue();
			
			System.out.println("name : " + name + ", age : " + age);
		}
		
		// 정상적으로 처리가 되었다면, 클라이언트로 성공값을 보내야 한다
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
// =========================================================================================================
}