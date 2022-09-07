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
    <link rel="stylesheet" href="<%=cp%>/resource/css/notice_list.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/board.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > 자유게시판</div>
    </div>

    <div class="board">
        <h3>자유게시판</h3>
        <form name="searchForm" action="<%=cp%>/board/list.do" method="post">
            <div>
                <label>
                    <select name="condition">
                        <option value="title" ${condition == "title" ? "selected='selected'" : ""}>글제목</option>
                        <option value="contents" ${condition == "contents" ? "selected='selected'" : ""}>글내용</option>
                        <option value="writer" ${condition == "writer" ? "selected='selected'" : ""}>작성자</option>
                    </select>
                </label>
                <input type="text" name="keyword">
                <button onclick="searchList()">검색</button>
                <button type="button" onclick="location.href='<%=cp%>/board/list.do';">새로고침</button>
            </div>
        </form>
        
        <br>
        <br>

        <table class="board-table">
            <tr class="board-tr">
                <td>글번호</td>
                <td class="board-title">제목</td>
                <td class="board-writer">작성자</td>
                <td>작성일</td>
                <td>조회수</td>
            </tr>
            <c:forEach var="dto" items="${list}">
                <tr class="board-tr">
                    <td>${dto.listNum}</td>
                    <td class="board-title">
                        <a href="${articleUrl}&num=${dto.num}">${dto.title}</a>
                    </td>
                    <td class="board-writer">
                        <img id="profile" src="<%=cp%>/resource/img/${dto.imageFilename}">${dto.name}
                    </td>
                    <td>${dto.created}</td>
                    <td>${dto.viewCount}</td>
                </tr>
            </c:forEach>
            <tr class="board-paging" height="35">
                ${dataCount == 0 ? "<td colspan='5'>등록된 게시물이 없습니다.<td>" : paging}
            </tr>
            <tr height="35" class="board-created">
                <td colspan="5">
                    <button type="button" onclick="location.href='<%=cp%>/board/write.do';">글등록</button>
                </td>
            </tr>
        </table>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>