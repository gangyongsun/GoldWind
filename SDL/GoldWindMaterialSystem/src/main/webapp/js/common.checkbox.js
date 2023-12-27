$(function () {
    $("#checkAll").click(function () {
        if (this.checked) {
            $("input:checkbox").prop("checked", true);
        } else {
            $("input:checkbox").prop("checked", false);
        }
    });
});

/**
 * 单选影响全选
 * 
 * @param ths
 * @returns
 */
function userCheck(ths) {
    if (ths.checked == false) {
        $("#checkAll").prop('checked', false);
    }else {
        var count = $("input[id='subcheckbox']:checkbox:checked").length;
        if (count == $("input[id='subcheckbox']:checkbox").length) {
            $("#checkAll").prop("checked", true);
        }
    }
}