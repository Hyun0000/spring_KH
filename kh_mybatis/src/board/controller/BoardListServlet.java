package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/boardlist")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 1; // 페이지 값 처리용
		int limit = 10; // 한 페이지당 출력할 목록 갯수
		
		// 전달된 페이지값 추출
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}

		BoardService bservice = new BoardService();
		
		// 전체 게시글 개수와 페이징 수에 맞춘 data 가져오기
		int listCount = bservice.getListCount(); // 전체 게시글 개수
		System.out.println("listCount : " + listCount);
		
		// 1. Rowbounds 이용
		// ArrayList<Board> list = bservice.selectList(currentPage, limit); // 페이징 수에 맞춘 data 가져오기
		// System.out.println("list rownum : " + list);
		
		// 2. ROWNUM 사용
		ArrayList<Board> list = bservice.selectListRownum(currentPage, limit); // 페이징 수에 맞춘 data 가져오기
		// System.out.println("list rownum : " + list);

		// 총 페이지 수 계산 : 목록이 최소 1개일 때 1page로 처리하기 위해 0.9를 더한다.
		int maxPage = (int) ((double) listCount / limit + 0.9);

		// 현재 페이지에 보여줄 시작 페이지 번호 (1, 11, 21, .......)
		// 예를 들어 현재 페이지가 13page 라면, 시작 페이지는 11page가 되어야 한다
		int startPage = (((int) ((double) currentPage / limit + 0.9)) - 1) * limit + 1;
		// 만약, 목록으로 보여질 페이지 수가 10개 이면, 끝 페이지는 20page가 되어야 한다
		int endPage = startPage + limit - 1;
		if (maxPage < endPage)
			endPage = maxPage;
		RequestDispatcher view = null;
		if (list != null && list.size() > 0) {
			view = request.getRequestDispatcher("WEB-INF/views/board/boardListView.jsp");
			request.setAttribute("list", list);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("maxPage", maxPage);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("listCount", listCount);
			view.forward(request, response);
		} else {
			view = request.getRequestDispatcher("WEB-INF/views/board/boardError.jsp");
			request.setAttribute("message", "게시글 페이지별 조회 실패");
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
