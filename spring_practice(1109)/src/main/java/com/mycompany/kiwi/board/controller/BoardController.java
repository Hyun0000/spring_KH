package com.mycompany.kiwi.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.kiwi.board.model.service.BoardService;
import com.mycompany.kiwi.board.model.vo.Board;
import com.mycompany.kiwi.member.model.vo.Member;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	// 다형성을 이용한 것이기에 자료형을 BoardServiceImpl로 할 필요없다.
	// BoardServiceImpl로 해도 문제는 없지만 보통 위와 같이 작성한다.
// ============================================================================================
	// 전체 글 조회
	// 
	/*
	 * - mv.setViewName("redirect:/boardlist")로 인해 dowrite() method 실행 종료 후
	 * 바로 이 method로 넘어왔다.
	 * 
	 * - dowrite() method에서 넘겨준 값을 받을 때는 아래와 같은 방법을 이용한다.
	 * 
	 * [ dowrite() method에서 넘겨준 값 ]
	 * mv.addObject("msg", "리다이렉트 방식으로도 메세지가 전달된다.");
	 * mv.addObject("pagenum", "1"); 
	 */
	@RequestMapping(value = "boardlist", method = RequestMethod.GET)
	public ModelAndView selectAllBoard(ModelAndView mv
			, @RequestParam(name="msg") String msg
			, @RequestParam(name="pagenum") int pagenum
			) {
		// cf)
		// @RequestParam(name="msg") String msg,
		// @RequestParam(name="pagenum") int pagenum,
		// 이것들의 값이 무엇인지 일일이 찍을 필요없다. AOP를 이용하면 된다.
		
		String viewPage = "";
		// viewpage를 아예 변수에 지정하기도 한다.
		
		List<Board> volist = null;
		
		// Controller에서는 Exception을 다른 곳으로 던지면 안 된다.
		// Controller에서 던지면 Exception이 톰캣으로 가버린다.
		try {
			viewPage = "board/boardlist";
			volist = boardService.getBoardList();
			mv.addObject("volist", volist);
			mv.addObject("url", "boardlist");
		} catch (Exception e) {
			viewPage = "error/errorpage";
			// mv.addObject("msg", "게시글 읽기에 문제가 발생하였습니다. 페이지를 다시 열어주세요");
			
			// errorPage 이동시 다른 곳으로 이동할 url 값도 같이 보낸다.
			mv.addObject("url", "boardlist");
			e.printStackTrace();
		}
		mv.setViewName(viewPage);
		return mv;
		// views 폴더까지는 디폴트로 경로가 생긴다.
	}
// ============================================================================================
	// 화면단으로부터 data 넘겨 받는 연습
	/*
	 * 현재 write() method와 dowrite() method의 URL이 같다.
	 * 하지만 method 방식(GET, POST)이 다르기에 비록 같은 URL이더라도
	 * view역할을 하는 method / 실질적 기능을 수행하는 method로 나눌 수 있다.
	 */
	
	// 이 method가 일종의 viewServlet 역할을 하는 것이다.
	@RequestMapping(value = "boardwrite", method = RequestMethod.GET)
	public ModelAndView write(ModelAndView mv) {
		mv.setViewName("board/boardwrite");
		return mv;
	}
	
	// 현재 <form>이 (method="post")이지만 아래의 method만 있으면 page를 정확히 찾을 수 없다.
	// why? URL을 이용한 page 이동은 GET 방식을 이용하는 것이기 때문이다.
	// 따라서 URL은 같지만 (RequestMethod.GET)인 method도 하나 만들어야한다.
	@RequestMapping(value = "boardwrite", method = RequestMethod.POST)
	public ModelAndView dowrite(
			@RequestParam(name="boardNum") String bnum,
			ModelAndView mv,
			HttpServletRequest request,
			Board bvo,
			Member mvo,
			@RequestParam(name="report") MultipartFile upfile, // 방법 1
			MultipartHttpServletRequest multi // 방법 2 
			) {
		// MultipartFileHttpServletRequest multi는 예전방식을 그대로 써야한다.
		MultipartFile upfile2 = multi.getFile("reprot");  // 방법 2
		
		// 방법 1
		// pom.xml에 jar를 넣고
		// <beans:bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">으로 
		// <bean>을 만들었으니 화면단에서 받은 파일을 MultipartFile 형태로 형변환이 가능한 것이다.
		// 이걸 저장만 하면 끝, 시마이
		
		// @RequestParam(name="upfile") MultipartFile upfile
		// 파일은 위의 모양으로 들어온다.
		// cf) pom.xml에 파일 관련 라이브러리 추가해야한다.
		
		// servlet은 컨트롤러에서도 multipart/form-data에 맞춰 data를 받은 다음에 그것을 일일이 쪼개야하는데
		// spring은 그것을 알아서 해준다. 단, 해당 기능(라이브러리)을 추가를 미리해야한다.
		
		/*
		 * @RequestParam(name="boardNum") String bnum
		 * - <input type="hidden" name="boardNum" value="${vo.boardNum}">을 통해 실려온 값을 꺼내서 String bnum에 넣어라
		 * - @RequestParam(name="boardNum")을 이용해 값을 꺼낸후 그 값을 String bnum에 넣어주는 것이다.
		 * - 근데 꼭 (String bnum)으로 해야할까?
		 * NO
		 * - @RequestParam(name="boardNum") 'int' bnumd로 적어놓으면 spring이 알아서 형변환 한 후 int bnum에 넣어준다.
		 * - 단, 화면단에서 넘어온 값이 '3A' 처럼 숫자만 있지 않을때는 문제가 발생한다.
		 * - method 자체를 진입을 하지 못하고 형변환 단계에서 멈춰버린다.
		 * 
		 * cf)
		 * 기본적으로 @RequestParam에 실린 값은 무조건 String이다.
		 */
		int bunms = Integer.parseInt(bnum);
		if (bunms == 0) {
			// 새글 쓰기
		} else {
			// 수정하기
		}
		
		// =====================================================================================		
		// 1.
		// <input type="text" name="title">
		// <input type="text" name="content">
		// <input type="submit" value="제출">
		
		// Board vo = new Board();
		// vo.setBoardTitle(request.getParameter("title"));
		// vo.setBoardContent(request.getParameter("content"));
		// 근데 이거 너무 귀찮다. 이런 작업을 하기가 싫다.
		
		// =====================================================================================
		// 2.
		// <input type="text" name="id">
		// <input type="text" name="email">
		// <input type="text" name="boardTitle">
		// <input type="text" name="boardContent">
		// <input type="text" name="boardReadCount">
		
		// <input>의 입력값들을 parameter인 (Board bvo)의 각 field에 알맞게 담고 싶다.
		// 이를 위해서는 아래의 조건을 만족해야한다.
		
		// '<input>의 name 값과 VO의 필드명을 일치'시킨다.
		// 만약 둘이 일치하면 <input>의 각 name에 들어간 value들이 (Board bvo)에서 이름이 동일한 필드에 맞게 'set'된다.
		// 이때 VO class에는 각 필드에 대한 setter method가 있어야한다.
		
		// 자세히
		// spring이 BoardVO class에 있는 각 field의 이름을 scan, <input>의 name 값과 VO의 필드명이 같으면
		// 헤당 name의 value를 field에 넣어준다.(알아서 넣어준다.)
		
		// cf) 이때 <input>의 입력값이 여전히 request에 실려가는건 맞다.
		// 이렇게 되면 사실상 parameter인 (HttpServletRequest request)도 필요없다.
		
		System.out.println("bvo : " + bvo);
		// 단, 이 방식을 이용하면 log를 이용해야지만 사용자로부터 받은 값이 제대로 넘어왔는지 확인 할 수 있다.
		// 이러한 불편함을 해결해주는 것이 'AOP'이다.
		
		try {
			boardService.insertBoard(bvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		* mv.setViewName("board/boardlist"); --> 여기 적히는 값은 jsp 파일의 파일명을 의미한다.
		* 현재 이 방식은 forward 방식이다. 즉, url이 바뀌지 않은채 view화면만 보여준다.
		* 나는 "boardlist"라는 url을 보여주고 싶은데 현재 method의 url인 "boardwrite"가 그대로 남아있는 것이다.
		* 이럴때는 천상 servlet에서의 "sendRedirect"와 같은 방법을 이용해야한다.(boardlist가 url에 나오게 하고싶다.)
		*/
		
		// 이럴때는 아래의 방법을 이용한다.(지금 sendRedirect 하는 것이다.)
		mv.setViewName("redirect:/boardlist"); // 이런 방식이 get 방식 전달이다.(forward도 마찬가지)
		
		// mv.setViewName("board/boardlist")와 달리 앞에 'board'가 없어도 된다.
		// 'redirect:'를 붙이면 servlet-context.xml에서 작성한 아래의 코드가 작동하지 않는다.
		// <beans:property name="prefix" value="/WEB-INF/views/" />
		// <beans:property name="suffix" value=".jsp" />
		// cf) url이 바뀌는 것은 get방식이다. url이 바뀌면서 post인 방식은 없다.
		
		// 대신 'HandlerMapping'으로 가서 boardlist라는 url을 value로 가지고 있는 method가 있는지 
		// 확인한 후 그에 해당하는 mapping method로 이동한다.
		// 현재 controller class에서는 아래의 method가 mv.setViewName("redirect:/boardlist")에 해당한다.
		
		// @RequestMapping(value = "boardlist", method = RequestMethod.GET)
		// public ModelAndView selectAllBoard(ModelAndView mv) {}
		
		// Servlet에서는 sendRedirect하면서 동시에 data를 전달하고 싶으면 session을 이용하거나 
		// sendRedirct(url?a=~~&b=~~)과 같은 방식을 이용했다.
		
		// 하지만 spring은 위와같은 방법없이 redirect:를 써도 data 전달이 가능하다.
		// 이 방식을 그대로 쓰면 된다.
		mv.addObject("msg", "리다이렉트 방식으로도 메세지가 전달된다.");
		mv.addObject("pagenum", "1");
		// 이 data들은 URL의 ? 뒤에 실려간다.
		
		// mv.addObject("boardVo", bvo);
		// URL을 이용한 GET 방식으로 data가 전달되기 때문에 이런 형태는 갈 수 없다.(get은 대량으로 data를 보낼 수 없다.)
		// 굳이 보내고 싶다면 아래와 같은 방법을 이용하면 된다.
		
		// mv.addObject("boardVo", String.valueOf(bvo));
		// 혹은
		// JSON으로 toString해서 던지고 받는 쪽도 JSON으로 받으면 된다.
		return mv;
	}
// ============================================================================================
	// ajax
	@RequestMapping(value = "boardwrite2", method = RequestMethod.POST)
	@ResponseBody
	// @ResponseBody : 나는 ajax를 이용한다.
	public String dowrite2(
			@RequestParam(name="boardNum") String bnum,
			ModelAndView mv, HttpServletRequest request, Board bvo, Member mvo) {
		
		int bunms = Integer.parseInt(bnum);
		if (bunms == 0) {
			// 새글 쓰기
		} else {
			// 수정하기
		}
		
		System.out.println("bvo : " + bvo);
		
		try {
			boardService.insertBoard(bvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mv.addObject("msg", "리다이렉트 방식으로도 메세지가 전달된다.");
		mv.addObject("pagenum", "1");
		
		// ajax를 호출한 succes로 전달될 data를 return에 적는다
		// 이 경우 뷰리졸버로 가지 않는다.
		return "";
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}