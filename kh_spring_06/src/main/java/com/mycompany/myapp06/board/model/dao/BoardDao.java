package com.mycompany.myapp06.board.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp06.board.model.vo.Board;

@Repository("boardDao")
public class BoardDao {
	@Autowired
	private SqlSession sqlSession;
	
	public List<Board> getBoardList() throws Exception {
// ==========================================================================
		// 실습용
		return sqlSession.selectList("BoardNS.boardlist");
// ==========================================================================		
		// 1. <resultMap> 사용
		// <select id="boardlistRM" resultMap="boardRM">
		
		// return sqlSession.selectList("BoardNS.boardlistRM");
// ==========================================================================
		// 2. hashmap 사용
		// <select id="boardlistHM" resultType="hashmap">
		// key : value 형태로 결과가 출력된다.
		// key = column 이름 / value = column에 해당하는 값
		// return sqlSession.selectList("BoardNS.boardlistHM");
// ==========================================================================
		// 3. 조건을 주고 검색(조건 : title, writer, Content) / <choose> ~ <when>
		// <select id="searchboardlist" parameterType="map" resultType="hashmap">
		// Map<String, Object> map1 = new HashMap<String, Object>();
		// map1.put("bt", "안녕하세요");
		// map1.put("bw", "박정원1");
		// map1.put("bc", "오늘 날씨가 좋아 킹받네요");
		// return sqlSession.selectList("BoardNS.searchboardlist", map1);
		
// ==========================================================================
		// 4. 조건을 주고 검색(조건 : title, writer, Content) / <if>
		// Map<String, Object> map1 = new HashMap<String, Object>();
		// <select id="searchboardlist2" parameterType="map" resultType="hashmap">
		// map1.put("bt", "안녕하세요");
		// map1.put("bw", "박정원1");
		// map1.put("bc", "오늘 날씨가 좋아 킹받네요");
		// return sqlSession.selectList("BoardNS.searchboardlist2", map1);
// ==========================================================================
		// 5. 검색 코드 예시 ==> <if>
		// Map<String, Object> map1 = new HashMap<String, Object>();
		// ============= 검색 필드 같은 역할 =============
		// map1.put("category", "작성자");
		// map1.put("category", "내용");
		// map1.put("category", "제목");
		// ============= 검색 필드 같은 역할 =============
		// map1.put("searchValue", "박정원1");
		// return sqlSession.selectList("BoardNS.searchboardlist3", map1);
// ==========================================================================
		// 6. 잘못된 검색 코드 예시 ==> <choose> ~ <when>
		// Map<String, Object> map1 = new HashMap<String, Object>();
		
		// map1.put("bt", "김지유");
		// 현재 Board의 title에는 '김지유'라는 제목이 없는 상태이다.
		// 만약 이 순서대로 mapper.xml에 보내면 아무것도 찾지 못한다.
		// 현재 <when> 절의 조건이 (test="bt != null)이기에 bt가 null이 아니기만 하면 된다.
		// map1.put("bt", "김지유") : 이렇게 값을 보내면 <when> 절의 조건을 만족하는 것이기에
		// 결국 아래의 bw, bc가 DB에 있는 값이라고 해도 원하는 결과를 얻지 못한다.
		// <when>은 일치하는 딱 하나만 sql문에 반영되고 그 밑의 조건은 무시하기 때문이다.
		// 따라서 검색 기능을 구현할 때는 <if>절을 써야한다.
		// map1.put("bw", "박정원1");
		// map1.put("bc", "오늘 날씨가 좋아 킹받네요");
		// return sqlSession.selectList("BoardNS.searchboardlist4", map1);
	}
// =============================================================================
	// 검색 기능
	public List<Board> searchBoard() {
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("searchField", "작성자");
		map1.put("title", "안녕하세요");
		map1.put("writer", "박정원1");
		map1.put("content", "오늘 날씨가 좋아 킹받네요");
		return sqlSession.selectList("BoardNS.searchBoard", map1);
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
// =============================================================================
	// 페이징 처리한 글 조회
	public List<Board> boardSelectPaging() {
		RowBounds rowBounds = new RowBounds(10, 20);
		return sqlSession.selectList("BoardNS.boardSelectPaging", null, rowBounds);
	}
}