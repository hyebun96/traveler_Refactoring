<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String cp = request.getContextPath();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Traveler</title>
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
	<link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
	<link rel="stylesheet" href="<%=cp%>/resource/css/main.css" type="text/css">
	<link rel="stylesheet" href="<%=cp%>/resource/css/notice_list.css" type="text/css">

	<script src="https://code.jquery.com/jquery-3.6.0.js"
			integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
	<script type="text/javascript" src="<%=cp%>/resource/js/travel.js"></script>
</head>
<body>
	<div class="header">
		<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	</div>
	<div class="navigation">
		<div class="nav-bar">HOME > TRAVEL</div>
	</div>

	<div class="travel">
		<div class="weather">
			<img alt="" src="<%=cp%>/resource/img/weather/${weatherDTO.weather}">
			<p class="location">
				${type} weather
				<span>( ${weatherDTO.tem} ℃)</span>
			</p>
			<p class="date">
				<c:choose>
				<c:when test="${sessionScope.member.userId == 'admin'}">
					<button class="btn-hover" type="button" onclick="location.href='<%=cp%>/travel/created.do?type=${type}'">글등록</button>
				</c:when>
				<c:otherwise>
					${weatherDTO.date}&nbsp;&nbsp;&nbsp;
				</c:otherwise>
				</c:choose>
			</p>
		</div>

		<c:forEach var="dto" items="${list}">
			<div>
				<div class="travel-box">
					<c:forEach var="image" items="${dto.saveFileName}">
						<img src="<%=cp%>/uploads/travel/${image}" alt=""/>
					</c:forEach>
				</div>
				<div class="travel-box2">
					<img src="<%=cp%>/resource/img/user.png" alt="">
					<span>${dto.place} | ${dto.created}</span><br>
					<span>${dto.userId}</span>
					<div class="content">${dto.information}</div>
				</div>
				<c:if test="${sessionScope.member.userId == 'admin'}">
				<div class="div-button">
					<button type="button" onclick="updateTravel('${dto.num}', '${type}')">글수정</button>
					<button type="button" onclick="deleteTravel('${dto.num}', '${type}')">글삭제</button>
				</div>
				</c:if>
				<br>
				<div class="like-box">
					<hr>
					<span>${dto.likeCount} Like&nbsp;&nbsp;&nbsp;Only read, like</span>
					<span style="float: right;">
						<span type="button" onclick="location.href='<%=cp%>/travel/like.do?num=${dto.num}&type=${type}'">
							<c:choose>
								<c:when test="${dto.likeNum > 0}">
									<img class="ico_like" alt="" src="<%=cp%>/resource/img/like.png">
								</c:when>
								<c:otherwise>
									<img class="ico_like" alt="" src="<%=cp%>/resource/img/unlike.png">
								</c:otherwise>
							</c:choose>
						</span>
					</span>
				</div>
			</div>
			<br>
		</c:forEach>
	</div>


	<div class="footer">
		<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
	</div>
</body>
</html>