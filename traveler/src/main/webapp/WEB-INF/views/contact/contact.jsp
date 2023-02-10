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
    <link rel="stylesheet" href="<%=cp%>/resources/css/contact.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resources/js/contact.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > CONTACT</div>
    </div>

    <div class="main">
        <br>

        <div class="title">
            <h1>CONTACT</h1>
        </div>

        <br>

        <form class="contactForm" name="contactForm" method="post">
            <br>
            <ul class="list-row">
                <li class="col-sub">이름</li>
                <li class="col-input">
                    <input type="text" name=ctName class="boxTF">
                </li>
                <li class="col-sub">분류</li>
                <li class="col-input">
                    <select name="ctSort" class="selectField">
                        <option value="">::선 택::</option>
                        <option value="sugg" ${dto.ctSort=="sugg" ? "selected='selected'" : ""}>제안</option>
                        <option value="edit"  ${dto.ctSort=="edit" ? "selected='selected'" : ""}>정보수정요청</option>
                        <option value="ad" ${dto.ctSort=="ad" ? "selected='selected'" : ""}>광고문의</option>
                        <option value="etc" ${dto.ctSort=="etc" ? "selected='selected'" : ""}>기타</option>
                    </select>
                </li>
            </ul>
            <ul class="list-row">
                <li class="col-sub">이메일</li>
                <li class="col-input">
                    <input type="text" name=ctEmail class="boxTF">
                </li>
                <li class="col-sub">전화번호</li>
                <li class="col-input">
                    <input type="text" name=ctTel class="boxTF">
                </li>
            </ul>
            <ul class="list-row" >
                <li class="col-sub">제목</li>
                <li class="col-input">
                    <input type="text" name="ctSubject" class="boxTF sub">
                </li>
            </ul>
            <ul class="list-row" >
                <li class="col-sub">내용<br></li>
            </ul>
            <ul class="list-row" >
                <li>
                    <textarea id="content" name="ctContent" rows="12" cols="53"></textarea>
                </li>
                <li class="div-button">
                    <button type="button" class="btn" onclick="sendOk();"> 전송</button>
                    <button type="reset" class="btn"> 다시입력</button>
                </li>
            </ul>
        </form>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>