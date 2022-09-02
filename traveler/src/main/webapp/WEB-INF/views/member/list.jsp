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
    <title>Insert title here</title>
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">
    <link rel="stylesheet" href="<%=cp%>/resource/css/style.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resource/css/main.css" type="text/css">
    <link rel="stylesheet" href="<%=cp%>/resource/css/member_list.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/member.js"></script>

</head>
<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
</div>
<div class="navigation">
    <div class="nav-bar">HOME > 회원정보목록</div>
</div>

<div class="member">
    <h3>회원정보 리스트</h3>
    <form name="searchForm" class="searchForm" action="<%=cp%>/member/list.do" method="post">
        <div class="member-list">
            <select name="condition">
                <option value="userId" ${condition=="userId"?"selected='selected'":"" }>회원아이디</option>
                <option value="userName" ${condition=="userName"?"selected='selected'":"" }>회원이름</option>
            </select>
            <input id="search-input" type="text" name="keyword">
            <button id="search-button" onclick="searchList()">검색</button>
            <button id="search-reset" type="button" onclick="javascript:location.href='<%=cp%>/member/list.do';">새로고침</button>
            <br>
        </div>
    </form>

    <table class="member-table">
        <br><br>
        <tr>
            <td>회원아이디</td>
            <td>회원이름</td>
            <td>전화번호</td>
            <td>이메일</td>
            <td>생년월일</td>
        </tr>
        <c:forEach var="dto" items="${list}">
            <tr>
                <td>${dto.userId}</td>
                <td>${dto.userName}</td>
                <td>${dto.userTel}</td>
                <td>${dto.userEmail}</td>
                <td>${dto.userBirth}</td>
            </tr>
        </c:forEach>
        <tr height="35">
            <td>
                ${dataCount==0?"등록된 회원이 없습니다.":paging}
            </td>
        </tr>
    </table>


</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>
</body>
</html>