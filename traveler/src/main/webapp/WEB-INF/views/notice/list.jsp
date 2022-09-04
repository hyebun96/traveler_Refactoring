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
    <link rel="stylesheet" href="<%=cp%>/resource/css/notice_list.css" type="text/css">

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

    <div class="board">
        <h3>공지사항</h3>
        <form name="searchForm" method="post">
            <div>
                <label>
                    <select name="condition">
                        <option value="title" ${condition == "title" ? "selected='selected'" : ""}>글제목</option>
                        <option value="contents" ${condition == "contents"?"selected='selected'" : ""}>글내용</option>
                    </select>
                </label>
                <input type="text" name="keyword">
                <button class="board-button" type="button" onclick="searchNoticeList()">검색</button>
                <button class="board-button" type="button" onclick="javascript:location.href='<%=cp%>/notice/notice.do';">
                    새로고침
                </button>
            </div>
        </form>

        <br>
        <br>

        <table class="board-table">
            <tr>
                <td>글번호</td>
                <td class="board-title"><a>제목</a></td>
                <td>작성자</td>
                <td>작성일</td>
                <td>조회수</td>
            </tr>
            <c:forEach var="dto" items="${importantList}" begin="0" end="4">
                <tr>
                    <td>
                        <span id="board-must">필독!</span>
                    </td>
                    <td class="board-title">
                        <a href="${articleUrl}&num=${dto.num}">${dto.title}</a>
                    </td>
                    <td>${dto.name}</td>
                    <td>${dto.created}</td>
                    <td>${dto.viewCount}</td>
                </tr>
            </c:forEach>
            <c:forEach var="dto" items="${list}">
                <tr>
                    <td>${dto.listNum}</td>
                    <td class="board-title">
                        <a href="${articleUrl}&num=${dto.num}">${dto.title}</a>
                    </td>
                    <td>${dto.name}</td>
                    <td>${dto.created}</td>
                    <td>${dto.viewCount}</td>
                </tr>
            </c:forEach>
            <tr class="board-paging" height="35">
                ${dataCount == 0 ? "<td colspan='5'>등록된 게시물이 없습니다.<td>" : paging}
            </tr>
            <c:if test="${sessionScope.member.userId == 'admin'}">
                <tr height="35" class="board-created">
                    <td colspan="5">
                        <button class="board-button" type="button"
                                onclick="createNoticeForm('${sessionScope.member.userId}', '${page}');">
                            글등록
                        </button>
                    </td>
                </tr>
            </c:if>
        </table>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>