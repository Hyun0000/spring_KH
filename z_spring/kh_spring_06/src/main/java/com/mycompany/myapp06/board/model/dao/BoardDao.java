package com.mycompany.myapp06.board.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp06.board.model.vo.Board;

@Repository("boardDao")
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<Board> selectAllBoard() {
		return sqlSession.selectList("Board.selectAllBoard");
	}
}
