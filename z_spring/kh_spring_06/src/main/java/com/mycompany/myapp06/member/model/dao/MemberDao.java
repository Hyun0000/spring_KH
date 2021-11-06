package com.mycompany.myapp06.member.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mycompany.myapp06.member.model.vo.Member;

// DAO 성격에 맞는 Bean으로 생성
@Repository("memberDao")
public class MemberDao {
	@Autowired
	private SqlSessionTemplate sqlSession;
	// private SqlSession sqlSession; 이것도 된다.
	// SqlSession 인터페이스를 구체적으로 구현한게 SqlSessionTemplate class이다.
	
	public List<Member> selectMembers() {
		return sqlSession.selectList("Member.listMember");
		
		// sqlsession : servlet-context.xml에서 만든다.
	}
}
