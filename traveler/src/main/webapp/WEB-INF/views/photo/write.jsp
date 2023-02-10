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
    <link rel="stylesheet" href="<%=cp%>/resources/css/write.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resources/js/photo.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > Photo</div>
    </div>

    <div class="write">
        <h3>Photo</h3>
        <br>

        <form class="writeBoardForm" name="photoForm" method="post" enctype="multipart/form-data">
            <div class="travel-box">

                <c:if test="${mode == 'updated'}">
                    <input name="imageFilename" value="${dto.imageFilename}" hidden>
                    <input name="photoNum" value="${dto.photoNum}" hidden>
                </c:if>

                <p>&nbsp;작성자 </p>
                    ${sessionScope.member.userId} (${sessionScope.member.userName})
                    <img src="<%=cp%>/resources/img/${sessionScope.member.imageFilename}.png" alt="" >
                <br>
                <p>장&nbsp;&nbsp;&nbsp;소 </p>
                <input class="place2" name="place" type="text" value="${dto.place}">
                <p>제&nbsp;&nbsp;&nbsp;목 </p>
                <input class="place2" name="subject" type="text" value="${dto.subject}">
                <br>
                <p class="content">정&nbsp;&nbsp;&nbsp;보</p>
                <textarea name="content" class="boxTA">${dto.content}</textarea>
                <c:if test="${dto.imageFilename == null}">
                    <p class="content">파일 첨부</p>
                    <input type="file" name="upload" value="${dto.originalFileName}">
                    <br>
                </c:if>
                <c:if test="${dto.originalFileName != null}">
                    <input type="file" name="upload" value="${dto.originalFileName}" hidden>
                    <p>첨부된 파일</p>
                    <div class="imageList">
                            ${dto.originalFileName} &nbsp;&nbsp;|&nbsp;&nbsp; <a onclick="deleteFile('${mode}', '${dto.photoNum}');">삭제</a><br>
                    </div>
                </c:if>
            </div>
            <div class="div-button">
                <button type="button" class="btn btn-hover" onclick="sendPhoto('${mode}')">${mode=='updated'?'수정완료':'등록하기'}</button>
                <button type="reset" class="btn btn-hover">다시입력</button>
                <button type="button" class="btn btn-hover" onclick="location.href='<%=cp%>/photo/list.do'">${mode=='updated'?'수정취소':'등록취소'}</button>
            </div>
        </form>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>