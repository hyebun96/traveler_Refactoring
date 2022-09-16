// board.js

function searchList() {
    const f = document.searchForm;
    f.submit();
}

function createBoard(mode) {
    const f = document.writeBoardForm;

    let str = f.title.value;
    if (!str) {
        alert("제목을 입력하세요. ");
        f.title.focus();
        return;
    }

    str = f.content.value;
    if (!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }

    f.action = "/traveler_war_exploded/board/" + mode + "_ok.do";
    f.submit();
}

function updateBoard(num, loginUserId, boardWriteId, page, query) {
    let url;
    query = "num=" + num + "&page=" + page + "&" + query;
    const returnPage = "&returnPage=" + "/board/view.do";

    if(loginUserId === boardWriteId){
        url = "/traveler_war_exploded/board/update.do?" + query;
        location.href = url;
    }
    if(loginUserId !== boardWriteId){
        url = "/traveler_war_exploded/main/access.do?" + query + returnPage;
        location.href = url;
    }

}

function deleteBoard(num, loginUserId, boardWriteId, query) {
    let url;
    query = "num=" + num + "&" + query;
    const returnPage = "&returnPage=" + "/board/view.do";
    
    if(loginUserId === 'admin' || loginUserId === boardWriteId){
        url = "/traveler_war_exploded/board/delete.do?" + query;
        if (confirm("자료를 삭제 하시 겠습니까 ? "))
            location.href = url;
    }

    if(loginUserId !== 'admin' && loginUserId !== boardWriteId){
        url = "/traveler_war_exploded/main/access.do?" + query + returnPage;
        location.href = url;
    }
}