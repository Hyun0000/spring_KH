<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="boardwrite" method="post" id="boardFrm">
	<!--
		<input type="text" name="title">
		<input type="text" name="content">
		<input type="submit" value="제출">
	-->
	
		<input type="text" name="id">
		<input type="text" name="email">
		 
		<input type="text" name="boardTitle">
		<input type="text" name="boardContent">
		<input type="text" name="boardReadCount1" class="numberonly">
		<input type="text" name="boardReadCount2" class="numberonly">
		<input type="text" name="boardReadCount3" class="numberonly">
		<!-- 입력칸이 여러개 일수도 있으니까 class 속성을 이용 -->
		
		<!-- 
		boardReadCount는 VO에서 자료형이 int인데 화면단에서 넘어오는 값들은 무조건 String 형이다.
		보통의 경우라면 controller에서 Integer.ParseInt()를 이용해 자료형의 차이를 해소한다.
		하지만 spring에서는 이러한 작업을 할 필요가 없다.
		
		why?
		자료의 형변환까지 알아서해준다.		
		날짜 모양도 거의 맞춰서 들어온다.
		무엇보다 VO에서 날짜의 자료형을 Date말고 String을 쓰는게 맘 편하다.
		
		물론 spring이 완벽한것은 아니다.
		<input type="text" name="boardReadCount">에서 숫자만이 아니라 '3A'라고 입력하면
		spring이 자료형을 변환하는 과정에서 오류가 발생한다.
		(boardReadCount 필드에 값을 넣을 수 없기에 오류가 발생한다.)
		
		더 큰 문제는 콘솔에서 이에 대한 오류메세지가 뜨지 않는다는 것이다.
		System.out.println("bvo : " + bvo); 이러한 log 조차뜨지 않는다.
		즉, controller에서 해당 method 자체를 진입조차 하지 못하는 것이다.
		
		why?
		method를 가기도 전에 자료형을 변환하는 과정에서 이미 오류가 나서 멈춰버렸기 때문이다.
		
		이것을 어떻게 해결하는가? js의 유효성 검사를 이용한다.
		
		
		(따라서 메소드 자체를 진입했는지 안 했는지를 알아야한다.)
		
		
		1. start가 제대로 된 start인지
		2. 메소드에 진입 자체를 한게 맞는지(자료형으로 인해 발생한 문제)
		 -->
		<input type="button" value="제출" id="btn">
	</form>
	<script type="text/javascript">
		// 유효성 검사
		// numberonly each로 숫자 변환되는지 확인하고 모두 통과  -->  boardFrm.submit();
	</script>
</body>
</html>