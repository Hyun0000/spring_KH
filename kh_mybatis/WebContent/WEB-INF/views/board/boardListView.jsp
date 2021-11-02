<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--
	request.setAttribute("list", list);
	request.setAttribute("currentPage", currentPage);
	request.setAttribute("maxPage", maxPage);
	request.setAttribute("startPage", startPage);
	request.setAttribute("endPage", endPage);
	request.setAttribute("listCount", listCount); 
-->
	<h1>게시판 - MyBatis Board</h1>
	
	<p>페이지</p>

	<c:forEach begin="${startPage}" end="${endPage}" step="1" var="i">
	
		<!-- 현재 선택한 페이지 숫자를 굵게 -->
		<c:if test="${currentPage == i}">
		<strong>
		</c:if>
		
		<a href="boardlist?page=${i}">${i}</a>
		
		<c:if test="${currentPage == i}">
		</strong>
		</c:if>
		
	</c:forEach>
	
	<table border="1">
		<tr>
			<th>글번호</th>
			<th>글제목</th>
			<th>작성자</th>
			<th>내용</th>
			<th>작성일</th>
			<!--
			<td>boardLevel</td>
			<td>boardOriginalFileName</td>
			<td>boardRenameFileName</td>
			<td>boardRef</td>
			<td>boardReplyRef</td>
			<td>boardReplySeq</td>
			<td>boardReadCount</td>
			-->
		</tr>
	<c:forEach var="board" items="${list}" >
		<tr>
			<td>${board.boardNum}</td>
			<td>${board.boardTitle}</td>
			<td>${board.boardWriter}</td>
			<td>${board.boardContent}</td>
			<td>${board.boardDate}</td>
			<%--
			<td>${board.boardLevel}</td>
			<td>${board.boardOriginalFileName}</td>
			<td>${board.boardRenameFileName}</td>
			<td>${board.boardRef}</td>
			<td>${board.boardReplyRef}</td>
			<td>${board.boardReplySeq}</td>
			<td>${board.boardReadCount}</td>
			--%>
		</tr>
	</c:forEach>
	</table>
	

	
</body>
</html>