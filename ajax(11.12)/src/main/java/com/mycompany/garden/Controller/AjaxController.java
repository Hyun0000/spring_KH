package com.mycompany.garden.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mycompany.garden.ajax.model.vo.Sample;
import com.mycompany.garden.ajax.model.vo.User;

@Controller
public class AjaxController {
// =========================================================================================================
	// 첫 page load
	// 기본적으로 GET 방식으로 page를 load한 후 load가 끝나면 ajax로 data를 가져와 화면에 뿌리게 한다. 
	// 이렇게 되면 해당 jsp page에서는 EL도 필요없게 된다.(물론 필요하면 써야지)	
	@RequestMapping(value = "ajaxtest", method = RequestMethod.GET)
	public ModelAndView testAjax(ModelAndView mv) {
		mv.setViewName("ajaxtest");
		return mv;
	}
// =========================================================================================================
	// 첫 page load 후 (window.onload event)를 이용해 ajax로 data를 가져온다.
	@RequestMapping(value = "ajaxtest", method = RequestMethod.POST)
	@ResponseBody // 이게 없으면 ajax로 data를 보낼 수 없다.
	public String testAjaxDate() {
		JSONObject job = new JSONObject();
		job.put("k1", 1);
		return job.toJSONString();
	}	
// =========================================================================================================
	// ajax1(옛날 방식)
	@RequestMapping(value = "test1.do", method = RequestMethod.POST)
	public void test1Method(
			// @RequestParam("name") String name, @RequestParam("age") int age, 이렇게 한 개씩 꺼내줄수도 있다.
			// (@RequestParam("name")의 (괄호)에는 <input>의 name 속성값을 작성)
			// @RequestParam("age") int age는 spring이 알아서 자동 형변환 해준다.
			// 하지만 만약 이 과정에서 오류가 발생하면 method 자체도 진입하지 (X) & 오류메세지도 뜨지 않는다.
			Sample vo, HttpServletResponse response, HttpSession session) throws IOException {
			// Sample vo에 (name : "신사임당", age : 47)이라는 data가 담겨들어온다.
			// cf) ﻿<input>의 name값과 VO의 필드명이 같으면 자동으로 VO의 필드값으로 set 된다. 당연히 자료형도 같아야한다.
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
		// job.toJSONString() --> ajax의 success로 간다.
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
	// ajax3 - simple-json
	@RequestMapping(value = "test3.do", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
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
		
		// simple-json은 반드시 아래의 과정을 거쳐야한다.
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
	// ajax3-1 - Gson
	@RequestMapping(value = "test3-1.do", method = RequestMethod.POST, produces="text/plain;charset=UTF-8")
	public void test3_1Method(HttpServletResponse response) throws IOException {
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("u1234", "p1234", "apple", 25, "h1234@kh.org"));
		list.add(new User("u2345", "p2345", "kiwi", 33, "p2345@kh.org"));
		list.add(new User("u3456", "p3456", "grape", 45, "j3456@kh.org"));
		
		Gson gson = new GsonBuilder().create();
		
		String gsonStr = gson.toJson(list);
		PrintWriter out = response.getWriter();

		out.append(gsonStr);
		out.flush();
		out.close();
	}
// =========================================================================================================
	// ajax4
	@RequestMapping(value = "test4.do", method = RequestMethod.POST)
	public ModelAndView test4Method(ModelAndView mv) throws UnsupportedEncodingException {
		logger.info("test4Method() run...");
		Sample samp = new Sample();
		
		System.out.println("samp : " + samp);
		samp.setName(URLEncoder.encode("손승완", "utf-8"));
		
		// map 객체를 ModelAndView 에 담아서 리턴한다
		Map<String, Sample> map = new HashMap<String, Sample>();
		map.put("samp", samp);
		
		// controller의 기본적인 forward 방식이다.
		mv.addAllObjects(map);
		// view 지정 : "jsonView"를 <bean>으로 등록하고 해당 <bean>의 id를 'jsonView'로 지정해야 한다.
		// cf) jsonView라는 이름의 jsp 파일이 있는것이 아니다.
		mv.setViewName("jsonView");
		return mv; // ajax 는 map 형태의 data를 json 객체 형태로 받는다.
	}
// =========================================================================================================
	// ajax5
	@RequestMapping(value = "test5.do", method = RequestMethod.POST)
	public ResponseEntity<String> test5Method(HttpServletRequest request, @RequestBody String param) throws Exception {
		// ajax에서 [ data : JSON.stringify(job) ] 방식으로 data를 보내면 controller에서는
		// [ @RequestBody String param ] 이렇게 받는다.(request에 실려오는 애들은 무조건 string이다.)
		// 이 param을 까보면 json 모양이다.
		
		logger.info("test5Method() run...");
		request.setCharacterEncoding("utf-8");
		
	// ====================== parsing - simple_json ======================
		// simple-json
		// 전송된 문자열(JSON.stringify(job))을 json 객체로 변환 처리
		JSONParser parser = new JSONParser();
		JSONObject job = (JSONObject)parser.parse(param);
		System.out.println("parsing - simple_json : " + job);
		// job = {"name":"강감찬","age":33}
		
		// Object java.util.HashMap.get(Object arg0)
		String name = (String)job.get("name");
		int age = ((Long)job.get("age")).intValue();
		// view 단에서 보낸 data를 int로 못꺼낼 가능성이 있기 때문에 애초에 (Long)으로 꺼내는 것이다.
		// (Long)으로 꺼낸후 intValue 만큼만 꺼내준다. --> intValue()
		// 이게 싫으면 애초에 js에서 유효성 검사를 하면 된다.
		System.out.println("parsing - simple_json name: " + name);
		System.out.println("parsing - simple_json age: " + age);
	// ====================== parsing - gson ======================
		// gson.... 이것도 그닥 좋은거 같지 않은데??
		JsonParser jsonParser = new JsonParser();
		// import com.google.gson.JsonParser;
		JsonElement jsonElement = jsonParser.parse(param);
		System.out.println("parsing - gson : " + jsonElement);
		
		String name2 = jsonElement.getAsJsonObject().get("name").getAsString();
		int age2 = jsonElement.getAsJsonObject().get("age").getAsInt();
		System.out.println("parsing - gson name : " + name2);
		System.out.println("parsing - gson : " + age2);
		
		
		System.out.println("name : " + name + ", age : " + age);
		
		//정상 완료됨을 클라이언트로 성공값을 보내야 한다.
		return new ResponseEntity<String>("apple", HttpStatus.OK);
		// return new ResponseEntity<String>("apple", HttpStatus.OK); 이라고 적으면
		// [ success : function(result) {} ] 의 (result) = apple 이 된다.
	}
// =========================================================================================================
	// ajax6 - view 단으로 넘겨 받은 객체 배열을 simple-json을 이용해 parsing
	@RequestMapping(value = "test6.do", method = RequestMethod.POST)
	@ResponseBody
	// @ResponseBody가 있든없든 결과는 동일하다. @ResponseBody 쓸거면 반환 타입을 String으로 해라
	// @ResponseBody를 쓰면 ResponseEntity<String>를 굳이 쓸 필요없다.
	
	// 1. 반환타입 = ResponseEntity<String>
	// public ResponseEntity<String> test6Method(HttpServletRequest request, @RequestBody String param) throws Exception {
	
	// 2. 반환타입 = String
	public String test6Method(HttpServletRequest request, @RequestBody String param) throws Exception {
		logger.info("test6Method() run...");
		request.setCharacterEncoding("utf-8");
		
		System.out.println("param : " + param);
		JSONArray jarr = (JSONArray)new JSONParser().parse(param);
		// JSONArray : A JSON array : JSONObject supports java.util.List interface.
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
		
		// 1. 반환타입 = ResponseEntity<String>
		// 정상적으로 처리가 되었다면, 클라이언트로 성공값을 보내야 한다
		// return new ResponseEntity<String>("success", HttpStatus.OK);
		
		// 2. 반환타입 = String
		// 반환타입이 string이 었을때
		 return "success";
	}
// =========================================================================================================
	// ajax7 - view 단으로 넘겨 받은 객체 배열을 gson을 이용해 parsing
	@RequestMapping(value = "test7.do", method = RequestMethod.POST)
	@ResponseBody
	public String test7Method(@RequestBody String param) throws Exception {
		logger.info("test7Method() run...");

		System.out.println("param : " + param);
		// param : [{"name":"이 이","age":30},{"name":"신사임당","age":47},{"name":"황진이","age":25}] --> json 모양
		Gson gson = new Gson();
		
		// Object를 이용해 개인적으롤 parsing해 봤다.
//		Object[] strArr = gson.fromJson(param, Object[].class);
//		System.out.println("strArr : " + strArr);
//		List<Object> reqVoList2 = Arrays.asList(strArr);
//		System.out.println("reqVoList2 : " + reqVoList2);
		
		// 방법1
		// 화면으로부터 받은 객체 배열을 Sample 형태의 배열로 담아보자 
		Sample[] reqVoArray = gson.fromJson(param, Sample[].class);
		
		// 배열로 되어 있는 것을 list 모양으로 바꾸자
		List<Sample> reqVoList = Arrays.asList(reqVoArray);
		
		System.out.println("reqVoArray : " + reqVoArray);
		System.out.println("reqVoList : " + reqVoList);
		// reqVoList : [Sample [name=이 이, age=30], Sample [name=신사임당, age=47], Sample [name=황진이, age=25]]
		// [name=이 이, age=30] --> SampleVO class의 toString() 때문에 이렇게 뿌려지는 것이다. 배열이 아니다.
		// 양끝의 []는 List 때문에 뿌려지는 것이다.
		
		// 방법2 - 버전이 안 맞음
		// List<Sample> reqVoList2 = gson.fromJson(param, new TypeToken<List<sample>>(){}.getType());
		
		// List
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("u1234", "p1234", "홍길동", 25, "h1234@kh.org"));
		list.add(new User("u2345", "p2345", "박문수", 33, "p2345@kh.org"));
		list.add(new User("u3456", "p3456", "장영실", 45, "j3456@kh.org"));
		
		// List + 각종 data를 채운 map
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("volist", list);
		map1.put("startNum", 1);
		map1.put("endNum", 15);
		
		return gson.toJson(map1);
	}
// =========================================================================================================
	// ajax8 - (spring으로 json을 cover) : list나 map 정도는 굳이 json을 안써도 spring이 알아서 json 모양으로 바꿔준 후 전달해준다.
	@RequestMapping(value = "test8.do", method = RequestMethod.POST)
	@ResponseBody
	// 1. ArrayList<User> 형태
	// public ArrayList<User> test8Method(@RequestBody String param) throws Exception {
	
	// 2. Map<String, Object> 형태
	public Map<String, Object> test8Method(@RequestBody String param) throws Exception {
		
		// 1. ArrayList<User> 형태
		// List
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User("u1234", "p1234", "홍길동", 25, "h1234@kh.org"));
		list.add(new User("u2345", "p2345", "박문수", 33, "p2345@kh.org"));
		list.add(new User("u3456", "p3456", "장영실", 45, "j3456@kh.org"));
	
		// 2. Map<String, Object> 형태
		// List + 각종 data를 채운 map
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("volist", list);
		map1.put("startNum", 1);
		map1.put("endNum", 15);
		
		// 1. ArrayList<User> 형태
		// return list;
		// 이렇게 해도된다.
		
		// 2. Map<String, Object> 형태
		return map1;
		// 이렇게 해도된다.
	}
}