package com.mycompany.myapp06.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.myapp06.board.model.service.BoardService;

@Controller
public class BoardDelete {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "boradDelete", method = RequestMethod.GET)
	public String boardDelete(Model model) {
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("title", "안녕하세요300");
		deleteMap.put("writer", "박정원300");
		
		boardService.boardDelete(deleteMap);
		
		if (boardService.boardDelete(deleteMap) == 1) {
			System.out.println("삭제 성공");
		}
		
		return "redirect:boardlist";
	}
}