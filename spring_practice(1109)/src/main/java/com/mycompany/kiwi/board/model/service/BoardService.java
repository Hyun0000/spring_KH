package com.mycompany.kiwi.board.model.service;

import java.util.List;

import com.mycompany.kiwi.board.model.vo.Board;

// interface에는 @Annotation을 따로 작성하지 않는다.
public interface BoardService {
	// throws Exception : controller가 오류를 처리하게 한다.
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
}
/*
 * interface
 * - 공통의 method, field의 형태와 이름을 정해 중복 작업, 중복 이름, 혼돈 방지
 * - 꼭 필요한 method를 만들 수 있도록 유도한다.
 * - 따라서 Service는 비즈니스 모델로 가기전에 interface를 먼저 만들어야한다.
 * - Dao에도 해당 작업을 할 수 있다.
 */