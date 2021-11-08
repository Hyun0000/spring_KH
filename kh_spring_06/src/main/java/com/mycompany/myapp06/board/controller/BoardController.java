package com.mycompany.myapp06.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp06.board.model.service.BoardService;
import com.mycompany.myapp06.board.model.vo.Board;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "boardlist", method = RequestMethod.GET)
	public ModelAndView selectAllBoard(ModelAndView mv) {
		System.out.println("나 지금 여기있어 : Controller selectAllBoard");
		
		// 아규먼트 형태로 나오게 해보자
		Board vo = new Board();
		vo.setBoardNum(111);
		vo.setBoardTitle("assasdasd");
		List<Board> volist = boardService.getBoardList(vo);
		
		
		
		mv.addObject("volist", volist);
		mv.setViewName("board/boardlist");
		return mv;
		// views까지는 디폴트로 경로가 생긴다.
	}
	
//	@RequestMapping(value = "boardlist", method = RequestMethod.GET)
//	public String selectAllBoard(Model model) {
//		System.out.println("나 지금 여기있어 : Controller selectAllBoard");
//		System.out.println("boardService.selectAllBoard() : " + boardService.getBoardList());
//		System.out.println("boardService.selectAllBoard().size() : " + boardService.getBoardList().size());
//		List<Board> volist = boardService.getBoardList();
//		
//		model.addAttribute("volist", boardService.getBoardList());
//		return "board/boardlist";
//		// views까지는 디폴트로 경로가 생긴다.
//	}
}
