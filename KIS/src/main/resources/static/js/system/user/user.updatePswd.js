$(function() {
	var load;
	$("#formId").ajaxForm({
		success : function(result) {
			layer.close(load);
			if (result && result.status != 200) {
				return layer.msg(result.message, function() {
				}), !1;
			} else {
				layer.msg('操作成功！');
				$("form :password").val('');
			}
		},
		beforeSubmit : function() {
			// 判断参数
			if ($.trim($("#pswd").val()) == '') {
				layer.msg('请输入原密码', function() {
				});
				$("#pswd").parent().removeClass('has-success').addClass('has-error');
				return !1;
			} else {
				$("#pswd").parent().removeClass('has-error').addClass('has-success');
			}
			if ($.trim($("#newPswd").val()) == '') {
				layer.msg('请输入新密码', function() {
				});
				$("#newPswd").parent().removeClass('has-success').addClass('has-error');
				return !1;
			} else {
				$("#newPswd").parent().removeClass('has-error').addClass('has-success');
			}
			if ($.trim($("#reNewPswd").val()) == '') {
				layer.msg('请再次输入新密码', function() {
				});
				$("#reNewPswd").parent().removeClass('has-success').addClass('has-error');
				return !1;
			} else {
				$("#reNewPswd").parent().removeClass('has-error').addClass('has-success');
			}
			if ($("#reNewPswd").val() != $("#newPswd").val()) {
				return layer.msg('2次新密码输入不一致。', function() {
				}), !1;
			}
			load = layer.load('正在提交！！！');
		},
		dataType : "json",
		clearForm : false
	});
});