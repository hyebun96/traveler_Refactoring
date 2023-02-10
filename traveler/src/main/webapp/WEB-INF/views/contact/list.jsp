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

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resources/js/contact.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > CONTACT목록확인</div>
    </div>

    <div class="board">
        <h3>CONTACT 목록 확인</h3>
        <form name="searchForm" action="<%=cp%>/contact/list.do" method="post">
            <div>
                <label>
                    <select name="condition">
                        <option value="ctSubject" ${condition=="ctSubject"?"selected='selected'":""}>글제목</option>
                        <option value="ctContent" ${condition=="ctContent"?"selected='selected'":""}>글내용</option>
                        <option value="ctName" ${condition=="ctName"?"selected='selected'":""}>작성자</option>
                    </select>
                </label>
                <input type="text" name="keyword" value="${keyword}">
                <input type="hidden" name="ctSort" value="${ctSort}">
                <button class="btn btn-hover" type="button" onclick="searchList()">검색</button>
                <button class="btn-hover" type="button" onclick="location.href='<%=cp%>/contact/list.do';">새로고침</button>
            </div>
        </form>

        <br>
        <br>

        <table class="menu">
            <tr>
                <td id="menu-all"><a href="javascript:searchList2('all');">전체</a></td>
                <td id="menu-sugg"><a href="javascript:searchList2('sugg');">제안</a></td>
                <td id="menu-edit"><a href="javascript:searchList2('edit');">정보 수정 요청</a></td>
                <td id="menu-ad"><a href="javascript:searchList2('ad');">광고문의</a></td>
                <td id="menu-etc"><a href="javascript:searchList2('etc');">기타</a></td>
                <td><input hidden id="ctSort" type="hidden" name="ctSort" value="${ctSort}"></td>
            </tr>
        </table>

        <br>

        <table class="board-table">
            <tr class="board-tr">
                <td>분류</td>
                <td>번호</td>
                <td class="board-title">제목</td>
                <td class="board-writer">작성자</td>
                <td>작성일</td>
            </tr>
            <c:forEach var="dto" items="${list}">
                <tr style="background-color:${dto.fin==1 ? '#efefef':''}">
                    <td>${dto.ctSort}</td>
                    <td>${dto.ctNum }</td>
                    <td class="board-title"><a href="${viewUrl}&ctNum=${dto.ctNum}">${dto.ctSubject}</a></td>
                    <td class="board-writer">${dto.ctName }</td>
                    <td>${dto.ctDate}</td>
                </tr>
            </c:forEach>
            <tr class="board-paging" height="35">
                ${dataCount == 0 ? "<td colspan='5'>등록된 게시물이 없습니다.<td>" : paging}
            </tr>
        </table>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>