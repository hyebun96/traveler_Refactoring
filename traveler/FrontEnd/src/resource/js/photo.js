// photo.js

$(function () {
    const photoNum = $("#photoNum").val();
    if(photoNum){
        $("#photo-" + photoNum).css("border", "2px solid #c4bfcb");
    }
});

function tagActive(state){
    if(state === '0'){
        $("#addTag").hide();
        $("#inputTag").show();
    } else {
        $("#addTag").show();
        $("#inputTag").hide();
    }
}

function addTag(photoNum){
    const tag = $("#tag").val();
    const query = "photoNum=" + photoNum + "&tag=" + tag;
    const returnPage = "&returnPage=" + "photo/list.do";
    let url =  "/traveler_war_exploded/photo/createdTag.do?" + query + returnPage;

    if(!photoNum){
        alert("오류.");
        return;
    }

    if(!tag){
        alert("태그를 입력하세요. ");
        $("#tag").focus();
        return;
    }
    location.href = url;
}

function tagRemove(tagNum, photoNum){
    const query =  "tagNum=" + tagNum + "&photoNum=" + photoNum;
    const returnPage = "&returnPage=" + "photo/list.do";
    let url =  "/traveler_war_exploded/photo/removeTag.do?" + query + returnPage;

    if(confirm("태그를 삭제하시겠습니까?")){
        location.href = url;
    }
}

function ListPhoto(photoNum){
    const url ="/traveler_war_exploded/photo/list.do?photoNum=" + photoNum;

    if(photoNum === null){
        return;
    }

    location.href = url;
}

function sendPhoto(mode) {
    const f = document.photoForm;

    let str = f.place.value;
    if (!str) {
        alert("장소를 입력하세요. ");
        f.place.focus();
        return;
    }

    str = f.subject.value;
    if (!str) {
        alert("제목을 입력하세요. ");
        f.subject.focus();
        return;
    }

    str = f.content.value;
    if (!str) {
        alert("정보를 입력하세요. ");
        f.content.focus();
        return;
    }

    str = f.upload.value;
    if (!str && !f.imageFilename.value) {
        alert("이미지 파일을 첨부하세요. (1장만 가능)");
        f.upload.focus();
        return;
    }

    if (str && !/(\.gif|\.jpg|\.png|\.jpeg)$/i.test(str)) {
        alert('이미지 파일만 가능합니다. !!!');
        f.upload.focus();
        return;
    }

    if (mode === "created") {
        f.action = "/traveler_war_exploded/photo/created_ok.do";
    } else if (mode === "updated") {
        const photoNum = f.photoNum.value;
        f.action = "/traveler_war_exploded/photo/updated_ok.do?photoNum=" + photoNum;
    }

    f.submit();
}

function updatePhoto(photoNum){
    const query =  "photoNum=" + photoNum;
    let url =  "/traveler_war_exploded/photo/updated.do?" + query;

    if(confirm("포토를 수정하시겠습니까?")){
        location.href = url;
    }
}

function deletePhoto(photoNum){
    const query =  "photoNum=" + photoNum;
    let url =  "/traveler_war_exploded/photo/delete.do?" + query;

    if(confirm("포토를 삭제하시겠습니까?")){
        location.href = url;
    }
}

function deleteFile(mode, photoNum) {
    const query =  "mode=" + mode + "&photoNum=" + photoNum;
    const url = "/traveler_war_exploded/photo/deleteFile.do?" + query;

    if(mode === "updated"){
        if (confirm("파일을 삭제 하시겠습니까?")) {
            location.href = url;
        }
    }
}