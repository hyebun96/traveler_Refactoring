<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String cp = request.getContextPath();
    Date nowTime = new Date();
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
    <link rel="stylesheet" href="<%=cp%>/resource/jquery/css/smoothness/jquery-ui.min.css" type="text/css">
    <script type="text/javascript" src="<%=cp%>/resource/js/util.js"></script>
    <script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.min.js"></script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
    <div class="nav-bar">HOME</div>
</div>


<div class=main>
    <div class="clock">
        <p id="nowDate" style="color: black; width: 300px; height: 100%;
			font-size: 15px; border: none;
			text-align: center; display: inline-block; margin: 10px auto; float: right;">
            <img alt="" src="<%=cp %>/resource/img/clock.png"
                 style="width:30px; height: 30px; float: left;"><%= nowTime%>
        </p>
    </div>

    <hr style="clear: both">

    <img src="<%=cp %>/resource/img/mainimg3.jpeg" style="height: 550px;">
    <br><br><br><br>
    <div class=main-title>
        <h3>나에게로 떠나는 여행에 오신걸 환영합니다 .</h3>
    </div>

    <p>일상 탈출을 위한 여행</p>
    <p>지금 바로 떠나는 국내여행</p>
    <br><br><br>
    <div class="main-box1">
        <table style="height: 100%; table-layout: fixed;" class="board-table">
            <tr style="border-bottom: 2px solid black;">
                <td>글번호</td>
                <td style="width: 40%">제목</td>
                <td>작성자</td>
                <td>작성일</td>
                <td>조회수</td>
            </tr>
            <c:forEach var="dto" items="${list }" begin="0" end="4">
                <tr>
                    <td><span
                            style="display: inline-block;padding:1px 3px; background: #ED4C00;color: #FFFFFF; ">중요!</span>
                    </td>
                    <td style="text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"><a
                            href="${articleUrl }?num=${dto.num}&page=1">${dto.title }</a></td>
                    <td>${dto.name }</td>
                    <td>${dto.created }</td>
                    <td>${dto.viewCount }</td>
                </tr>
            </c:forEach>
        </table>


    </div>
    <div class="main-box2">
        <a href="<%=cp %>/photo/photoMain.do">
            <img src="<%=cp %>/resource/img/Nam.PNG" style="height: 300px;">
        </a>
    </div>
</div>


<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/resource/jquery/js/jquery.ui.datepicker-ko.js"></script>
</body>
</html>