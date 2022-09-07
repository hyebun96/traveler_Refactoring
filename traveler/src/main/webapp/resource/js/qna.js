function searchList() {
    const f = document.searchForm;
    f.submit();
}

function memberOnly() {
    const url = "/traveler_war_exploded/qna/access.do?";
    location.href = url;
}

function createQNA(mode) {
    const f = document.writeBoardForm;

    let str = f.subject.value;
    if (!str) {
        alert("제목을 입력하세요. ");
        f.subject.focus();
        return;
    }

    str = f.content.value;
    if (!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }

    f.action = "/traveler_war_exploded/qna/" + mode + "_ok.do";
    f.submit();
}

function updateQna(qnaNum, loginUserId, qnaWriteId, page, query) {

    if(loginUserId === qnaWriteId){
        query = "qnaNum=" + qnaNum + "&page=" + page + "&" + query;
        const url = "/traveler_war_exploded/qna/update.do?" + query;

        location.href = url;
    }
    if(loginUserId !== qnaWriteId){
        memberOnly();
    }

}

function deleteQna(qnaNum, loginUserId, qnaWriteId, page, query) {

    if(loginUserId === 'admin' || loginUserId === qnaWriteId){
        query = "qnaNum=" + qnaNum + "&page=" + page + "&" + query;
        const url = "/traveler_war_exploded/qna/delete.do?" + query;

        if (confirm("위 자료를 삭제 하시 겠습니까 ? ")) {
            location.href = url;
        }
    }

    if(loginUserId !== 'admin' && loginUserId !== qnaWriteId) {
        memberOnly()
    }
}