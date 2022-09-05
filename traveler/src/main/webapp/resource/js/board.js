// board.js

function searchList() {
    var f = document.searchForm;
    f.submit();
}

function sendOk(mode) {
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

    if(loginUserId === boardWriteId){
        url = "/traveler_war_exploded/board/update.do?" + query;

        location.href = url;
    }
    if(loginUserId !== boardWriteId){
        url = "/traveler_war_exploded/board/access.do?" + query;
        location.href = url;
    }

}

function deleteBoard(num, loginUserId, boardWriteId, query) {
    let url;
    query = "num=" + num + "&" + query;
    
    if(loginUserId === 'admin' || loginUserId === boardWriteId){
        url = "/traveler_war_exploded/board/delete.do?" + query;

        if (confirm("자료를 삭제 하시 겠습니까 ? "))
            location.href = url;
    } else if(loginUserId === 'admin' && loginUserId !== boardWriteId){
        url = "/traveler_war_exploded/board/access.do?" + query;
        location.href = url;
    }
}