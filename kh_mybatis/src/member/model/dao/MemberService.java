package member.model.dao;

import java.util.List;
import java.util.Map;

import member.model.vo.Member;

public class MemberService {
	public MemberService() {
	}
// ====================================================================================================
	// 기존 JDBC에서 쓰이던 Connection 연결을 Dao에서 관리하므로,
	// 별도 Connection 정보를 담당하지 않는다.
	public Member selectMember(Member m) {
		return new MemberDao().selectMember(m);
	}
// ====================================================================================================
	public Member checkIdDup(Member m) {
		return new MemberDao().checkIdDup(m);
	}
// ====================================================================================================
	public int insertMember(Member m) {
		int result = 0;
		result = new MemberDao().insertMember(m);
		return result; 
	}
// ====================================================================================================
	public int memberDelete(Member member) {
		int result = 0;
		result = new MemberDao().memberDelete(member);
		return result;
	}
// ====================================================================================================
	public int memberUpdate(Map<String, Object> map1) {
		int result = 0;
		result = new MemberDao().memberUpdate(map1);
		return result;
	}
// ====================================================================================================
	// 페이징 처리
	public List<Member> selectMembers(int offset, int PAGE_SIZE) {
		return new MemberDao().selectMembers(offset, PAGE_SIZE);
	}
}
