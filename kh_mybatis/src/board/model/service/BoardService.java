package board.model.service;

import java.util.ArrayList;

import board.model.dao.BoardDao;
import board.model.vo.Board;

public class BoardService {
	public BoardService() {}
// ====================================================================================================
	public int getListCount() {
		return new BoardDao().getListCount();
	}
// ====================================================================================================
	// 1. Rowbounds 이용
	public ArrayList<Board> selectList(int currentPage, int limit) {
		return new BoardDao().selectList(currentPage, limit);
	}
	// ====================================================================================================
	// 2. ROWNUM 사용
	public ArrayList<Board> selectListRownum(int currentPage, int limit) {
		return new BoardDao().selectListRownum(currentPage, limit);
	}
}
