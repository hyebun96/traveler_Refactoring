//엔터시 다음 input 창으로 넘어가는 기능
$(function () {
    $("input").not($(":button")).keypress(function (evt) {
        if (evt.keyCode === 13) {
            const fields = $(this).parents('form,body').find('button,input,textarea,select');
            let index = fields.index(this);
            if (index > -1 && (index + 1) < fields.length) {
                fields.eq(index + 1).focus();
            }
            return false;
        }
    });
});