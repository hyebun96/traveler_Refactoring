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
    <link rel="stylesheet" href="<%=cp%>/resource/css/write.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/travel.js"></script>
</head>
<body>
    <div class="header">
        <jsp:include page="/WEB-INF/views/layout/header.jsp"/>
    </div>
    <div class="navigation">
        <div class="nav-bar">HOME > TRAVEL</div>
    </div>

    <div class="write">
        <h3>Travel</h3>

        <br>
        <br>

        <form class="writeBoardForm" name="travelForm" method="post" enctype="multipart/form-data">
            <div class="travel-box">
                <p>&nbsp;작성자 </p>
                    ${dto.userId} (${dto.userName}) <img src="<%=cp%>/resource/img/user.png" alt="" >
                <br>
                <p>지&nbsp;&nbsp;&nbsp;역</p>
                <label>
                    <select name="type">
                        <option value="${dto.type}">::지역선택::</option>
                        <option value="Seoul" ${dto.type == "seoul" ? "selected='selected'" : ""}>서울</option>
                        <option value="Gangwon-do" ${dto.type == "gangwon-do" ? "selected='selected'" : ""}>강원</option>
                        <option value="Chungcheongbuk-do" ${dto.type == "chungcheongbuk-do" ? "selected='selected'" : ""}>충북</option>
                        <option value="Gwangju" ${dto.type == "gwangju" ? "selected='selected'" : ""}>광주</option>
                        <option value="Gyeongsangbuk-do" ${dto.type == "gyeongsangbuk-do" ? "selected='selected'" : ""}>경북</option>
                        <option value="Jeju" ${dto.type == "jeju" ? "selected='selected'" : ""}>제주</option>
                    </select>
                </label>
                <p>장&nbsp;&nbsp;&nbsp;소 </p>
                <input class="place" name="place" type="text" value="${dto.place}">
                <br>
                <p class="content">정&nbsp;&nbsp;&nbsp;보</p>
                <textarea name="information">${dto.information}</textarea>
                <p class="content">파일 첨부</p>
                <input multiple="multiple" type="file" name="upload">
                <br>
                <c:if test="${dto.saveFileName != null}">
                    <p>첨부된 파일</p>
                    <div class="imageList">
                    <c:forEach var="image" items="${imageList}">
                        ${image.key} &nbsp;&nbsp;|&nbsp;&nbsp; <a href="javascript:deleteFile('${mode}', '${image.value}','${dto.num}');">삭제</a><br>
                    </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="div-button">
                <button type="button" class="btn btn-hover" onclick="createTravel('${mode}', '${dto.num}');">${mode=='update'?'수정완료':'등록하기'}</button>
                <button type="reset" class="btn btn-hover">다시입력</button>
                <button type="button" class="btn btn-hover" onclick="location.href='<%=cp%>/travel/list.do?type=${type}';">${mode=='update'?'수정취소':'등록취소'}</button>
            </div>
        </form>
    </div>

    <div class="footer">
        <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
    </div>
</body>
</html>