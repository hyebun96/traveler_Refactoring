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
    <link rel="stylesheet" href="<%=cp%>/resource/css/signup.css" type="text/css">

    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(function () {  //엔터치면 다음으로 넘어가게함.
            $("input").not($(":button")).not($(":reset")).keypress(function (evt) { //input에서 keypress가 발생했으면(버튼과 리셋은 빼고)
                if (evt.keyCode == 13) {
                    var fields = $(this).parents("form,body").find("button,input,select,textarea"); //form또는 body 안에 모든 button,input,select,textarea 찾아라
                    var index = fields.index(this);

                    if (index > -1 && (index + 1) < fields.length) {
                        fields.eq(index + 1).focus();
                    }
                    return false; //엔터 이벤트 취소
                }
            });
        });
        $(function () {
            $(".userImg").click(function () {
                $(".userImg").css("background-color", "white");
            });
            $(".user1").click(function (){
                $("#upload").val('user1');
                $(".user1").css("background-color", "#e7f0fe");
            });
            $(".user2").click(function (){
                $("#upload").val('user2');
                $(".user2").css("background-color", "#e7f0fe");
            });
            $(".user3").click(function (){
                $("#upload").val('user3');
                $(".user3").css("background-color", "#e7f0fe");
            });
            $(".user4").click(function (){
                $("#upload").val('user4');
                $(".user4").css("background-color", "#e7f0fe");
            });
            $(".user5").click(function (){
                $("#upload").val('user5');
                $(".user5").css("background-color", "#e7f0fe");
            });
        });

    </script>

    <script type="text/javascript"> /* 자바스크립트는  웹브라우저에 의해 실행되어지는거,전세계적으로 많이 씀*/
    let f = document.memberForm;
    if(!f.upload.value){
        let imgName = f.upload.value;
        $("." + imgName).css("background-color", "#e7f0fe");
        console.log(imgName + '이거야'); // TODO
    }


    function memberOk() {
        var str;

        str = f.upload.value;
        if(!str){
            alert("계정의 이미지를 선택해 주세요.");
            f.upload.focus();
            return;
        }

        str = f.userId.value;
        str = str.trim();
        if (!str) {
            alert("아이디를 입력하세요. ");
            f.userId.focus();
            return;
        }
        if (!/^[a-z][a-z0-9_]{2,10}$/i.test(str)) {
            alert("아이디는 3~10자이며 첫글자는 영문자이어야 합니다.");
            f.userId.focus();
            return;
        }
        f.userId.value = str;

        str = f.userPwd.value;
        str = str.trim();
        if (!str) {
            alert("패스워드를 입력하세요. ");
            f.userPwd.focus();
            return;
        }
        if (!/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str)) {
            alert("패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.");
            f.userPwd.focus();
            return;
        }
        f.userPwd.value = str;

        if (str != f.userPwdCheck.value) {
            alert("패스워드가 일치하지 않습니다. ");
            f.userPwdCheck.focus();
            return;
        }

        str = f.userName.value;
        str = str.trim();
        if (!str) {
            alert("이름을 입력하세요. ");
            f.userName.focus();
            return;
        }
        f.userName.value = str;

        str = f.userBirth.value;
        str = str.trim();
        if (!str || !isValidDateFormat(str)) {
            alert("생년월일를 입력하세요[YYYY-MM-DD]. ");
            f.userBirth.focus();
            return;
        }

        str = f.tel1.value;
        str = str.trim();
        if (!str) {
            alert("전화번호를 입력하세요. ");
            f.tel1.focus();
            return;
        }

        str = f.tel2.value;
        str = str.trim();
        if (!str) {
            alert("전화번호를 입력하세요. ");
            f.tel2.focus();
            return;
        }
        if (!/^(\d+)$/.test(str)) {
            alert("숫자만 가능합니다. ");
            f.tel2.focus();
            return;
        }

        str = f.tel3.value;
        str = str.trim();
        if (!str) {
            alert("전화번호를 입력하세요. ");
            f.tel3.focus();
            return;
        }
        if (!/^(\d+)$/.test(str)) {
            alert("숫자만 가능합니다. ");
            f.tel3.focus();
            return;
        }

        str = f.email1.value;
        str = str.trim();
        if (!str) {
            alert("이메일을 입력하세요. ");
            f.email1.focus();
            return;
        }

        str = f.email2.value;
        str = str.trim();
        if (!str) {
            alert("이메일을 입력하세요. ");
            f.email2.focus();
            return;
        }

        var mode = "${mode}";
        if (mode == "created") {
            alert("회원가입하시겠습니까?");
            f.action = "<%=cp%>/member/member_ok.do";

        } else if (mode == "update") {
            alert("수정하시겠습니까?");
            f.action = "<%=cp%>/member/update_ok.do";
        }

        f.submit();
    }

    function changeEmail() {
        var f = document.memberForm;

        var str = f.selectEmail.value;
        if (str != "direct") {
            f.email2.value = str;
            f.email2.readOnly = true;
            f.email1.focus();
        } else {
            f.email2.value = "";
            f.email2.readOnly = false;
            f.email1.focus();
        }
    }

    //날짜입력검증
    function isValidDateFormat(data) {
        var p = /^[12][0-9]{3}[\.|\-|\/]?[0-9]{2}[\.|\-|\/]?[0-9]{2}$/; //0-9까지 3개
        if (!p.test(data)) {
            return false;
        }
        //g을 안붙여주면 첫번째 일치하는것만 없애줌.ex>2020-04-04에서 202004-04가 됨
        p = /(\.)|(\-)|(\/)/g;
        data = data.replace(p, "");

        var y = parseInt(data.substr(0, 4));
        var m = parseInt(data.substr(4, 2));
        if (m < 1 || m > 12) {
            return false;
        }

        var d = parseInt(data.substr(6));
        var lastDay = (new Date(y, m, 0)).getDate();
        if (d < 1 || d > lastDay) {
            f.birth.focus();
            return false;
        }
        return true;
    }


    </script>
</head>

<body>

<div class="header">
    <jsp:include page="/WEB-INF/views/layout/header.jsp"></jsp:include>
</div>
<div class="navigation">
    <div class="nav-bar">HOME > Signup</div>
</div>


<div class="main">
    <div class="signup">
        <h1>${title}</h1>
        <br>
    </div>

    <div class="index">

        <form name="memberForm" action="javascript:send();" method="post" enctype="multipart/form-data">
            <input id="upload" hidden type="text" name="upload" value="${dto.imageFilename}">
                <img class="userImg user1" id="user1" value="user1" src="<%=cp%>/resource/img/user1.png"/>
                <img class="userImg user2" id="user2" value="user2" src="<%=cp%>/resource/img/user2.png"/>
                <img class="userImg user3" id="user3" value="user3" src="<%=cp%>/resource/img/user3.png"/>
                <img class="userImg user4" id="user4" value="user4" src="<%=cp%>/resource/img/user4.png"/>
                <img class="userImg user5" id="user5" value="user5" src="<%=cp%>/resource/img/user5.png"/>
            </input>

            <input type="text" name="userId" value="${dto.userId}" class="imo" required="required" maxlength="15"
                   pattern="[a-zA-Z0-9]+" placeholder="UserID" ${mode=="update" ? "readonly='readonly' ":""}>
            <span data-placeholder="UserID"></span>

            <input type="password" name="userPwd" required="required" maxlength="15" pattern="[a-zA-Z0-9]+"
                   placeholder="Password 1">
            <span data-placeholder="Password"></span>

            <input type="password" name="userPwdCheck" required="required" maxlength="15" pattern="[a-zA-Z0-9]+"
                   placeholder="Password 2">
            <span data-placeholder="Password"></span>

            <input type="text" name="userName" value="${dto.userName}" required="required" maxlength="10"
                   pattern="[a-zA-Z0-9]+" placeholder="UserName" ${mode=="update" ? "readonly='readonly' ":""}>
            <span data-placeholder="UserName"></span>

            <span data-placeholder="tel"></span>

            <select class="selectField" id="tel1" name="tel1" style="float: left;">
                <option value="">선 택</option>
                <option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
                <option value="011" ${dto.tel1=="011" ? "selected='selected'" : ""}>011</option>
                <option value="016" ${dto.tel1=="016" ? "selected='selected'" : ""}>016</option>
                <option value="017" ${dto.tel1=="017" ? "selected='selected'" : ""}>017</option>
                <option value="018" ${dto.tel1=="018" ? "selected='selected'" : ""}>018</option>
                <option value="019" ${dto.tel1=="019" ? "selected='selected'" : ""}>019</option>
            </select>
            <p style="float: left; margin-top: 12px;">&nbsp;-&nbsp;</p>
            <input type="text" name="tel2" value="${dto.tel2}" required="required" maxlength="11" pattern="[0-9]+"
                   placeholder="tel" style="float:left; width: 142px;">
            <p style="float: left; margin-top: 12px;">&nbsp;-&nbsp;</p>
            <input type="text" name="tel3" value="${dto.tel3}" required="required" maxlength="11" pattern="[0-9]+"
                   placeholder="tel" style="float:left; width: 143px;">

            <select name="selectEmail" onchange="changeEmail();" class="selectField" style="float: left;">
                <option value="">선 택</option>
                <option value="naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버</option>
                <option value="hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한메일</option>
                <option value="hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫메일</option>
                <option value="gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지메일</option>
                <option value="direct">입력</option>
            </select>

            <input class="email" type="text" name="email1" value="${dto.email1}" required="required" size="13"
                   maxlength="30" pattern="[a-zA-Z0-9]+" placeholder="email" style="float:left; width: 140px;">
            <p style="float: left; margin-top: 12px;">@</p>
            <input class="email" type="text" name="email2" value="${dto.email2}" required="required" size="13"
                   maxlength="30" pattern="[a-zA-Z0-9]+" placeholder="email" readonly="readonly"
                   style="float:left; width: 150px;">
            <span data-placeholder="Email"></span>


            <input type="text" name="userBirth" value="${dto.userBirth}" required="required" maxlength="10"
                   pattern="[0-9]+" placeholder="Birth[ YYYY-MM-DD ]">
            <span data-placeholder="Birth"></span>

            <button class="indexBtn" type="button" name="sendButton" onclick="memberOk();"
                    style="margin-left: 10px;">${mode=="created"?"sign up":"수정완료"}</button>
            <button class="indexBtn" type="reset">다시입력</button>
            <c:if test="${mode=='created'}">
                <button class="indexBtn" type="button" onclick="javascript:location.href='<%=cp%>/';">가입취소</button>
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
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

</body>
</html>
