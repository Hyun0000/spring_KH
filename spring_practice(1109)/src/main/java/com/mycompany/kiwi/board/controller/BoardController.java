package com.mycompany.kiwi.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.kiwi.board.model.service.BoardService;
import com.mycompany.kiwi.board.model.vo.Board;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	// 다형성을 이용한 것이기에 자료형을 BoardServiceImpl로 할 필요없다.
	// BoardServiceImpl로 해도 문제는 없지만 보통 위와 같이 작성한다.
// ============================================================================================
	// 전체 글 조회
	@RequestMapping(value = "boardlist", method = RequestMethod.GET)
	public ModelAndView selectAllBoard(ModelAndView mv) {
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
			mv.addObject("msg", "게시글 읽기에 문제가 발생하였습니다. 페이지를 다시 열어주세요");
			
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
	 * 하지만 method 방식이 다르기에 비록 같은 URL이더라도 view역할을 하는 method와 실질적 기능을 수행하는 method로 나눌 수 있다.
	 */
	
	// 현재 <form>이 (method="post")이기에 이 method만 있으면 page를 정확히 찾을 수 없다.
	// 이 method가 일종의 viewServlet 역할을 하는 것이다.
	@RequestMapping(value = "boardwrite", method = RequestMethod.GET)
	public ModelAndView write(ModelAndView mv) {
		mv.setViewName("board/boardwrite");
		return mv;
	}
	
	@RequestMapping(value = "boardwrite", method = RequestMethod.POST)
	public ModelAndView dowrite(ModelAndView mv, HttpServletRequest request, Board bvo) {
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
		
		// 자세히
		// spring이 Board에 있는 각 field의 이름을 scan해서 <input>의 name 값과 VO의 필드명이 같으면
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
		mv.setViewName("board/boardlist");
		return mv;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}