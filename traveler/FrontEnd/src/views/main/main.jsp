<%@ page import="java.util.Date" %>
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
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>

    <script src="https://code.jquery.com/jquery-3.6.0.js"
    integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/photo.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/main.js"></script>
    <script type="text/javascript" src="https://kit.fontawesome.com/667371032c.js" crossorigin="anonymous"></script>
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
                <img alt="" src="<%=cp%>/resource/img/clock.png">
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
                            <img src="<%=cp%>/uploads/travel/${dto.saveFileName}" class="slider-image">
                        </a>
                    </div>
                    </c:forEach>
                </div>
            </div>
            <div class="post-move">
                <span class="prev"><i class="fa-solid fa-circle-chevron-left"></i></span>
                <span class="move-box">&nbsp;&nbsp;???????????????? ???????????? ?????????&nbsp;&nbsp;</span>
                <span class="next"><i class="fa-solid fa-circle-chevron-right"></i></span>
            </div>
        </div>

        <br>
        <br>
        <br>

        <div class=main-title>
            <h3>Welcome ????????????????????</h3>
            <p>???????????? ???????????? ????????? ????????? ??????????????????.</p>
            <p>????????? ?????? ???????????? ??????, ?????? ????????? ?????????</p>
        </div>

        <br>
        <br>
        <br>

        <h3 class="noticeTitle">&nbsp;???? Notice</h3>
        <h3 class="photoTitle">&nbsp;??????_?????? ??????</h3>
        <div class="main-notice">
            <table class="board-table">
                <tr>
                    <td>????</td>
                    <td>??????</td>
                    <td>?????????</td>
                    <td>?????????</td>
                    <td>?????????</td>
                </tr>
                <c:forEach var="dto" items="${noticeList}">
                <tr>
                    <td>
                        <span>??????</span>
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
                <img onclick="ListPhoto('${dto.photoNum}')" alt="" src="<%=cp%>/uploads/photo/${dto.imageFilename}">
            </c:forEach>
        </div>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
    </div>
</body>
</html>