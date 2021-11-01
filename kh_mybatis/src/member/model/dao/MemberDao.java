package member.model.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import member.model.vo.Member;

public class MemberDao {
	public MemberDao() {}
	
	// java와 Oracle을 연결하는데 있어 MyBatis가 그 사이의 '연결고리' 역할을한다.
	// JDBC는 기존의 연결고리 역할에서 '리소스 관리'로 역할이 바뀐다.
	private SqlSessionFactory getSqlSessionFactory() {
		String resource = "mybatis/mybatis-config.xml";
		SqlSessionFactory factory = null;
		try {
			// InputStream으로 (mybatis-config.xml)의 설정 정보를 읽어와(로드해서) MyBatis 객체를 생성한다.
			// DB에서 가져오는 수많은 data를 읽기위해 java의 InputStream을 사용한다.
			InputStream inputStream = Resources.getResourceAsStream(resource);
			
			// 읽어 온 (mybatis-config.xml)의 설정 정보를 바탕으로 SqlSessionFactoryBuilder를 이용해 factory를 생성
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(inputStream);
			// InputStream을 가지고 다시 bulid()를 해서 factory를 만드는 것이다.(공장에서 찍어내듯이 팍팍 읽어 생성한다.)
		} catch (IOException e) {
			e.printStackTrace();
		}
		return factory;
	}
	// ===================결론 : 이게 DB 연결하는 코드이다.===================
	// 이 부분은 크게 신경쓰지 말것, 어차피 Spring가면 또 설정이 바뀐다.
	
	public Member selectMember(Member m) {
		Member member = null;
		SqlSession session = null;
		try {
			// SqlSessionFactory를 통해 SqlSession 객체 생성
			session = getSqlSessionFactory().openSession(false); // autocommit 해제
			member = session.selectOne("Member.loginCheck", m);
			// namespace(Member)와 엘리먼트의 id(loginCheck)값을 이용해 DB 접근
			
			// 1. Member
			// member-mapper.xml에서 <mapper namespace="Member">의 namespace 값이다.
			
			// 2. loginCheck
			// <select id="loginCheck" parameterType="Member" resultMap="r2">에서 (id="loginCheck") 값이다.
			System.out.println(session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return member;
	}
	
	public Member checkIdDup(Member m) {
		Member member = null;
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession(false); // DB 연결
			member = session.selectOne("Member.memberInfo", m.getId()); // namespace를 이용하여 DB 접근
			System.out.println(session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return member;
	}

	public int insertMember(Member m) {
		System.out.println("m : " + m);
		int result = 0;
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession(false); // DB 연결
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
}
