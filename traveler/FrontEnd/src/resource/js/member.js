// member.js

$(function () {
    let msg = $("#msg").val();
    if(msg !== '' && msg !== undefined){
        alert(msg);
    }

    let userImg = $("#upload").val();
    if(userImg !== '' && userImg !== undefined){
        $("." + userImg).css("background-color", "#e7f0fe");
    }

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

function searchList() {
    const f = document.searchForm;
    f.submit();
}

function sendLogin() {
    const f = document.loginForm;

    let str = f.userId.value;
    if (!str) {
        alert("아이디를 입력하세요. ");
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if (!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "/traveler_war_exploded/member/login_ok.do";
    f.submit();
}

function memberOk(mode) {
    const f = document.memberForm;
    let str;

    str = f.upload.value;
    if(!str){
        alert("계정의 이미지를 선택해 주세요.");
        $('#user1').click();
        return;
    }

    str = f.userId.value.trim();
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

    if(mode !== "update"){
        str = f.userPwd.value.trim();
        strCheck = f.userPwdCheck.value.trim();

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
        if (str !== strCheck) {
            alert("패스워드가 일치하지 않습니다. ");
            f.userPwdCheck.focus();
            return;
        }
    }

    str = f.userName.value.trim();
    if (!str) {
        alert("이름을 입력하세요. ");
        f.userName.focus();
        return;
    }

    str = f.userBirth.value.trim();
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
        alert("전화번호는 숫자만 가능합니다. ");
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
        alert("전화번호는 숫자만 가능합니다. ");
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

    if (mode === "created") {
        if (confirm("회원가입하시겠습니까?")) {
            f.action = "/traveler_war_exploded/member/member_ok.do";
        }

    } else if (mode === "update") {
        if (confirm("수정하시겠습니까?")) {
            f.action = "/traveler_war_exploded/member/update_ok.do";
        }
    }
    f.submit();

}

function changeEmail() {
    const f = document.memberForm;
    let str = f.selectEmail.value;

    if (str !== "direct") {
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
    let p = /^[12][0-9]{3}[\.|\-|\/]?[0-9]{2}[\.|\-|\/]?[0-9]{2}$/; //0-9까지 3개
    if (!p.test(data)) {
        return false;
    }
    //g을 안붙여주면 첫번째 일치하는것만 없애줌.ex>2020-04-04에서 202004-04가 됨
    p = /(\.)|(\-)|(\/)/g;
    data = data.replace(p, "");

    const y = parseInt(data.substr(0,4));
    const m = parseInt(data.substr(4, 2));
    if (m < 1 || m > 12) {
        return false;
    }

    const d = parseInt(data.substr(6));
    const lastDay = (new Date(y, m, 0)).getDate();
    if (d < 1 || d > lastDay) {
        f.birth.focus();
        return false;
    }
    return true;
}

function memberUpdate(mode) {
    const f = document.memberForm;

    if (mode === "delete") {
        if (confirm("탈퇴하시겠습니까?")) {
            f.action = "/traveler_war_exploded/member/delete.do";
        }

    } else if (mode === "update") {
        f.action = "/traveler_war_exploded/member/pwd.do?mode=update";
    }
    f.submit();
}

function pwdOk() {
    const f = document.pwdForm

    const str = f.userPwd.value;
    if (!str) {
        alert("패스워드를 입력하세요. ");
        f.userPwd.focus();
        return;
    }

    f.action = "/traveler_war_exploded/member/pwd_ok.do";
    f.submit();
}