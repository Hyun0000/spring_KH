package board.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import board.model.vo.Board;


public class BoardDao {
	public BoardDao() {}
// ====================================================================================================
	private SqlSessionFactory getSqlSessionFactory() {
		String resource = "mybatis/mybatis-config.xml";
		SqlSessionFactory factory = null;
		try {
			InputStream inputStream = Resources.getResourceAsStream(resource);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return factory;
	}
// ====================================================================================================
	public int getListCount() {
		// 총 게시글 개수를 리턴하는 메소드
		int listCount = 0;
		// resultType="int"로 쓰면 int listCount = 0;가 아니라
		// Integer listCount = 0;로 작성해야한다.
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession(false);
			listCount = session.selectOne("Board.listCount");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return listCount;
	}
// ====================================================================================================
	public ArrayList<Board> selectList(int currentPage, int limit) {
		// 1. Rowbounds를 사용하는 경우(페이지 단위로 게시글 목록 조회)
		
		ArrayList<Board> list = null;
		SqlSession session = null;
		
		int startRow = (currentPage - 1) * limit;
		try {
			session = getSqlSessionFactory().openSession(false);
			// 대상의 '시작 행(offset)'과 '제한개수(limit)'를 인자로 Rowbounds 객체를 생성해 XML에 전달한다.
			RowBounds row = new RowBounds(startRow, limit);
			
			// Parameter Object가 없으므로 가운데 인자를 null로 하여 전달한다.
			list = new ArrayList<Board>(session.selectList("Board.selectAll", null, row));
			// dao의 selectList method의 반환 타입은 현재 ArrayList<Board>이다.
			// 이때 SqlSession 객체에서 제공하는 selectList() method의 반환타입은 무조건 List이다.
			// 따라서 부모인 List를 ArrayList<Board>모양으로 형변환해야 return이 가능하다. 
			
			// 즉, '다운캐스팅'을 해야한다는 것이다. 근데 왜 아래와 같은 모습이 아닐까?
			// list = (ArrayList<Board>)(session.selectList("Board.selectAll", null, row));
			
			// (session.selectList("Board.selectAll", null, row))가 List 모양으로 return을 하는건 맞다.
			// 이때 문제는 실질적 data가 담겨있는 List가 아니라 List의 '주소'만 준다는 것이다.
			// why?
			
			// InputStream을 이용해 DB를 연결하고 data를 읽는 것이기에 DB에서 가져온 data는 메모리가 아니라
			// InputStream이 사용하는 임시 cache에 저장된다. 그래서 List의 '주소'만 준다는 것이다. 
			// 즉, DB에서 가져온 data가 Heap, stack 등과 같은 실질적인 메모리에 저장되는 것이 아니라 cache에만 '임시로 저장'된다는 것이다. by InputStream
			
			// 따라서 주소만 있고 실질적인 내용물은 아직 메모리에 저장되지 않았기에 다운캐스팅을 적용할 수가 잆는 것이다.
			// why? 캐스팅을 위해서는 data가 어느 영역이 됐건간에 반드시 '메모리'에 저장이 돼야한다. 
			// (주소만 있지 실질적 내용물이 아직 메모리에 저장되지 않은 것이기에 data를 찾을 수 없는 것이다.)
			// 그렇기에 반드시 new를 이용해 실질적인 메모리 저장 공간을 만들어줘야한다.
			// 이때 new를 통해 생성함과 동시에 ArrayList<Board>를 적어 다운캐스팅도 동시에 진행한다.
			// session이 InputStream을 통해 탄생한 것이기 때문에 반드시 한 번은 new를 이용해 저장 공간을 만들어줘야한다.
			
			// 결론 : MyBatis는 IOstream을 사용하기에 DB에서 가져온 data를 메모리를 저장하는 작업을 반드시 한 번은 꼭해줘야한다.
			// 그게 new 연산자를 이용하는 것이다.
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return list;
	}
// ====================================================================================================
	public ArrayList<Board> selectListRownum(int currentPage, int limit) {
		// 2. ROWNUM을 사용하는 경우(페이지 단위로 게시글 목록 조회)
		
		// 직접 시작 행과 끝나는 행을 계산하여 Map의 형태로 값을 전달한다.
		ArrayList<Board> list = null;
		SqlSession session = null;
		
		// ROWNUM을 이용하는 것이기에 직접 rownum의 범위를 계산한다.
		int startRow = (currentPage - 1) * limit + 1; // 시작 행
		int endRow = startRow + limit - 1; // 끝나는 행
		
		HashMap<String, Integer> boardPage = new HashMap<String, Integer>();
		boardPage.put("start", startRow);
		boardPage.put("end", endRow);
		try {
			session = getSqlSessionFactory().openSession(false);
			// 생성한 HaspMap을 selectList() 메소드의 인자로 전달한다
			list = new ArrayList<Board>(session.selectList("Board.selectAllRownum", boardPage));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;
	}
}
