<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<button id="test1" type="button">ajax버튼1</button>
	<!-- session.setAttribute("samp", vo); -->
	<div id="responseVal">[${samp}]</div>
	
	<button id="test2" type="button">ajax버튼2</button>
	<div id="d2"></div>
	
	<button id="test3" type="button">ajax버튼3</button>
	<div id="d3"></div>
	
	<button id="test5" type="button">ajax버튼5</button>
	<div id="d5"></div>
	
	<button id="test6" type="button">ajax버튼6</button>
	<div id="d6"></div>
	
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script type="text/javascript">
		$("#test1").on("click", function () {
			$.ajax({
				url : "test1.do",
				type : "post",
				data : {
					name : "신사임당",
					age : 47
					},
				success : function (result) {
					if (result == "ok") {
						alert("전송 성공!");
					} else {
						alert("전송 실패!");
					}
				},
				error : function (request, status, errorData) {
					alert("error code : " + request.status + "\n"
							+ "message : " + request.responseText + "\n"
							+ "error : " + errorData);
				}
			});
		});
// ==================================================================================
		$("#test2").on("click", function () {
			// (test2.do) controller로부터 json 객체를 리턴받아, div에 출력한다.
			$.ajax({
				url : "test2.do",
				type : "post",
				dataType : "json",
			// [ success : function (data) ]에 실려오는 data(=controller로부터 반환 받는 data)가 (dataType : "json") 모양이다.
				success : function (data) {
					// 전달받은 JSONObject에 담은 Value를 Key로 접근하여 출력한다.
					$("#d2").html("번호 : " + data.no
							+ "<br>제목 : " + data.title
							+ "<br>작성자 : " + data.writer
							+ "<br>내용 : " + decodeURIComponent(data.content.replace(/\+/g, " ")));
				},
				error : function (request, status, errorData) {
					alert("error code : " + request.status + "\n"
							+ "message : " + request.responseText + "\n"
							+ "error : " + errorData);
				}
			});
		});
// ==================================================================================
		$("#test3").on(
			"click",
			function() {
				$.ajax({
					url : "test3.do",
					type : "post",
					dataType : "json",
					// view단에서 controller로 가지고 가는 data는 없다.
					success : function(data) {
						// 전달받은 data를 JSON 문자열 형태로 바꾼다.
						var jsonStr = JSON.stringify(data);
						console.log(data);
						
						// 바꾼 문자열을 json 객체로 변환한다.
						var json = JSON.parse(jsonStr);
						console.log(json);
						
						// data를 담은 <div>
						var values = $("#d3").html();
						
						// 위에서 변환한 json 객체를 이용해 <div>에 data를 담는다.
						// (controller로부터 리스트를 받아 출력한다.)
						for (var i in json.list) {
							values += json.list[i].userId
									+ ", "
									+ json.list[i].userPwd
									+ ", "
									+ decodeURIComponent(json.list[i].userName)
									+ ", "
									+ json.list[i].age
									+ ", "
									+ json.list[i].email
									+ "<br>";
						}
						// values에 담은 값을 <div id="d3">에 출력한다.
						$("#d3").html(values);
					},
					error : function(request, status,
							errorData) {
						alert("error code : "
								+ request.status + "\n"
								+ "message : "
								+ request.responseText
								+ "\n" + "error : "
								+ errorData);
					}
				});
			});
// ==================================================================================
	// JSON 객체를 뷰에서 Controller로 보내는 방법
	$("#test5").on("click", function () {
		// 자바스크립트에서 json 객체를 생성해 서버 컨트롤러로 전송
		var job = new Object();
		job.name = "강감찬";
		job.age = 33;
		console.log(JSON.stringify(job));
		// {"name":"강감찬","age":33}
		
		$.ajax({
			url : "test5.do",
			type : "post",
			data : JSON.stringify(job),
			contentType : "application/json; charset=utf-8",
			success : function (result) {
				alert("전송성공");
				$("#d5").html("전송한 json 객체의 값 : " + job.name + ", " + job.age);
			},
			error : function(request, status, errorData){
				alert("error code : " + request.status + "\n"
				+ "message : " + request.responseText + "\n"
				+ "error : " + errorData);
			}
		});
	});
// ===================================================================================	
	// JSON 객체 배열을 뷰에서 Controller로 보내는 방법
	$("#test6").on("click", function () {
		// 자바스크립트에서 jsonArray 객체(객체 배열)를 만들어서, 서버 컨트롤러로 보내기
		var jArray = [
				{"name" : "이 이", "age" : 30 },
				{"name" : "신사임당", "age" : 47},
				{"name" : "황진이", "age" : 25}
			];
		
		console.log(JSON.stringify(jArray));
		
		$.ajax({
			url : "test6.do",
			data : JSON.stringify(jArray),
			type : "post",
			contentType : "application/json; charset=utf-8",
			successs : function (result) {
				alert("전송 성공!");
				var value = $("#d6").html();
				for ( var i in jArray) {
					values += "이름 : " + jArray[i].name + ", 나이 : " + jArray[i].age + "<br>";
				}
				$("#d6").html(values);
			},
			error : function(request, status, errorData){
				alert("error code : " + request.status + "\n"
						+ "message : " + request.responseText + "\n"
						+ "error : " + errorData);
			}
		})
	})
	</script>
</body>
</html>