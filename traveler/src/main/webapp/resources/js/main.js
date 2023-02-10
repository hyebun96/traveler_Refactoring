// main javascript

$(function() {
    let msg = $("#msg").val();
    if(msg !== '' && msg !== undefined){
        alert(msg);
    }

    setInterval(clock, 1000);
});

function clock(){  // 실시간 시계
    let date = new Date();
    let year = date.getFullYear();
    let month = date.getMonth();
    let day = date.getDate();
    let hour = date.getHours();
    let min = date.getMinutes();
    let sec = date.getSeconds();

    let nowDate =  year + "년 " + month + "월 " + day + "일 " + hour + "시 " + min + "분 " + sec + "초";

    $("#nowTime").empty();
    $("#nowTime").append(nowDate);
}

$.noConflict();

jQuery(document).ready(function($){
    $('.post-wrapper').slick({
        slidesToShow: 3,
        slidesToScroll: 1,
        autoplay: true,
        autoplaySpeed: 2000,
        nextArrow:$('.next'),
        prevArrow:$('.prev'),
    });
});

document.cookie = "safeCookie1=foo; SameSite=Lax";
document.cookie = "safeCookie2=foo";
document.cookie = "crossCookie=bar; SameSite=None; Secure";