// notice.js

function searchList() {
    let f = document.searchForm;
    f.submit();
}

function writeNotice(userId, page) {
    const query = "page=" + page;
    let url;

    if(userId === 'admin'){
        url = "/traveler_war_exploded//notice/write.do?" + query;
    } else {
       return;
    }
    location.href = url;
}

function sendOk(mode) {
    const f = document.writeBoardForm;
    let str;

    str = f.title.value;
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

    f.action = "/traveler_war_exploded/notice/" + mode + "_ok.do";
    f.submit();
}

function deleteFile(fileNum, mode) {

    if(mode === 'update'){
        const url = "/traveler_war_exploded/notice/deleteFile.do?num=${dto.num}&page=${page}&fileNum=" + fileNum;
        location.href = url;
    }

}

function deleteBoard(num, loginUserId, boardWriteId, query) {

    let url;

    if(loginUserId === 'admin' || loginUserId === boardWriteId){
        query = "num=" + num + "&" + query;
        url = "/traveler_war_exploded/notice/delete.do?" + query;

        if (confirm("자료를 삭제 하시 겠습니까 ? ")){
            location.href = url;
        }
    }

    if(loginUserId === 'admin' && loginUserId !== boardWriteId){
        query = "num=" + num + "&" + query;
        url = "/traveler_war_exploded/notice/access.do?" + query;
        location.href = url;
    }

}

function updateBoard(num, loginUserId, boardWriteId, page, query) {

    let url;

    if(loginUserId === boardWriteId){
        query = "num=" + num + "&page=" + page;
        url = "/traveler_war_exploded/notice/update.do?" + query;

        location.href = url;
    }
    if(loginUserId !== boardWriteId){
        query = "num=" + num + "&" + query;
        url = "/traveler_war_exploded/notice/access.do?" + query;
        location.href = url;
    }

}

