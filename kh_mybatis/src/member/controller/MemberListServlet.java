package member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.dao.MemberDao;
import member.model.dao.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet("/memberlist")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int startRnum = 2; // 시작 rownum
		int offset = startRnum - 1;
		// (startRnum = 2)에서 볼 수 있듯이 나는 현재 2번째 순서의 글 부터 조회해서 출력하고 싶은 것이다.
		// 근데 (offset = 2)를 하면 '2번째 글까지 제외'하고 3번째 글 부터 조회되는 것이기에
		// offset에서 '-1'을 해주는 것이다.
		final int PAGE_SIZE = 8;
		
		int endRnum = 4; // 얘는 사실상 더 이상 필요없다.
		
		List<Member> volist = new MemberService().selectMembers(offset, PAGE_SIZE);
		
		System.out.println("memberlist 결과 : " + volist);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
