package com.mycompany.myapp06.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.myapp06.board.model.service.BoardService;
import com.mycompany.myapp06.board.model.service.BoardServiceImpl;
import com.mycompany.myapp06.board.model.vo.Board;
import com.mycompany.myapp06.member.model.vo.Member;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "boardlist", method = RequestMethod.GET)
	public ModelAndView selectAllBoard(ModelAndView mv) {
		String viewPage = "";
		List<Board> volist = null;
		try {
			volist = boardService.getBoardList();
			viewPage = "board/boardlist";
			mv.addObject("volist", volist);
		} catch (Exception e) {
			viewPage = "error/commmError";
			mv.addObject("msg", "게시글 읽기에 문제가 발생하였습니다. 페이지를 다시 열어주세요");

			mv.addObject("url", "boardlist");
			e.printStackTrace();
		}
		
		mv.setViewName(viewPage);
		return mv;
	}
// ============================================================================================
	@RequestMapping(value = "/boardwrite", method = RequestMethod.GET)
	public ModelAndView write(ModelAndView mv){
		mv.setViewName("board/boardwrite");
		return mv;
	}
	
	@RequestMapping(value = "/boardwrite", method = RequestMethod.POST)
	public ModelAndView doWrite(ModelAndView mv, Board bvo, Member mvo){
		try {
			boardService.insertBoard(bvo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
// ============================================================================================
//	@Autowired
//	private BoardService boardService;
//	
//	@RequestMapping(value = "/boardlist", method = RequestMethod.GET)
//	public ModelAndView getBoardList(ModelAndView mv){
//		System.out.println("Controller BoardController getBoardList");
//		Board vo = new Board();
//		vo.setBoardNum(111);
//		vo.setBoardTitle("aaaa");
//		List<Board> volist = boardService.getBoardList(vo);
//		mv.addObject("volist", volist);
//		mv.setViewName("board/boardlist");
//		return mv;
//	}
//	@RequestMapping(value = "/boardlistPagin", method = RequestMethod.GET)
//	public String getBoardListPaging(Model m){
//		System.out.println("Controller BoardController getBoardListPaging");
//		return "board/boardlist";
//	}
//	@RequestMapping(value = "/boardwrite", method = RequestMethod.GET)
//	public String write(Model m){
//		
//		return "board/";
//	}
//	@RequestMapping(value = "/read", method = RequestMethod.GET)
//	public String read(Model m){
//
//		return "board/";
//	}
//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public String update(Model m){
//
//		return "board/";
//	}
// ============================================================================================	
}
