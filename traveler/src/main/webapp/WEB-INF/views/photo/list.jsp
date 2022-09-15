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
    <link rel="stylesheet" href="<%=cp%>/resource/css/photo.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/photo.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > Gallay</div>
    </div>

    <div class="photo">
        <div>
            <p class="title">Photo</p>
            <c:if test="${sessionScope.member.userId != null}">
                <p class="createBtn">
                    <button class="btn-hover" type="button" onclick="location.href='<%=cp%>/photo/created.do'">글등록</button>
                </p>
            </c:if>
        </div>

        <div class="photo-menu post-move">
            <a href=""><img alt="" src="<%=cp%>/resource/img/namsan.jpg">서울</a>
            <a href=""><img alt="" src="<%=cp%>/resource/img/inje.jpg">강원</a>
            <a href=""><img alt="" src="<%=cp%>/resource/img/samyang.jpg">충북</a>
            <a href=""><img alt="" src="<%=cp%>/resource/img/namsan.jpg">광주</a>
            <a href=""><img alt="" src="<%=cp%>/resource/img/namsan.jpg">경북</a>
            <a href=""><img alt="" src="<%=cp%>/resource/img/namsan.jpg">제주</a>
        </div>

        <table class="photo-list">
            <c:forEach var="dto" items="${list}">
            <tr class="tag-tr">
                <td></td>
                <td class="center-img">
                    <c:forEach var="tagDTO" items="${dto.tagList}">
                    <span>${tagDTO.tag}<a onclick="tagRemove('{tagDTO.tagNum}')">✖️</a></span>
                    </c:forEach>
                    <span id="addTag" onclick="tagActive('0')">➕</span>
                    <span id="inputTag" hidden>
                        <input type="text" name="tag" id="tag">
                        <a id="checkTag" onclick="addTag('${dto.photoNum}')">✔️</a>
                        <a onclick="tagActive('1')">✖️</a>
                    </span>
                </td>
                <td></td>
            </tr>
            <tr class="post-move">
                <td>
                    <span class="prev">◀️</span>
                </td>
                <td class="center-img">
                    <a onclick="photoarticle('1', '${photoarticleUrl}');">
                        <img src="<%=cp%>/uploads/photo/${dto.imageFilename}">

                    </a>
                </td>
                <td>
                    <span class="next">▶️</span>
                </td>
            </tr>
            <tr>
                <td></td>
                <td class="center-img">
                    <div class="photo-box2">
                    <img src="<%=cp%>/resource/img/user.png" alt="">
                    <span>${dto.place} | ${dto.subject}</span><br>
                    <span>${dto.userId} |  ${dto.created}</span>
                    <div class="content">${dto.content}</div>
                    </div>
                </td>
                <td></td>
            </tr>
            </c:forEach>
        </table>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>