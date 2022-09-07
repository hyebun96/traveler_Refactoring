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
    <link rel="stylesheet" href="<%=cp %>/resource/css/write.css" type="text/css">

    <script type="text/javascript" src="<%=cp%>/resource/js/qna.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > Q&A</div>
    </div>

    <div class="write">
        <h3>Q & A</h3>

        <br>
        <br>

        <form class="writeBoardForm" name="writeBoardForm" method="post">
            <input type="hidden" name="page" value="${page}">
            <c:if test="${mode=='update'}">
                <input type="hidden" name="qnaNum" value="${dto.qnaNum}">
                <input type="hidden" name="condition" value="${condition}">
                <input type="hidden" name="keyword" value="${keyword}">
            </c:if>
            <c:if test="${mode=='reply'}">
                <input type="hidden" name="groupNum" value="${dto.groupNum}">
                <input type="hidden" name="depth" value="${dto.depth}">
                <input type="hidden" name="parent" value="${dto.qnaNum}">
            </c:if>

            <div>
                <p>제목</p>
                <label>
                    <input  class="write-title" name="subject" type="text" value="${dto.subject}">
                </label>
                <br>

                <div class="write-content">
                    <p>내용 </p>
                    <textarea name="content">${dto.content}</textarea>
                </div>
                <br>
            </div>
            <div class="div-button">
                <button type="button" onclick="createQNA('${mode}');">${mode=='update'?'수정':'등록'}</button>
                <button type="button" onclick="location.href='<%=cp%>/qna/list.do';">목록으로</button>
            </div>
        </form>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>