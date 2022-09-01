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
    <link rel="stylesheet" href="<%=cp%>/resource/css/member_signup.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="<%=cp%>/resource/js/member.js"></script>

</head>

<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp" />
</div>
<div class="navigation">
    <div class="nav-bar">HOME > Signup</div>
</div>


<div class="main">
    <div class="signup">
        <h1>${title}</h1>
        <br>
    </div>

    <input id="msg" value="${msg}" hidden/>

    <div class="index">
        <form name="memberForm" action="javascript:send();" method="post" enctype="multipart/form-data">
            <p>
                <c:if test="${dto.userId != 'admin'}">
                <input hidden id="upload" name="upload" type="text"  value="${dto.imageFilename}">
                    <img class="userImg user1" id="user1" value="user1" src="<%=cp%>/resource/img/user1.png"/>
                    <img class="userImg user2" id="user2" value="user2" src="<%=cp%>/resource/img/user2.png"/>
                    <img class="userImg user3" id="user3" value="user3" src="<%=cp%>/resource/img/user3.png"/>
                    <img class="userImg user4" id="user4" value="user4" src="<%=cp%>/resource/img/user4.png"/>
                    <img class="userImg user5" id="user5" value="user5" src="<%=cp%>/resource/img/user5.png"/>
                </input>
                </c:if>
            </p>

            <p>
                <span data-placeholder="UserID" class="span-id">ID</span>
                <input type="text" name="userId" value="${dto.userId}" class="imo" required="required" maxlength="15"
                   pattern="[a-zA-Z0-9]+" placeholder="" ${mode=="update" ? "readonly='readonly' ":""}>
            </p>

            <p ${mode=="update" ? "hidden" : ""}>
                <span data-placeholder="Password" class="span-id">PW</span>
                <input type="password" name="userPwd" required="required" maxlength="15" pattern="[a-zA-Z0-9]+"
                       placeholder="">
            </p>

            <p ${mode=="update" ? "hidden" : ""}>
                <span data-placeholder="Password2" class="span-id">PW&nbsp;✅</span>
                <input type="password" name="userPwdCheck" required="required" maxlength="15" pattern="[a-zA-Z0-9]+"
                       placeholder="">
            </p>

            <p>
                <span data-placeholder="UserName" class="span-id">이름</span>
                <input type="text" name="userName" value="${dto.userName}" required="required" maxlength="10"
                       pattern="[a-zA-Z0-9]+" placeholder="" ${mode=="update" ? "readonly='readonly' ":""}>
            </p>


            <p>
                <span data-placeholder="Birth" class="span-id">생년월일</span>
                <input type="text" name="userBirth" value="${dto.userBirth}" required="required" maxlength="10"
                       pattern="[0-9]+" placeholder="">
            </p>

            <p>
                <span data-placeholder="tel" class="span-id">전화</span>
                <select class="selectField tel" id="tel1" name="tel1">
                    <option value="">선 택</option>
                    <option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
                    <option value="011" ${dto.tel1=="011" ? "selected='selected'" : ""}>011</option>
                    <option value="016" ${dto.tel1=="016" ? "selected='selected'" : ""}>016</option>
                    <option value="017" ${dto.tel1=="017" ? "selected='selected'" : ""}>017</option>
                    <option value="018" ${dto.tel1=="018" ? "selected='selected'" : ""}>018</option>
                    <option value="019" ${dto.tel1=="019" ? "selected='selected'" : ""}>019</option>
                </select>
                <span class="special-ch">-</span>
                    <input type="text" name="tel2" value="${dto.tel2}" required="required" maxlength="11" pattern="[0-9]+" placeholder="" class="tel">
                <span class="special-ch">-</span>
                    <input type="text" name="tel3" value="${dto.tel3}" required="required" maxlength="11" pattern="[0-9]+" placeholder="" class="tel">
            </p>

            <p>
                <span data-placeholder="Email" class="span-id">메일</span>
                <input class="email" type="text" name="email1" value="${dto.email1}" required="required" size="13"
                       maxlength="30" pattern="[a-zA-Z0-9]+" placeholder="">
                <span class="special-ch">@</span>
                <input class="email" type="text" name="email2" value="${dto.email2}" required="required" size="13"
                       maxlength="30" pattern="[a-zA-Z0-9]+" placeholder="" readonly="readonly">
                <select name="selectEmail" onchange="changeEmail();" class="selectField email">
                    <option value="">선 택</option>
                    <option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버</option>
                    <option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한메일</option>
                    <option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫메일</option>
                    <option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지메일</option>
                    <option value="direct">입력</option>
                </select>
            </p>

            <button class="indexBtn indexBtn2" type="button" name="sendButton" onclick="memberOk('${mode}');">
                ${mode=="created"?"sign up":"수정완료"}
            </button>
            <button class="indexBtn" type="reset">
                다시입력
            </button>
            <c:if test="${mode=='created'}">
                <button class="indexBtn" type="button" onclick="javascript:location.href='<%=cp%>/';">
                    가입취소
                </button>
            </c:if>
            <c:if test="${mode!='created'}">
                <input type="hidden" name="imageFilename" value="${dto.imageFilename}">
                <button class="indexBtn" type="button" onclick="javascript:location.href='<%=cp%>/member/myPage.do/';">
                    수정취소
                </button>
            </c:if>

        </form>
    </div>
</div>

<div class="footer">
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>

</body>
</html>
