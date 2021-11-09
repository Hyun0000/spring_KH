package com.mycompany.kiwi.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.kiwi.board.model.vo.Board;

@Repository("boardDao")
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	// service단에서 throws Exception을 하고있으므로 dao에서도 throws 처리를 해야한다.
	public List<Board> getBoardList() throws Exception {
		return sqlSession.selectList("BoardNS.boardlistRM");
	}
}