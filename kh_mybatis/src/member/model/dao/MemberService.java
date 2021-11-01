package member.model.dao;

import member.model.vo.Member;

public class MemberService {
	public MemberService() {
	}

	// 기존 JDBC에서 쓰이던 Connection 연결을 Dao에서 관리하므로,
	// 별도 Connection 정보를 담당하지 않는다.
	public Member selectMember(Member m) {
		return new MemberDao().selectMember(m);
	}
	
	public Member checkIdDup(Member m) {
		return new MemberDao().checkIdDup(m);
	}
	
	public int insertMember(Member m) {
		int result = 0;
		result = new MemberDao().insertMember(m);
		return result; 
	}
}
