package com.mycompany.myapp06.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp06.board.model.dao.BoardDao;
import com.mycompany.myapp06.board.model.vo.Board;

@Service("boardService")
@Transactional(rollbackFor = Exception.class)
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

	@Override
	public List<Board> getBoardList() throws Exception {
		return boardDao.getBoardList();
	}

	@Override
	public List<Board> searchBoardList(Board vo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertBoard(Board vo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBoard(Board vo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBoard(Board vo) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBoard(int bno) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
//	// 글 작성
//	public int boardInsert(Map<String, Object> insertMap) {
//		return boardDao.boardInsert(insertMap);
//	}
//// =============================================================================
//	// 글 삭제
//	public int boardDelete(Map<String, Object> deleteMap) {
//		return boardDao.boardDelete(deleteMap);
//	}
//// =============================================================================
//	// 검색 기능
//	public List<Board> searchBoard() {
//		return boardDao.searchBoard();
//	}
//// =============================================================================
//	// 페이징 처리한 글 조회
//	public List<Board> boardSelectPaging() {
//		return boardDao.boardSelectPaging();
//	}
}