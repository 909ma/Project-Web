<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>아 오늘 영화 뭐 보지?</title>
</head>
<style>
* {
	text-align: center;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/commonStyles.css">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico">
<body>
	<%@ include file="./header.jsp"%>
	<main>


		<h2>환영합니다!</h2>
		<p>회원가입 또는 로그인하여 계속 진행하세요.</p>

		<div>
			<button onclick="location.href='회원가입페이지.html'">회원가입</button>
			<button onclick="location.href='로그인페이지.html'">로그인</button>
		</div>
	</main>

	<%@ include file="./footer.jsp"%>
</body>
</html>
