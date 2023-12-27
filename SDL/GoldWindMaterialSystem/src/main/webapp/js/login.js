jQuery(document).ready(
	function() {
		try {
			var _href = window.location.href + "";
			if (_href && _href.indexOf('?kickout') != -1) {
				layer.msg('您已经被踢出，请重新登录！');
			}
		} catch (e) {

		}
		// 回车事件绑定
		document.onkeydown = function(event) {
			var e = event || window.event || arguments.callee.caller.arguments[0];
			if (e && e.keyCode == 13) {
				$('#login').click();
			}
		};

		// 登录操作
		$('#login').click(
			function() {
				var username = $('.username').val();
				var password = $('.password').val();
				if (username == '') {
					$('.error').fadeOut('fast', function() {
						$('.error').css('top', '27px').show();
					});
					$('.error').fadeIn('fast', function() {
						$('.username').focus();
					});
					return false;
				}
				if (password == '') {
					$('.error').fadeOut('fast', function() {
						$('.error').css('top', '96px').show();
					});
					$(this).find('.error').fadeIn('fast', function() {
						$('.password').focus();
					});
					return false;
				}
				var pswd = MD5(username + "#" + password), data = {
					pswd : pswd,
					email : username,
					rememberMe : $("#rememberMe").is(':checked')
				};
				var load = layer.load();

				$.ajax({
					url : "submitLogin.shtml",
					data : data,
					type : "post",
					dataType : "json",
					beforeSend : function() {
						layer.msg('开始登录，请注意后台控制台!');
					},
					success : function(result) {
						layer.close(load);
						if (result && result.status != 200) {
							layer.msg(result.message, function() {});
							$('.password').val('');
							return;
						} else {
							layer.msg('登录成功！');
							setTimeout(function() {
								// 登录返回
								window.location.href = result.back_url;
							}, 1000)
						}
					},
					error : function(e) {
						console.log(e, e.message);
						layer.msg('请看后台Java控制台，是否报错，确定已经配置数据库和Redis', new Function());
					}
				});
			});
		$('.page-container form .username, .page-container form .password').keyup(function() {
			$(this).parent().find('.error').fadeOut('fast');
		});
		
		// 注册跳转
		$("#register").click(function() {
			window.location.href = "register.shtml";
		});
	});