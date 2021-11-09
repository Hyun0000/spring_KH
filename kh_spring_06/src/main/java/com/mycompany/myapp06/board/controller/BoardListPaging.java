//package com.mycompany.myapp06.board.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.mycompany.myapp06.board.model.service.BoardService;
//
//@Controller
//public class BoardListPaging {
//	@Autowired
//	private BoardService boardService;
//	
//	@RequestMapping(value = "boardpaging", method = RequestMethod.GET)
//	public ModelAndView boardSelectPaging() {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("boardpaging", boardService.boardSelectPaging());
//		modelAndView.setViewName("board/boardpaging");
//		return modelAndView;
//	}
//}