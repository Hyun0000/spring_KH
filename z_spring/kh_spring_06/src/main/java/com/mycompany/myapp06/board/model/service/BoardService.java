package com.mycompany.myapp06.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp06.board.model.dao.BoardDao;
import com.mycompany.myapp06.board.model.vo.Board;

@Service("boardService")
public class BoardService {
	@Autowired private BoardDao boardDao;
	
	public List<Board> selectAllBoard() {
		return boardDao.selectAllBoard();
	}
}
