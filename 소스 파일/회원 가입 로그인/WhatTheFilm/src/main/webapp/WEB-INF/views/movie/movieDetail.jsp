<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*, java.io.*, java.net.*, com.fasterxml.jackson.databind.ObjectMapper"%>
<%@ include file="../header.jsp"%>
<!DOCTYPE html>
<html>
<head>

<style>


h2 {
	color: #333333;
	font-size: 24px;
	font-weight: bold;
	margin-bottom: 10px;
}

/* 소제목 스타일 */
h3 {
	color: #666666;
	font-size: 18px;
	font-weight: bold;
	margin-bottom: 8px;
}

/* 일반 텍스트 스타일 */
p {
	color: #333333;
	font-size: 14px;
	margin-bottom: 6px;
}

/* 리스트 스타일 */
ul {
	list-style-type: disc;
	color: #333333;
	font-size: 14px;
	margin-bottom: 10px;
	margin-left: 20px;
}

/* 테이블 스타일 */
table {
	width: 100%;
	border-collapse: collapse;
	margin-bottom: 10px;
}

th, td {
	padding: 6px;
	border: 1px solid #cccccc;
	text-align: left;
	color: #333333;
	font-size: 14px;
}

th {
	background-color: #f2f2f2;
	font-weight: bold;
}

/* 스탭 목록 스타일 */
ul.staff-list {
	list-style-type: none;
	color: #333333;
	font-size: 14px;
	margin-bottom: 10px;
}

ul.staff-list li {
	margin-bottom: 6px;
}

ul.staff-list li .role {
	font-weight: bold;
}

ul.staff-list li .name {
	margin-left: 10px;
}
    #movieBox {
        width: 1024px;
        margin: 0 auto; /* 가로 중앙 정렬 */
    }
    #posterImage {
        display: block;
        margin: 0 auto;
    }
</style>

    <style>
        .hidden {
            display: none;
        }

        .buttons-container {
            margin-bottom: 20px;
            position: relative;
        }

        .buttons-container button {
            background-color: #ffffff;
            border: none;
            padding: 5px 10px;
            margin-right: 10px;
            color: #000000;
            cursor: pointer;
        }

        .buttons-container:after {
            content: "";
            position: absolute;
            bottom: -5px;
            left: 0;
            width: 100%;
            height: 1px;
            background-color: #000000;
        }
    </style>


<meta charset="UTF-8">
<title>Movie Detail</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
	function searchMovie(movieTitle) {
		var apiKey = "e2a56aa6721d47327a92acc02bfbddf3"; // 본인의 API 키로 대체해야 합니다.
		var movieNmEncoded = encodeURIComponent(movieTitle);

		// 1. 영화 정보 조회
		var kobisUrl = "https://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=f5eef3421c602c6cb7ea224104795888&movieNm="
				+ movieNmEncoded;
		$.ajax({
			url : kobisUrl,
			type : "GET",
			success : function(response) {
				var movies = response.movieListResult.movieList;
				if (movies.length > 0) {
					var movie = findMovieByKoreanTitle(movies, movieTitle);
					if (movie) {
						var movieNmEn = movie.movieNmEn;
						// 2. 영화 정보로 검색
						searchMovieByEnglishTitle(movieNmEn, movieTitle);
						//console.log(rank + " : " + movieNmEn + "(eng)"); // 검색 방식 로그 출력
					} else {
						searchMovieByKoreanTitle(movieTitle);
						//console.log(rank + " : " + movieTitle); // 검색 방식 로그 출력
					}
				} else {
					searchMovieByKoreanTitle(movieTitle);
					//console.log(rank + " : " + movieTitle); // 검색 방식 로그 출력
				}
			},
			error : function() {
				searchMovieByKoreanTitle(movieTitle);
				//console.log(rank + " : " + movieTitle); // 검색 방식 로그 출력
			}
		});
	}

	function findMovieByKoreanTitle(movies, movieTitle) {
		for (var i = 0; i < movies.length; i++) {
			var movie = movies[i];
			var title = movie.movieNm;
			if (title === movieTitle) {
				return movie;
			}
		}
		return null;
	}

	function searchMovieByEnglishTitle(movieTitle, movieNm) {
		var apiKey = "e2a56aa6721d47327a92acc02bfbddf3"; // 본인의 API 키로 대체해야 합니다.
		var encodedTitle = encodeURIComponent(movieTitle);
		var apiUrl = "https://api.themoviedb.org/3/search/movie?api_key="
				+ apiKey + "&query=" + encodedTitle;

		$.ajax({
			url : apiUrl,
			type : "GET",
			success : function(response) {
				var results = response.results;
				if (results.length > 0) {
					var movie = results[0];
					var posterPath = movie.poster_path;
					if (posterPath) {
						var posterUrl = "https://image.tmdb.org/t/p/w500"
								+ posterPath;
						$("#posterImage").attr("src", posterUrl); // 포스터 이미지 업데이트
					} else {
						searchMovieByKoreanTitle(movieNm);
					}
				} else {
					searchMovieByKoreanTitle(movieNm);
				}
			},
			error : function() {
				searchMovieByKoreanTitle(movieNm);
			}
		});
	}

	// 3. 한국어로 다시 검색
	function searchMovieByKoreanTitle(movieTitle) {
		var apiKey = "e2a56aa6721d47327a92acc02bfbddf3"; // 본인의 API 키로 대체해야 합니다.
		var encodedTitle = encodeURIComponent(movieTitle);
		var apiUrl = "https://api.themoviedb.org/3/search/movie?api_key="
				+ apiKey + "&query=" + encodedTitle;

		$
				.ajax({
					url : apiUrl,
					type : "GET",
					success : function(response) {
						var results = response.results;
						if (results.length > 0) {
							var movie = results[0];
							var posterPath = movie.poster_path;
							var posterUrl = "https://image.tmdb.org/t/p/w500"
									+ posterPath;
							$("#posterImage").attr("src", posterUrl); // 포스터 이미지 업데이트
						} else {
							$("#posterImage")
									.attr("src",
											"${pageContext.request.contextPath}/resources/image/errorPoster.png"); // 검색 결과 없을 경우 이미지 초기화
						}
					},
					error : function() {
						$("#posterImage").attr("src", ""); // 에러 발생 시 이미지 초기화
					}
				});
	}
</script>

    <script>
        function toggleSection(sectionId) {
            var sections = document.getElementsByClassName("movie-section");
            for (var i = 0; i < sections.length; i++) {
                var section = sections[i];
                if (section.id === sectionId) {
                    section.style.display = "block";
                } else {
                    section.style.display = "none";
                }
            }
        }
    </script>


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/commonStyles.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/naviStyles.css">
</head>
<body>
	
	<main>
		<div class="container">
			<div class="topnav">
				<a class="active" href="/board">메인 화면</a>
				<a href="#">공지 사항</a> 
				<a href="#">자유게시판</a> 
				<a href="#">영화 평점</a> 
				<a href="/dailyMovie">상영작 통계 조회</a> 
				<a href="/HowMuchDailyMovie">개봉작 통계 조회</a>
			</div>
			<br />
			<button class="button">회원 정보 관리</button>
			<button class="button">설정</button>
		</div>
		<div id = "movieBox">
		<br><br><br><br>
		<!-- <h1>Movie Detail</h1> -->
		<%
		// 전달받은 number 파라미터 값 추출
		int number = Integer.parseInt(request.getParameter("number"));

		// TODO: number를 사용하여 필요한 데이터를 조회 및 출력하는 로직 작성
		// MySQL 데이터베이스 연결 및 쿼리 실행 등 필요한 작업을 수행하세요.
		%>
		<%
		// JSON 파일 읽기
		String apiUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=f5eef3421c602c6cb7ea224104795888&movieCd="
				+ number;
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		// JSON 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> jsonData = objectMapper.readValue(connection.getInputStream(), Map.class);
		Map<String, Object> movieInfoResult = (Map<String, Object>) jsonData.get("movieInfoResult");
		Map<String, Object> movieInfo = (Map<String, Object>) movieInfoResult.get("movieInfo");

		String movieNm = (String) movieInfo.get("movieNm");
		String movieNmEn = (String) movieInfo.get("movieNmEn");
		String showTm = (String) movieInfo.get("showTm");
		String prdtYear = (String) movieInfo.get("prdtYear");
		String openDt = (String) movieInfo.get("openDt");

		List<Map<String, String>> genres = (List<Map<String, String>>) movieInfo.get("genres");
		List<Map<String, String>> directors = (List<Map<String, String>>) movieInfo.get("directors");
		List<Map<String, String>> actors = (List<Map<String, String>>) movieInfo.get("actors");
		List<Map<String, String>> showTypes = (List<Map<String, String>>) movieInfo.get("showTypes");
		List<Map<String, String>> companys = (List<Map<String, String>>) movieInfo.get("companys");
		List<Map<String, String>> audits = (List<Map<String, String>>) movieInfo.get("audits");
		List<Map<String, String>> staffs = (List<Map<String, String>>) movieInfo.get("staffs");

		
		
		// 영화 포스터 이미지 출력
		out.println("<img id='posterImage' src='' alt='포스터 없음' width='500'>");

		// 영화 검색 함수 호출
		out.println("<script>searchMovie('" + movieNm + "');</script>");
		%>

		<h2><%=movieNm%>
			(<%=prdtYear%>)
		</h2>
    <div class="buttons-container">
        <button onclick="toggleSection('movie-info')">영화 정보</button>
        <button onclick="toggleSection('actors')">감독/출연</button>
        <button onclick="toggleSection('companys')">제작/배급사</button>
        <button onclick="toggleSection('staffs')">스텝</button>
    </div>

    <div id="movie-info" class="movie-section">
        <p>영문 제목: <%= movieNmEn %></p>
        <p>상영 시간: <%= showTm %>분</p>
        <p>개봉일: <%= openDt %></p>
        <h3>장르</h3>
        <ul>
            <% for (Map<String, String> genre : genres) { %>
                <li><%= genre.get("genreNm") %></li>
            <% } %>
        </ul>
        <h3>상영 형식</h3>
        <ul>
            <% for (Map<String, String> showType : showTypes) { %>
                <li><%= showType.get("showTypeGroupNm") %> - <%= showType.get("showTypeNm") %></li>
            <% } %>
        </ul>
        <h3>등급</h3>
        <ul>
            <% for (Map<String, String> audit : audits) { %>
                <li><%= audit.get("watchGradeNm") %> (심의번호: <%= audit.get("auditNo") %>)</li>
            <% } %>
        </ul>
    </div>

    <div id="actors" class="movie-section hidden">
        <h3>감독</h3>
        <ul>
            <% for (Map<String, String> director : directors) { %>
                <li><%= director.get("peopleNm") %> (<%= director.get("peopleNmEn") %>)</li>
            <% } %>
        </ul>

        <h3>출연</h3>
        <table>
         <tr>
                <th>이름</th>
                <th>영문명</th>
                <th>작중역할</th>
            </tr>
        
            <tr>
                <% for (Map<String, String> actor : actors) { %>
                    <td><%= actor.get("peopleNm") %></td>
                    <td><%= actor.get("peopleNmEn") != null ? actor.get("peopleNmEn") : "" %></td>
                    <td><%= actor.get("cast") %></td>
                </tr>
                <tr>
                <% } %>
            </tr>
        </table>
    </div>

    <div id="companys" class="movie-section hidden">
        <h3>제작/배급사</h3>
        <table>
        <tr>
                <th>제작/배급사</th>
                <th>영문명</th>
                <th>분야</th>
            </tr>
        
            <tr>
                <% for (Map<String, String> company : companys) { %>
                    <td><%= company.get("companyNm") %></td>
                    <td><%= company.get("companyNmEn") != null ? company.get("companyNmEn") : "" %></td>
                    <td><%= company.get("companyPartNm") %></td>
                </tr>
                <tr>
                <% } %>
            </tr>
        </table>
    </div>

    <div id="staffs" class="movie-section hidden">
        <h3>스텝</h3>
        <ul>
            <% for (Map<String, String> staff : staffs) { %>
                <li><%= staff.get("staffRoleNm") %> - <%= staff.get("peopleNm") %> (<%= staff.get("peopleNmEn") %>)</li>
            <% } %>
        </ul>
    </div>
    </div>
	</main>
	<%@ include file="../footer.jsp"%>
</body>
</html>
