<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="boardwrite" method="post" id="boardFrm" enctype="multipart/form-data">
		<input type="text" name="id">
		<input type="text" name="email">
		 
		<input type="text" name="boardTitle">
		<input type="text" name="boardContent">
		<input type="text" name="boardReadCount1" class="numberonly">
		<input type="text" name="boardReadCount2" class="numberonly">
		<input type="text" name="boardReadCount3" class="numberonly">
		
		<input type="hidden" name="boardNum" value="0">
		<input type="file" name="report">
		<!-- 
			<input type="hidden" name="boardNum" value="${vo.boardNum}">
			- 만약 ${vo.boardNum} 값 자체가 존재하지 않는다면 controller한테 "" 형태로 값이 넘어간다.
			- 이경우 만약 controller에서 (int bunm)으로 parameter의 타입을 정하면 형변환 과정에서 오류가 발생한다.
			- 그럼 또 아예 method 자체를 진입하지 못 한다.
			
			cf)
			update의 경우 (type="hidden")으로 글번호를 같이 전달할 때도 있다.
			따라서 value="${vo.boardNum}"에 값이 들어있냐의 여부에 따라 insert를 할지 update를 할지가 결정된다.
			
			cf)
			(type="hidden")이니까 아에 value 속성으로 해당 <input>의 값을 setting 해줘야한다.
		 -->
		 <input type="submit" value="제출">
	</form>
</body>
</html>