package com.mycompany.myapp06.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.myapp06.board.model.dao.BoardDao;
import com.mycompany.myapp06.board.model.vo.Board;

@Service("boardService")
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	public List<Board> getBoardList(Board vo) {
		System.out.println("나 지금 여기있어 : Service selectAllBoard");
		return boardDao.getBoardList();
	}
// =============================================================================
	// 글 작성
	public int boardInsert(Map<String, Object> insertMap) {
		return boardDao.boardInsert(insertMap);
	}
// =============================================================================
	// 글 삭제
	public int boardDelete(Map<String, Object> deleteMap) {
		return boardDao.boardDelete(deleteMap);
	}
}
