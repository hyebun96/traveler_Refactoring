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
    <link rel="stylesheet" href="<%=cp%>/resource/css/member_login.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/member.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > LOGIN</div>
    </div>

    <div class="main">
        <div class="login">
            <h1>Login</h1>
        </div>
        <p class="error-msg">
            <span>&nbsp;${message}</span>
        </p>
        <div class="index">
            <form name="loginForm" class="loginForm" action="javascript:send();" method="post">
                <input type="text" name="userId" required="required" maxlength="20" pattern="[a-zA-Z0-9]+" placeholder="UserID">
                <span data-placeholder="UserID"></span>
                <input type="password" name="userPwd" required="required" maxlength="10" pattern="[a-zA-Z0-9]+" placeholder="Password">
                <span data-placeholder="Password"></span>
                <button class="btn-hover" type="button" onclick="sendLogin();">Login</button>
            </form>
        </div>
        <div class="bottom-text">
            Don't have account? <a href="<%=cp%>/member/member.do">Sign up</a>
        </div>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
    </div>
</body>
</html>