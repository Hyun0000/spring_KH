package com.mycompany.myapp06.board.model.service;

import java.util.List;
import com.mycompany.myapp06.board.model.vo.Board;

public interface BoardService {
	
	public List<Board> getBoardList() throws Exception;
// =============================================================================
	public List<Board> searchBoardList(Board vo) throws Exception;
// =============================================================================
	public int insertBoard(Board vo) throws Exception;
// =============================================================================
	public int updateBoard(Board vo) throws Exception;
// =============================================================================
	// 오버로딩(조건을 다양하게 주기 위해)
	public int deleteBoard(Board vo) throws Exception;
	public int deleteBoard(int bno) throws Exception;

// ======================== 실습용 ========================
//	// 글 작성
//	public int boardInsert(Map<String, Object> insertMap);
//// =============================================================================
//	// 글 삭제
//	public int boardDelete(Map<String, Object> deleteMap);
//// =============================================================================
//	// 검색 기능
//	public List<Board> searchBoard();
//// =============================================================================
//	// 페이징 처리한 글 조회
//	public List<Board> boardSelectPaging();
}