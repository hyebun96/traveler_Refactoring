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
    <link rel="stylesheet" href="<%=cp%>/resources/css/member_mypage.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resources/js/member.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp" />
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > MyPage</div>
    </div>

    <div class="main">
        <div class="signup">
            <h1>${title}<img class="profile" src="<%=cp%>/resources/img/${dto.imageFilename}.png"/>
            </h1>
        </div>

        <div class="index">
            <form name="memberForm" class="memberForm" action="javascript:send();" method="post" enctype="multipart/form-data">
                <p>
                    <span>&nbsp;&nbsp;&nbsp;&nbsp;I&nbsp;D&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <input type="text" name="userId" value="${dto.userId}" class="imo input-box" readonly="readonly"/>
                </p>
                <p>
                    <span>&nbsp;&nbsp;이&nbsp;름&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <input type="text" name="userName" value="${dto.userName}" readonly="readonly" class="input-box"/>
                </p>
                <p>
                    <span>전화번호&nbsp;&nbsp;</span>
                    <input type="text" name="tel" value="${dto.tel1} - ${dto.tel2} - ${dto.tel3}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" readonly="readonly" class="input-box"/>
                    <input type="hidden" name="tel1" value="${dto.tel1}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" class="tel" readonly="readonly">
                    <input type="hidden" name="tel2" value="${dto.tel2}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" class="tel" readonly="readonly">
                    <input type="hidden" name="tel3" value="${dto.tel3}" required="required" maxlength="11" pattern="[0-9]+" placeholder="tel" class="tel" readonly="readonly">
                </p>
                <p>
                    <span>&nbsp;&nbsp;e-mail&nbsp;&nbsp;&nbsp;</span>
                    <input class="email input-box" type="text" name="email1" value="${dto.email1} @ ${dto.email2}" required="required" size="13" readonly="readonly"/>
                    <input class="email input-email" type="hidden" name="email1" value="${dto.email1}" readonly="readonly">
                    <input class="email" type="hidden" name="email2" value="${dto.email2}" readonly="readonly">
                </p>
                <p>
                    <span>생년월일&nbsp;&nbsp;</span>
                    <input type="text" name="userBirth" value="${dto.userBirth}" readonly="readonly" class="input-box"/>
                </p>

                <c:if test="${sessionScope.member.userId!='admin'}">
                    <button class="indexBtn updateBtn btn-hover" type="button" name="sendButton" onclick="memberUpdate('update');"> 정보수정 </button>
                    <button class="indexBtn btn-hover" type="button" name="sendButton" onclick="memberUpdate('delete');">회원탈퇴</button>
                </c:if>
            </form>
        </div>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>