package com.mycompany.myapp06.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp06.board.model.vo.Board;

@Repository("boardDao")
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<Board> getBoardList() {
		System.out.println("나 지금 여기있어 : Repository selectAllBoard");
		// 실습용
		return sqlSession.selectList("BoardNS.boardlist");
		
		
		// resultMap 사용
		// return sqlSession.selectList("BoardNS.boardlistRM");
		
		// 해쉬맵(키 밸류 형태로 결과가 출력된다.)
		// 키 = 컬럼 이름 / 벨류 = 컬럼에 해당하는 값
		// return sqlSession.selectList("BoardNS.boardlistHM");
		
		// Map<String, Object> map1 = new HashMap<String, Object>();
// ==========================================================================
//		map1.put("bt", "안녕하세요");
//		map1.put("bw", "박정원1");
//		map1.put("bc", "오늘 날씨가 좋아 킹받네요");
		
		// return sqlSession.selectList("BoardNS.searchboardlist", map1);
//		return sqlSession.selectList("BoardNS.searchboardlist2", map1);
// ==========================================================================		
		// 작성자, 내용, 전체, 제목
//		map1.put("category", "작성자");
//		map1.put("searchValue", "박정원1");
		
//		map1.put("category1", "작성자");
//		map1.put("searchValue1", "박정원1");
//		
//		map1.put("category", "내용");
//		map1.put("searchValue", "오늘 날씨가 좋아 킹받네요");
//		return sqlSession.selectList("BoardNS.searchboardlist3", map1);
// ==========================================================================
//		map1.put("bt", "김지유");
////		map1.put("bw", "박정원1");
//		map1.put("bc", "오늘 날씨가 좋아 킹받네요");
//		
//		/*
//		 * map1.put("bt", "김지유");
//		 * map1.put("bc", "오늘 날씨가 좋아 킹받네요");
//		 * 이 순서대로 xml에 보내면 아무것도 찾지 못한다.
//		 * map1.put("bt", "김지유"); 여기서 <when> 조건문이 걸려버리고
//		 * 김지유로 조건 검색을 하지만 일치하는 작성자가 없기 때문이다.
//		 */
//		
//		return sqlSession.selectList("BoardNS.searchboardlist4", map1);
	}
// =============================================================================
	// 글 작성
	public int boardInsert(Map<String, Object> insertMap) {
		return sqlSession.insert("BoardNS.boardInsert", insertMap);
	}
// =============================================================================
	// 글 삭제
	public int boardDelete(Map<String, Object> deleteMap) {
		return sqlSession.insert("BoardNS.boardDelete", deleteMap);
	}
}