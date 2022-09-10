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

    <div class="write">
        <h3>공지사항</h3>
        <br>
        <br>

        <form class="writeBoardForm" name="writeBoardForm" method="post" enctype="multipart/form-data">
            <input type="hidden" name="page" value="${page}">
            <c:if test="${mode =='update'}">
                <input type="hidden" name="num" value="${dto.num}">
                <input type="hidden" name="page" value="${page}">
            </c:if>

            <div>
                <p>제목</p>
                <label>
                    <input class="write-title" name="title" type="text" value="${dto.title}">
                </label>
                <br>
                <p>중요</p>
                <label>
                    <input type="checkbox" name="important" value="1" ${dto.important==1 ? "checked='checked' ":"" }>
                    체크하면 공지사항 상단에 계속 표시됩니다.
                </label>
                <br>
                <div class="write-content">
                    <p>내용 </p>
                    <textarea name="content">${dto.content}</textarea>
                </div>
                <br>
                <p>첨부</p>
                    <input class="upload" type="file" name="upload" multiple="multiple">
                <br>
                <c:if test="${mode=='update'}">
                    <p>첨부된 파일</p>
                    <c:forEach var="dto" items="${list}">
                        <c:if test="${not empty list}">
                            ${dto.originalFileName}
                            | <a href="javascript:deleteNoticeFile('${dto.num}', '${dto.fileNum}', '${mode}', '${page}');">삭제</a>
                            <br>
                        </c:if>
                    </c:forEach>
                </c:if>
            </div>
            <div class="div-button">
                <button class="btn-hover" type="button" onclick="createNotice('${mode}');">${mode=='update'?'수정':'등록'}</button>
                <button class="btn-hover" type="button" onclick="location.href='<%=cp%>/notice/list.do';">목록으로</button>
            </div>
        </form>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>