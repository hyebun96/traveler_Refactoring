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

    <script type="text/javascript" src="<%=cp%>/resource/js/qna.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > Q&A</div>
    </div>

    <div class="viewport">
        <h3>Q & A</h3>

        <br>
        <br>

        <table class="view-table">
            <tr>
                <td class="view"></td>
                <td class="view"></td>
                <td class="view">
                    <img id="profile" src="<%=cp%>/resource/img/${dto.imageFilename}"/>${dto.userId}
                </td>
                <td class="view">${dto.created}</td>
            </tr>
            <tr>
                <td class="view-title title">제목</td>
                <td class="title" colspan="4">${dto.subject}</td>
            </tr>
            <tr>
                <td class="view-title">내용</td>
                <td class="view-content" colspan="4">${dto.content}</td>
            </tr>
        </table>

        <div class="div-button">
            <button class="btn btn-hover" type="button" onclick="location.href='<%=cp%>/qna/list.do?${query}';">목록으로</button>
            <c:if  test="${sessionScope.member.userId == 'admin' && dto.depth==0}">
                <button type="button" class="btn btn-hover" onclick="location.href='<%=cp%>/qna/reply.do?qnaNum=${dto.qnaNum}&page=${page}';">답변</button>
            </c:if>
            <c:if test="${sessionScope.member.userId == dto.userId}">
                <button type="button" class="btn btn-hover" onclick="updateQna('${dto.qnaNum}', '${sessionScope.member.userId}', '${dto.userId}', '${page}');">수정</button>
            </c:if>
            <c:if test="${sessionScope.member.userId != dto.userId}">
                <button type="button" class="btn btn-hover" disabled="disabled" hidden="hidden">수정</button>
            </c:if>
            <button type="button" class="btn btn-hover" onclick="deleteQna('${dto.qnaNum}', '${sessionScope.member.userId}', '${dto.userId}', '${page}');">삭제</button>
        </div>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>