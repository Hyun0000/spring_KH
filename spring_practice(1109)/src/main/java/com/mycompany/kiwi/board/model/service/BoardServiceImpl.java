package com.mycompany.kiwi.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.kiwi.board.model.dao.BoardDao;
import com.mycompany.kiwi.board.model.vo.Board;

@Service("BoardService")
// @Annotation을 이용한 transaction AOP 만들기는 이게 끝이다.
// @Transactional(rollbackFor = Exception.class)
// 현재 XML로 @Transaction AOP를 만들었기때문에 이 부분을 주석처리 했다.
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

	@Override
	public List<Board> getBoardList() throws Exception {
		return boardDao.getBoardList();
	}

	@Override
	public List<Board> searchBoardList(Board vo) throws Exception {
		return null;
	}

	@Override
	public int insertBoard(Board vo) throws Exception {
		return 0;
	}

	@Override
	public int updateBoard(Board vo) throws Exception {
		return 0;
	}

	@Override
	public int deleteBoard(Board vo) throws Exception {
		return 0;
	}

	@Override
	public int deleteBoard(int bno) throws Exception {
		return 0;
	}
}
/*
 * - transaction이 해주는 것
 * - insert, updata 작업 등에 대한 commit, rollback 처리를 알아서 해준다. 
 * - 일일이 transaction 관련 코드를 직접 작성하지 않아도 된다.
 * 
 * - Service 단에서 transaction을 꼭 걸어줘야한다.
 * - 하나의 기능에서 insert 작업을 2번한다고 했을때 controller에서 service를 2번 호출(x)
 * - dao에서 insert 작업을 2번 (x)
 * - Service 단에서 해당 작업을 해야한다. & 이에 대한 transaction 관리도 해야한다.
 * - transaction은 pom.xml에 따로 추가할 것이 없다.
 */