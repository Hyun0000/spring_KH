//package com.mycompany.myapp06.board.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.mycompany.myapp06.board.model.service.BoardService;
//
//@Controller
//public class BoardInsert {
//	@Autowired
//	private BoardService boardService;
//	
//	@RequestMapping(value = "boardinsert", method = RequestMethod.GET)
//	public String boardInsert() {
//		Map<String, Object> insertMap = new HashMap<String, Object>();
//		insertMap.put("title", "안녕하세요300");
//		insertMap.put("writer", "박정원300");
//		insertMap.put("content", "오늘 비가 와서 킹받네요");
//		
//		boardService.boardInsert(insertMap);
//		
//		 return "redirect:boardlist";
//	}
//}
