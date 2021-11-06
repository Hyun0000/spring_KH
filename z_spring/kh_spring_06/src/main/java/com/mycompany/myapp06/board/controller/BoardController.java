package com.mycompany.myapp06.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.myapp06.board.model.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "boradlist", method = RequestMethod.GET)
	public String selectAllBoard(Model model) {
		model.addAttribute("boradlist", boardService.selectAllBoard());
		System.out.println("boardService.selectAllBoard() : " + boardService.selectAllBoard());
		System.out.println("boardService.selectAllBoard().size() : " + boardService.selectAllBoard().size());
		return "boradlist";
	}
}
