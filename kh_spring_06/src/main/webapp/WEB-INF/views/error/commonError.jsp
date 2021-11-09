<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
${mag}
<!-- 5초후 자동 페이지 이동, url로 지정된 곳으로 이동 -->
<script>
	location.href = "${pageCon...}/${url}"
</script>
</body>
</html>