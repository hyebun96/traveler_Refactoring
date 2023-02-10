// contact.js

$(function () {
    const ctSort = $("#ctSort").val();
    if(ctSort){
        $("#menu-" + ctSort).addClass("active");
    }
});

function sendOk() {
    const f = document.contactForm;

    let str = f.ctName.value;
    if (!str) {
        alert("이름을 입력하세요. ");
        f.ctName.focus();
        return;
    }

    str = f.ctSort.value;
    if (!str) {
        alert("분류를 선택하세요. ");
        f.ctSort.focus();
        return;
    }

    str = f.ctEmail.value;
    if (!str) {
        alert("이메일을 입력하세요.");
        f.ctEmail.focus();
        return;
    }

    if (!/^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(str)) {
        alert("이메일 형식이 맞지 않습니다. ");
        f.ctEmail.focus();
        return;
    }

    str = f.ctTel.value;
    if (!str) {
        alert("전화번호를 입력하세요.");
        f.ctTel.focus();
        return;
    }

    if (!/^(\d+)$/.test(str)) {
        alert("전화번호는 숫자만 가능합니다. ");
        f.ctTel.focus();
        return;
    }

    str = f.ctSubject.value;
    if (!str) {
        alert("제목을 입력하세요. ");
        f.ctSubject.focus();
        return;
    }

    str = f.ctContent.value;
    if (!str) {
        alert("내용을 입력하세요. ");
        f.ctContent.focus();
        return;
    }

    if (confirm("자료를 전송하시겠습니까?")) {
        f.action = "/traveler_war_exploded/contact/contact_ok.do";
    }

    f.submit();
}

function searchList() {
    const f = document.searchForm;
    f.action = "/traveler_war_exploded/contact/list.do"
    f.submit();
}

function searchList2(ctSort) {
    const f = document.searchForm;
    f.ctSort.value = ctSort;
    f.action = "/traveler_war_exploded/contact/list.do"
    f.submit();
}

function deleteContact(ctNum, loginId, query) {

    if(loginId !== 'admin'){
        return;
    }

    query = "ctNum=" + ctNum + "&" + query;
    const url = "/traveler_war_exploded/contact/delete.do?" + query;

    if (confirm("자료를 삭제 하시 겠습니까 ? ")) {
        location.href = url;
    }
}

function doneORUndoneContact(ctNum, loginId, fin, query) {

    if(loginId !== 'admin'){
        return;
    }

    query = "ctNum=" + ctNum +  "&fin=" + fin + "&" + query;
    const url = "/traveler_war_exploded/contact/doneORUndone.do?" + query;

    if ( (fin === 1 ? confirm("미완료 처리 되돌리시겠습니까 ? ") : confirm("완료처리 하시겠습니까 ? "))){
        location.href = url;
    }
}

function mailContact(query){
    const f = document.mailForm;

    str = f.receiverEmail.value;
    if (!str) {
        alert("받는 사람 이메일을 입력해주세요. ");
        f.receiverEmail.focus();
        return;
    }
    if (!/^[a-zA-Z0-9+-\_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(str)) {
        alert("이메일 형식이 맞지 않습니다. ");
        f.receiverEmail.focus();
        return;
    }

    str = f.subject.value;
    if (!str) {
        alert("제목을 입력해주세요. ");
        f.subject.focus();
        return;
    }

    str = f.contant.value;
    if (!str) {
        alert("내용을 입력해주세요. ");
        f.contant.focus();
        return;
    }

    if (confirm("회신 메일을 보내겠습니까? ")){
        f.action = "/traveler_war_exploded/contact/mail.do";
    }

    f.submit();
}



