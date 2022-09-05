<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Traveler</title>
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resource/css/main.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resource/css/member_login.css" type="text/css">
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">권한 제한</div>
    </div>

    <div class=main>
        <div class="login">
            <h1>권한 제한</h1>
        </div>
        <p class="error-msg">권한이 존재하지 않습니다.</p>
        <div class="index">
            <button type="button" onclick="location.href='<%=cp%>${query}';">목록으로</button>
        </div>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>