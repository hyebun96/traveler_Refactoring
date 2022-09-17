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
    <script src="https://kit.fontawesome.com/667371032c.js" crossorigin="anonymous"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > Photo</div>
    </div>

    <div class="photo">
        <div  class="title">
            <p>Photo</p>
            <c:if test="${sessionScope.member.userId != null}">
                <button class="createBtn btn-hover" type="button" onclick="location.href='<%=cp%>/photo/created.do'">글등록</button>
            </c:if>
        </div>

        <div class="post-move">
            <div class="photo-menu">
                <input hidden id="photoNum" value="${dto.photoNum}">
                <c:forEach var="allDTO" items="${allList}">
                    <img id="photo-${allDTO.photoNum}" onclick="ListPhoto('${allDTO.photoNum}')" alt="" src="<%=cp%>/uploads/photo/${allDTO.imageFilename}">
                </c:forEach>
            </div>
        </div>

        <table class="photo-list">
            <tr class="post-move">
                <td class="move">
                    <span class="prev">
                        <c:if test="${prevPhotoDTO != null}">
                            <a href="<%=cp%>/photo/list.do?photoNum=${prevPhotoDTO.photoNum}">◀️</a>
                        </c:if>
                    </span>
                </td>
                <td class="center-img">
                    <img id="photo-img" src="<%=cp%>/uploads/photo/${dto.imageFilename}">
                </td>
                <td class="move">
                    <span class="next">
                        <c:if test="${nextPhotoDTO != null}">
                        <a href="<%=cp%>/photo/list.do?photoNum=${nextPhotoDTO.photoNum}">▶️</a>
                        </c:if>
                    </span>
                </td>
            </tr>
            <tr class="tag-tr">
                <td></td>
                <td class="center-img">
                    <span id="addTag"><a onclick="tagActive('0')">Tag <i class="fa-solid fa-plus"></i></a></span>
                    <span id="inputTag" hidden>
                        <input type="text" name="tag" id="tag">
                        <a id="checkTag" onclick="addTag('${dto.photoNum}')"><i class="fa-solid fa-circle-check"></i></a>
                        <a onclick="tagActive('1')">✖️</a>
                    </span>
                    <marquee class="tag-list">
                    <c:forEach var="tagDTO" items="${dto.tagList}">
                        <span>${tagDTO.tag}<a onclick="tagRemove('${tagDTO.tagNum}', '${dto.photoNum}')">✖️</a></span>
                    </c:forEach>
                    </marquee>
                </td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td class="center-img">
                    <div class="photo-box2">
                        <img src="<%=cp%>/resource/img/user.png" alt="">
                        <span class="update-btn">
                            <c:if test="${sessionScope.member.userId == dto.userId}">
                                <button class="btn-hover" type="button" onclick="updatePhoto('${dto.photoNum}')">글수정</button>
                                <button class="btn-hover" type="button" onclick="deletePhoto('${dto.photoNum}')">글삭제</button>
                            </c:if>
                        </span>
                        <span>${dto.place} | ${dto.subject}</span><br>
                        <span>${dto.userId} |  ${dto.created}</span>
                        <div class="content">${dto.content}</div>
                    </div>
                </td>
                <td></td>
            </tr>
        </table>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>