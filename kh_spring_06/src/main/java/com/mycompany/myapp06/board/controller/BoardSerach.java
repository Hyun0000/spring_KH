package com.mycompany.myapp06.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.myapp06.board.model.service.BoardService;

@Controller
public class BoardSerach {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "boardSerach", method = RequestMethod.GET)
	public String boardSearch(Model model) {
		model.addAttribute("boardSerach", boardService.searchBoard());
		return "board/boardSerach";
	}
}
