<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String cp = request.getContextPath();
%>

<link rel="stylesheet" href="<%=cp%>/resource/css/header.css" type="text/css">
<script src="http://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="<%=cp%>/resource/js/header.js"></script>

<div class="header-top">
    <div class="header-center">
        <p style="margin: 2px;">
            <a href="<%=cp %>/main.do">
                <img id="logo" alt="HOME" src="<%=cp %>/resource/img/logo3.png">
            </a>
        </p>
    </div>

    <div class="header-right">
        <div class="header-nav">
            <div class="menu">
                <ul class="nav">
                    <li>
                        <a href="<%=cp%>/travel/list.do?type=seoul">여행지</a>
                        <ul>
                            <li><a href="<%=cp%>/travel/list.do?type=Seoul">서울</a></li>
                            <li><a href="<%=cp%>/travel/list.do?type=Gangwon-do">강원</a></li>
                            <li><a href="<%=cp%>/travel/list.do?type=Chungcheongbuk-do">충북</a></li>
                            <li><a href="<%=cp%>/travel/list.do?type=Gwangju">광주</a></li>
                            <li><a href="<%=cp%>/travel/list.do?type=Gyeongsangbuk-do">경북</a></li>
                            <li><a href="<%=cp%>/travel/list.do?type=Jeju">제주</a></li>
                        </ul>
                    </li>

                    <li class="small-nav">
                        <a href="<%=cp %>/photo/photoMain.do">GALLERY</a>
                        <ul>
                            <li><a class="small-nav-a" href="<%=cp %>/photo/photoMain.do">사진</a></li>
                        </ul>
                    </li>

                    <li class="small-nav">
                        <a href="<%=cp %>/notice/notice.do">BOARD</a>
                        <ul>
                            <li><a class="small-nav-a" href="<%=cp %>/notice/notice.do">공지사항</a></li>
                            <li><a class="small-nav-a" href="<%=cp%>/qna/list.do">Q&amp;A</a></li>
                            <li><a class="small-nav-a" href="<%=cp %>/board/board.do">자유게시판</a></li>
                        </ul>
                    </li>

                    <li class="small-nav">
                        <a href="<%=cp%>/contact/contact.do">CONTACT</a>
                        <ul>
                            <li><a class="small-nav-a" href="<%=cp%>/contact/contact.do">CONTACT</a></li>
                            <c:if test="${sessionScope.member.userId=='admin'}">
                                <li><a href="<%=cp%>/contact/list.do">목록확인</a></li>
                            </c:if>
                        </ul>
                    </li>
                </ul>

                <ul class="nav2">
                    <li>
                        <c:if test="${empty sessionScope.member}">
                            <a href="<%=cp%>/member/login.do">login</a>
                            &nbsp;
                            <a href="<%=cp%>/member/member.do">sign up</a>
                        </c:if>
                        <c:if test="${not empty sessionScope.member}">
                            <span>${sessionScope.member.userName}</span>님
                            &nbsp;
                            <a href="<%=cp%>/member/logout.do">Logout</a>
                            &nbsp;
                            <a href="<%=cp%>/member/pwd.do?mode=update">
                                <img id="profile" src='<%=cp%>/resource/img/${sessionScope.member.userId != null ?  sessionScope.member.imageFilename :  "user1"}.png' />
                            </a>
                        </c:if>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
	
	


