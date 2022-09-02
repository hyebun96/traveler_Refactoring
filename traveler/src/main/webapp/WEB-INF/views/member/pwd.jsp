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
    <link rel="stylesheet" href="<%=cp%>/resource/css/member_pwd.css" type="text/css">

    <script type="text/javascript" src="<%=cp%>/resource/js/member.js"></script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
</div>
<div class="navigation">
    <div class="nav-bar">HOME > MyPage</div>
</div>

<!-- login -->
<div class="main">
    <div class="login">
        <span>패스워드 재확인</span>
        <br>
    </div>

    <c:if test="${message == null }">
        <p class="msg"><span>정보보호를 위해 패스워드를 다시 입력해주세요.</span></p>
    </c:if>
    <c:if test="${message != null }">
        <p class="error-msg"><span>&nbsp;${message}</span></p>
    </c:if>

    <div class="index">
        <form name="pwdForm" action="javascript:send();" method="post">
            <input type="text" name="userId" required="required" maxlength="10" pattern="[a-zA-Z0-9]+"
                   value="${sessionScope.member.userId}" readonly="readonly">
            <span data-placeholder="UserID"></span>
            <input type="password" name="userPwd" required="required" maxlength="10" pattern="[a-zA-Z0-9]+"
                   placeholder="Password">
            <span data-placeholder="Password"></span>
            <input type="hidden" name="mode" value="${mode}">
            <button type="button" onclick="pwdOk();">확인</button>
        </form>

    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp" />
</div>
</body>
</html>