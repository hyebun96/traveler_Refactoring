// main javascript

//엔터치면 다음으로 넘어가게함.
$(function () {
    $("input").not($(":button")).not($(":reset")).keypress(function (evt) { //input에서 keypress가 발생했으면(버튼과 리셋은 빼고)
        if (evt.keyCode === 13) {
            const fields = $(this).parents("form,body").find("button,input,select,textarea"); //form또는 body 안에 모든 button,input,select,textarea 찾아라
            const index = fields.index(this);

            if (index > -1 && (index + 1) < fields.length) {
                fields.eq(index + 1).focus();
            }
            return false; //엔터 이벤트 취소
        }
    });
});

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