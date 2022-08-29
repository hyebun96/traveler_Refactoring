// main javascript

$(function() {
    let time = 500;
    let idx = idx2 = 0;
    let slide_width = $("#bxslider").width();

    $("#bxslider li:first").css("display", "block");

    setInterval(clock, 1000);
});

function clock(){
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth();
    let day = date.getDate();
    let hour = date.getHours();
    let min = date.getMinutes();
    let sec = date.getSeconds();

    let nowDate =  year + "년 " + month + "월 " + day + "일 " + hour + "시 " + min + "분 " + sec + "초";

    console.log(nowDate);
    $("#nowTime").empty();
    $("#nowTime").append(nowDate);
}