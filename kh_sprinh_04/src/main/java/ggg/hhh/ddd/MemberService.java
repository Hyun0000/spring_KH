package ggg.hhh.ddd;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("memberService")
public class MemberService {
	@Autowired MemberDao memberDao;
	// @Repository("memberDao")
	
	// new 하지않고도 memberDao를 사용할 수 있다.
	
	public MemberService() {
	}
// ====================================================================================================
	// 기존 JDBC에서 쓰이던 Connection 연결을 Dao에서 관리하므로,
	// 별도 Connection 정보를 담당하지 않는다.
	public Member selectMember(Member m) {
		return memberDao.selectMember(m);
	}
// ====================================================================================================
	public Member checkIdDup(Member m) {
		return memberDao.checkIdDup(m);
	}
// ====================================================================================================
	public int insertMember(Member m) {
		int result = 0;
		result = memberDao.insertMember(m);
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
