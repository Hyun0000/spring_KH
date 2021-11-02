<%@ page language="java" contentType="text/html; charset=UTF-8" 
pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>오류페이지</title>
</head>
<body>
<!-- request.setAttribute("message", "게시글 페이지별 조회 실패"); -->
	<div id="product_no">
	<p id="product_no_write">이런<br>${message}</p>
	<img src="https://img4.daumcdn.net/thumb/R658x0.q70/?fname=https://t1.daumcdn.net/news/202105/25/holapet/20210525041814981qduu.jpg">
	<p>저희 사이트 망했습니다.</p>
	<a href="javascript:history.back()">뒤로가기</a>
	</div>
</body>
</html>