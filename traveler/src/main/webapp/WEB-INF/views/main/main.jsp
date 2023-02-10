<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page isELIgnored="false" %>
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
    <link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/notice_list.css">

    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
    <link href="<%=cp%>/resources/css/main.css" rel="stylesheet"  type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
    integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resources/js/photo.js"></script>
    <script type="application/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    <script type="text/javascript" src="<%=cp%>/resources/js/main.js"></script>
    <script type="application/javascript" src="https://kit.fontawesome.com/667371032c.js" crossorigin="anonymous"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME</div>
    </div>

    <div class="main">
        <div class="clock">
            <p class="nowDate">
                <img alt="" src="<%=cp%>/resources/img/clock.png">
                <b id="nowTime"></b>
            </p>
        </div>

        <hr>

        <div class="page-wrapper">
            <div class="post-slider">
                <div class="post-wrapper">
                    <c:forEach var="dto" items="${travelImageList}" >
                    <div class="post">
                        <a href="<%=cp%>/travel/list.do?type=${dto.type}">
                            <img src="<%=cp%>/uploads/travel/${dto.saveFileName}" class="slider-image" alt="">
                        </a>
                    </div>
                    </c:forEach>
                </div>
            </div>
            <div class="post-move">
                <span class="prev"><i class="fa-solid fa-circle-chevron-left"></i></span>
                <span class="move-box">&nbsp;&nbsp;🗽관리자가 추천하는 여행지&nbsp;&nbsp;</span>
                <span class="next"><i class="fa-solid fa-circle-chevron-right"></i></span>
            </div>
        </div>

        <br>
        <br>
        <br>

        <div class=main-title>
            <h3>Welcome ‍🙋🏻‍♀️</h3>
            <p>국내여행 구석구석 나만의 여행을 공유해주세요.</p>
            <p>다양한 추천 여행지도 보고, 여행 후기도 남기고</p>
        </div>

        <br>
        <br>
        <br>

        <h3 class="noticeTitle">&nbsp;📢 Notice</h3>
        <h3 class="photoTitle">&nbsp;✏️_여행 후기</h3>
        <div class="main-notice">
            <table class="board-table">
                <tr>
                    <td>📢</td>
                    <td>제목</td>
                    <td>작성자</td>
                    <td>작성일</td>
                    <td>조회수</td>
                </tr>
                <c:forEach var="dto" items="${noticeList}">
                <tr>
                    <td>
                        <span>공지</span>
                    </td>
                    <td class="notice">
                        <a href="<%=cp%>/notice/view.do?num=${dto.num}&page=1">${dto.title}</a>
                    </td>
                    <td>${dto.name }</td>
                    <td>${dto.created }</td>
                    <td>${dto.viewCount }</td>
                </tr>
                </c:forEach>
            </table>
        </div>

        <div class="main-photo">
            <input type="text" id="photoNum" hidden>
            <c:forEach var="dto" items="${photoList}">
                <img onclick="ListPhoto('${dto.photoNum}')" alt="" src="<%=cp%>/uploads/photo/${dto.saveFilename}">
            </c:forEach>
        </div>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
    </div>
</body>
</html>