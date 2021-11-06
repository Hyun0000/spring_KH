package ggg.hhh.ddd;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("memberDao")
public class MemberDao {

	@Autowired SqlSession sqlSession;
	// 이미 만들어진 객체들 중에서 가져오고 싶은 객체의 '이름(id)'을 가져와서 쓰는 것이다.(객체를 연결해준다.)
	// sqlSession 이름은 
	/*
	 * (id="sqlSession")와 동일해야한다.
	 * <beans:bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
	 * <beans:constructor-arg ref="sqlSessionFactory" /> <!-- sqlSession은
	 * sqlSessionFactory를 가지고 만든다. --> <!-- sqlSession : 이 이름을 dao에서
	 * 사용한다.(sqlSession이 객체 이름이다.) --> </beans:bean>
	 */
	
	// 매 dao class마다 new를 이용해 sqlSession을 만들 필요가 없다.
	// 미리 객체를 만들어 놓았기에 그것을 가져다 쓰기만 하면 되는것이다. --> 공통 모듈을 만든 것이다.
	// 이것이 리소스 관리에도 좋다.
	
	public Member selectMember(Member m) {
		return sqlSession.selectOne("Member.loginCheck", m.getId()); // namespace를 이용하여 DB 접근
	}
	
// ========================================================================================
	public Member checkIdDup(Member m) {
		return sqlSession.selectOne("Member.memberInfo", m.getId()); // namespace를 이용하여 DB 접근
	}
// ========================================================================================
	// 페이징 처리
	public List<Member> selectMembers(int offset, int PAGE_SIZE) {
		List<Member> members = null;
		SqlSession session = null;
		try {
			RowBounds rowBounds = new RowBounds(offset, PAGE_SIZE);
			 members = session.selectList("Member.listMember", null, rowBounds); // namespace를 이용하여 DB 접근
			// selectList(String arg0, Object arg1, RowBounds arg2)
			// 마지막에 rowBounds를 파라미터로 넣어주기위해 아무 값이 없는 null을 2번째에 넣는다.
			System.out.println(session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return members;
	}
// ========================================================================================
	public int insertMember(Member m) {
		System.out.println("m : " + m);
		int result = 0;
		SqlSession session = null;
		try {
			result = session.insert("Member.insertMember", m); // namespace를 이용하여 DB 접근
			System.out.println(session);
			if (result > 0) {
				session.commit();
			} else {
				session.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("result dao : " + result);
		return result;
	}
// ========================================================================================
	public int memberDelete(Member member) {
		System.out.println("m : " + member);
		int result = 0;
		SqlSession session = null;
		try {
			result = session.delete("Member.deleteMember", member); // namespace를 이용하여 DB 접근
			System.out.println(session);
			if (result > 0) {
				session.commit();
			} else {
				session.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("result dao : " + result);
		return result;
	}
// ========================================================================================
	public int memberUpdate(Map<String, Object> map1) {
		int result = 0;
		SqlSession session = null;
		try {
			result = session.update("Member.updateMemberMap", map1); // namespace를 이용하여 DB 접근
			/*
			 * update(String statement, Object parameter)
			 * String statement : "namespace명.엘리먼트의id명"
			 * Object parameter : Object이기에 어떠한 모양이든 넣을 수있다.
			 * 
			 * ex)
			 * Object parameter에 "jwPark"을 넣으면 mapper.XML의 해당 엘리먼트의 (parameterType="String")이다.
			 * Object parameter에 VO를 넣으면 mapper.XML의 해당 엘리먼트의 (parameterType="package명.....class명")이다. 혹은 별칭 사용
			 * 
			 */
			System.out.println(session);
			if (result > 0) {
				session.commit();
			} else {
				session.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("result dao : " + result);
		return result;
	}
}
