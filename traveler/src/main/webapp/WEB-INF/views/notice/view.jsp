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
    <link rel="stylesheet" href="<%=cp %>/resource/css/notice_view.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/notice.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > 공지사항</div>
    </div>

    <div class="viewport">
        <h3>공지사항</h3>
        <br>
        <br>

        <table class="view-table">
            <tr>
                <td class="view"></td>
                <td class="view"></td>
                <td class="view">
                    <img id="profile" src="<%=cp%>/resource/img/user.png"/>${dto.name}
                </td>
                <td class="view">${dto.created}</td>
            </tr>
            <tr>
                <td class="view-title title">제목</td>
                <td class="title" colspan="4">${dto.title}</td>
            </tr>
            <tr>
                <td class="view-title">내용</td>
                <td class="view-content" colspan="4">${dto.content}</td>
            </tr>
            <tr>
                <td class="view-title">첨부</td>
                <td colspan="2">
                    <c:forEach var="dto" items="${list}">
                        <c:if test="${not empty list}">
                            <a href="<%=cp%>/notice/download.do?fileNum=${dto.fileNum}">${dto.originalFileName}</a><br>
                        </c:if>
                    </c:forEach>
                    <c:if test="${empty list}">
                        첨부파일이 존재하지 않습니다.
                    </c:if>
                </td>
                <td class="view">조회수&nbsp;&nbsp;👀&nbsp;&nbsp;${dto.viewCount}</td>
            </tr>
        </table>

        <div class="div-button">
            <button type="button" class="btn-hover" onclick="location.href='<%=cp%>/notice/list.do?${query}';">목록으로</button>
            <button type="button" class="btn btn-hover" onclick="updateNotice('${dto.num}', '${sessionScope.member.userId}', '${dto.id}', '${page}', '${query}');">수정</button>
            <button type="button" class="btn btn-hover" onclick="deleteNotice('${dto.num}', '${sessionScope.member.userId}', '${dto.id}', '${query}');">삭제</button>
        </div>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>