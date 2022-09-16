// notice.js

function searchNoticeList() {
    const f = document.searchForm;
    f.submit();
}

function createNoticeForm(userId, page) {
    const query = "page=" + page;
    let url;

    if(userId === 'admin'){
        url = "/traveler_war_exploded//notice/write.do?" + query;
    } else {
       return;
    }
    location.href = url;
}

function createNotice(mode) {
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



function updateNotice(num, loginUserId, boardWriteId, page, query) {
    let url;

    if(loginUserId === boardWriteId){
        query = "num=" + num + "&page=" + page;
        url = "/traveler_war_exploded/notice/update.do?" + query;

        location.href = url;
    }
    if(loginUserId !== boardWriteId){
        query = "num=" + num + "&" + query;
        const returnPage = "&returnPage=" + "/notice/list.do";
        url = "/traveler_war_exploded/main/access.do?" + query + returnPage;
        location.href = url;
    }
}

function deleteNotice(num, loginUserId, boardWriteId, query) {
    let url;
    query = "num=" + num + "&" + query;

    if(loginUserId === 'admin' || loginUserId === boardWriteId){
        url = "/traveler_war_exploded/notice/delete.do?" + query;
        if (confirm("자료를 삭제 하시 겠습니까 ? ")){
            location.href = url;
        }
    }
    if(loginUserId !== 'admin' && loginUserId !== boardWriteId){
        const returnPage = "&returnPage=" + "/notice/list.do";
        url = "/traveler_war_exploded/main/access.do?" + query + returnPage;
        location.href = url;
    }
}

function deleteNoticeFile(num, fileNum, mode, page) {
    const query = "num=" + num + "&fileNum=" + fileNum + "&page=" + page;

    if(mode === 'update') {
        if (confirm("파일을 삭제 하시겠습니까?")) {
            const url = "/traveler_war_exploded/notice/deleteFile.do?" + query;
            location.href = url;
        }
    }
}
