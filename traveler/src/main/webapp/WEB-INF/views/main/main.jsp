<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>spring</title>
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resource/css/main.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resource/css/board.css" type="text/css">

    <script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="<%=cp%>/resource/js/main.js"></script>
</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
</div>
<div class="navigation">
    <div class="nav-bar">HOME</div>
</div>

<div class=main>
    <div class="clock">
        <p class="nowDate">
            <img alt="" src="<%=cp %>/resource/img/clock.png">
            <b id="nowTime"></b>
        </p>
    </div>

    <hr style="clear: both">

    <ul id="bxslider">
        <c:if test="${travelImageList != null}">
            <c:forEach var="dto" items="${travelImageList}" >
                <li><img src="<%=cp %>/uploads/travel/${dto.saveFileName}"/></li>
            </c:forEach>
        </c:if>
    </ul>

    <br><br><br><br>

    <div class=main-title>
        <h3>나에게로 떠나는 여행에 오신걸 환영합니다 .</h3>
    </div>

    <p>일상 탈출을 위한 여행</p>
    <p>지금 바로 떠나는 국내여행</p>
    <br><br><br>
    <div class="main-box1">
        <table class="board-table">
            <tr>
                <td>글번호</td>
                <td class="title">제목</td>
                <td>작성자</td>
                <td>작성일</td>
                <td>조회수</td>
            </tr>
            <c:forEach var="dto" items="${list }" begin="0" end="4">
            <tr>
                <td>
                    <span>중요!</span>
                </td>
                <td class="notice">
                    <a href="${articleUrl }?num=${dto.num}&page=1">${dto.title }</a>
                </td>
                <td>${dto.name }</td>
                <td>${dto.created }</td>
                <td>${dto.viewCount }</td>
            </tr>
            </c:forEach>
        </table>
    </div>

    <div class="main-box2">
        <a href="<%=cp %>/photo/photoMain.do"></a>
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</div>
</body>
</html>