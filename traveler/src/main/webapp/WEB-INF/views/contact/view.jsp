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
        <div class="nav-bar">HOME > CONTACT 목록 확인</div>
    </div>

    <div class="contact">
        <h3>CONTACT</h3>
        <br>

        <input type="hidden" name="ctNum" value="${dto.ctNum}">
        <input type="hidden" name="page" value="${page}">
        <input type="hidden" name="rows" value="${rows}">

        <div class="contactView">
        <ul class="list-row">
            <li class="col-sub2">작성자</li>
            <li class="col-input2">${dto.ctName}</li>
            <li class="col-sub2">분류</li>
            <li class="col-input2">${dto.ctSort}</li>
        </ul>
        <ul class="list-row">
            <li class="col-sub2">전화번호</li>
            <li class="col-input3">${dto.ctTel}</li>
        </ul>
        <ul class="list-row">
            <li class="col-sub2">작성시간</li>
            <li class="col-input3">${dto.ctDate}</li>
        </ul>
        <ul class="list-row">
            <li class="col-sub2">제목</li>
            <li class="col-input3">${dto.ctSubject}</li>
        </ul>
        <ul class="list-row">
            <li class="col-sub2">내용</li><br>
            <li class="col-text">${dto.ctContent}</li>
            <li class="div-button">
                <button type="button" class="btn-hover" onclick="javascript:location.href='<%=cp%>/contact/list.do?${query}';">목록으로</button>
                <button type="button" class="btn btn-hover" onclick="deleteContact('${dto.ctNum}', '${sessionScope.member.userId}', '${query}');">삭제</button>
            </li>
        </ul>
        </div>

        <form class="contactView" name="mailForm" method="post">
            <ul class="list-row">
                <li class="col-input3">관리자는 이메일로 회신이 가능합니다.</li>
            </ul>
            <ul class="list-row">
                <li class="col-sub2">보내는 사람</li>
                <li class="col-input3"><input name="senderEmail" value="hyebun1996@gmail.com" readonly></li>
            </ul>
            <ul class="list-row">
                <li class="col-sub2">받는 사람</li>
                <li class="col-input3"><input name="receiverEmail" value="${dto.ctEmail}"></li>
            </ul>
            <ul class="list-row">
                <li class="col-sub2">제목</li>
                <li class="col-input3">
                    <input type="text" name="subject" value="Re : '${dto.ctSubject}' 대한 답변입니다.">
                </li>
            </ul>
            <ul class="list-row">
                <li class="col-sub2">내용</li><br>
                <li class="col-input3">
                    <textarea name="contant"></textarea>
                </li>
                <li class="div-button">
                    <button type="button" class="btn-hover" onclick="mailContact('${query}')">메일전송</button>
                    <button type="button" class="btn btn-hover" onclick="doneORUndoneContact('${dto.ctNum}', '${sessionScope.member.userId}', ${dto.fin}, '${query}');">
                        ${dto.fin == 0 ? '완료 처리' : '미완료처리' }
                    </button>
                </li>
            </ul>
        </form>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>