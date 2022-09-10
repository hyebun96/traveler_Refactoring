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
    <script type="text/javascript" src="<%=cp%>/resource/js/qna.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > Q&A</div>
    </div>

    <div class="board">
        <h3>Q & A</h3>
        <form name="searchForm" action="<%=cp%>/qna/list.do" method="post">
            <div>
                <label>
                    <select name="condition">
                        <option value="subject" ${condition=="subject"?"selected='selected'":""}>글제목</option>
                        <option value="content" ${condition=="content"?"selected='selected'":""}>글내용</option>
                        <option value="userName" ${condition=="userName"?"selected='selected'":""}>작성자</option>
                    </select>
                </label>
                <input type="text" name="keyword">
                <button class="board-button btn-hover" onclick="searchList()">검색</button>
                <button class="board-button btn-hover" type="button" onclick="location.href='<%=cp%>/qna/list.do';">새로고침</button>
            </div>
        </form>

        <br>
        <br>

        <table class="board-table">
            <tr>
                <td class="qna-num">종류</td>
                <td class="qna-num">글번호</td>
                <td class="qna-title"><a>제목</a></td>
                <td class="qna-num">작성자</td>
                <td class="qna-num">작성일</td>
                <td class="qna-num">조회수</td>
            </tr>
            <c:forEach var="dto" items="${list}">
                <tr class="question">
                    <td>질문🙋🏻</td>
                    <td>${dto.listNum}</td>
                    <td class="qna-title">
                        <c:if test="${sessionScope.member.userId == 'admin' || sessionScope.member.userId == dto.userId}">
                            <a href="${viewUrl}&qnaNum=${dto.qnaNum}">${dto.subject}</a>
                        </c:if>
                        <c:if test="${sessionScope.member.userId != dto.userId && sessionScope.member.userId != 'admin'}">
                            <a onclick="memberOnly();">${dto.subject}🔒</a></c:if>
                    </td>
                    <td>${dto.userName}</td>
                    <td>${dto.created}</td>
                    <td>${dto.hitCount}</td>
                </tr>
                <c:forEach var="replyDto" items="${replyList}">
                    <c:if test="${replyDto.parent == dto.qnaNum}">
                        <tr>
                            <td>답변🍀</td>
                            <td></td>
                            <td class="qna-title">
                            &nbsp;└&nbsp;re:
                            <c:if test="${sessionScope.member.userId == 'admin' || sessionScope.member.userId == qnaId}">
                                <a href="${viewUrl}&qnaNum=${replyDto.qnaNum}">${replyDto.subject}</a>
                            </c:if>
                            <c:if test="${sessionScope.member.userId != qnaId && sessionScope.member.userId != 'admin'}">
                                <a onclick="memberOnly();">${replyDto.subject}🔒</a></c:if>
                            </td>
                            <td>${replyDto.userName}</td>
                            <td>${replyDto.created}</td>
                            <td>${replyDto.hitCount}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:forEach>
            <tr class="board-paging" height="35">
                ${dataCount == 0 ? "<td colspan='6'>등록된 게시물이 없습니다.<td>" : paging}
            </tr>
            <tr class="board-created">
                <td colspan="6">
                    <button class="board-button btn-hover" type="button" onclick="location.href='<%=cp%>/qna/write.do';">글등록</button>
                </td>
            </tr>
        </table>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>