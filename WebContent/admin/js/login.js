$(function() {
	$(document).keydown(function(event) {
		if (event.keyCode == 13) {
			$("#login").click();
		}
	});
	$('input:checkbox').iCheck({
		checkboxClass : 'icheckbox_square-blue',
		radioClass : 'iradio_square-blue',
		increaseArea : '20%' // optional
	});
	$("#login").click(function() {
		var loginName = $.trim($("input[name='loginName']").val());
		var password = $.trim($("input[name='password']").val());
		var yzm = $.trim($("input[name='yzm']").val());
		if (!loginName) {
			layer.msg("用户名不能为空", {
				offset : 'm',
				anim : 6
			});
			return false;
		}
		if (loginName.length < 2) {
			layer.msg("用户名过短", {
				offset : 'm',
				anim : 6
			});
			return false;
		}
		if (!password) {
			layer.msg("密码不能为空", {
				offset : 'm',
				anim : 6
			});
			return false;
		}
		if (password.length < 6) {
			layer.msg("密码不能少于6位", {
				offset : 'm',
				anim : 6
			});
			return false;
		}
		if (!yzm){
            layer.msg("验证码不能为空", {
                offset : 'm',
                anim : 6
            });
            return false;
		}
		if(yzm.length != 6){
            layer.msg("验证码长度不正确", {
                offset : 'm',
                anim : 6
            });
            return false;
		}
		$.post(adminContextPath + "/login.do", {
			loginName : loginName,
			password : password,
			yzm : yzm,
			rememberMe : $("input[name='rememberMe']").val()
		}, function(respone) {
			if (respone.responeCode == "0") {
				sessionStorage.clear();
				window.location.href = "/admin/pages/index.html";
			}
		});
	});

	//获取验证码
	$("#yzm").click(function () {
        var loginName = $.trim($("input[name='loginName']").val());
        var password = $.trim($("input[name='password']").val());
        if (!loginName) {
            layer.msg("用户名不能为空", {
                offset : 'm',
                anim : 6
            });
            return false;
        }
        if (loginName.length < 2) {
            layer.msg("用户名过短", {
                offset : 'm',
                anim : 6
            });
            return false;
        }
        if (!password) {
            layer.msg("密码不能为空", {
                offset : 'm',
                anim : 6
            });
            return false;
        }
        if (password.length < 6) {
            layer.msg("密码不能少于6位", {
                offset : 'm',
                anim : 6
            });
            return false;
        }
		$.post(adminContextPath + '/getVerificationCode.do',{type :'WT00004',loginName:loginName},function (respone) {
		    if(respone.responeCode == "0"){
                djsyzmbtnFn('yzm');
            }else {
                layer.msg(respone.responeMsg, {
                    offset : 'm',
                    anim : 6
                });
            }
        })
    });
    var wait = 60;
//倒计时验证码
    var djsyzmbtnFn = function (btnId){
        if (wait == 0) {
            $("#"+btnId).removeAttr("disabled");
            $("."+btnId).html("&nbsp;&nbsp;");
            wait = 60;
        } else {
            $("#"+btnId).attr("disabled","disabled");
            $("."+btnId).html(wait+"秒后可以重新发送!");
            wait--;
            setTimeout(function() {
                    djsyzmbtnFn(btnId) ;
                },
                1000) ;
        }
    };

	//个人中心
	//$.post(adminContextPath + "/user/getUserInfo.do",function(respone){
	//	if (respone.responeCode == '0') {
	//		window.location.href = "/admin/pages/index.html";
	//	}
    //});
});