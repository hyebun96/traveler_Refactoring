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
    <link rel="stylesheet" href="<%=cp%>/resources/css/style.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resources/css/main.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resources/css/notice_list.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resources/css/write.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
           integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resources/js/board.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > 자유게시판</div>
    </div>

    <div class="write">
        <h3>자유게시판</h3>

        <br>
        <br>

        <form class="writeBoardForm" name="writeBoardForm" method="post">
            <c:if test="${mode=='update'}">
                <input type="hidden" name="num" value="${dto.num}">
                <input type="hidden" name="page" value="${page}">
            </c:if>
            <div>
                <p>제목</p>
                <label>
                    <input class="write-title" name="title" type="text" value="${dto.title}">
                </label>
                <br>
                <div class="write-content">
                    <p>내용 </p>
                    <textarea name="content">${dto.content}</textarea>
                </div>
                <br>
            </div>

            <div class="div-button">
                <button type="button" class="btn-hover" onclick="createBoard('${mode}');">${mode=='update'?'수정':'등록'}</button>
                <button type="button" class="btn-hover" onclick="location.href='<%=cp%>/board/list.do';">목록으로</button>
            </div>
        </form>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>