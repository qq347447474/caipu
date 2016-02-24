$('#reg_btn').bind("click",reg);

function reg() {
	$("#reg_btn").attr("disabled", true);
	$.ajax({
		url : "user/add",
		async : false,
		cache : false,
		type : 'post',
		dataType : "json",
		data : {
			username : $("#username").val(),
			password : $("#password").val(),
			repassword : $("#repassword").val(),
			chkAccept : $("#chkAccept").is(':checked'),
		},
		success : function(data) {
			if (data.code == '200') {
				alert('注册成功！');
				location.href = "login";
			} else {
				$("#errorInfos").css('display', 'block');
				$("#errorInfo").html(data.description);
				$("#reg_btn").attr("disabled", false);
			}
		}
	});
}

$(function() {
	// 用户名失去焦点
	$("#username").blur(function() {
		if ($("#username").val().trim() == "") {
			$('#usernamediv').addClass('has-error');
		} else {
			$('#usernamediv').removeClass('has-error');
			$('#usernamediv').addClass('has-success');
		}
	});
	// 密码失去焦点
	$("#password").blur(function() {
		if ($("#password").val().trim() == "") {
			$('#passworddiv').addClass('has-error');
		} else {
			$('#passworddiv').removeClass('has-error');
			$('#passworddiv').addClass('has-success');
		}
	});
	// 验证密码失去焦点
	$("#repassword").blur(function() {
		if ($("#repassword").val().trim() != $("#password").val().trim()) {
			$('#repassworddiv').addClass('has-error');
		} else {
			$('#repassworddiv').removeClass('has-error');
			$('#repassworddiv').addClass('has-success');
		}
	});
});

function sendEmail() {
    $("#send_email_btn").attr("disabled", true);
    $.ajax({
        url: "user/sendValiCode",
        async: false,
        cache: false,
        type: 'post',
        dataType: "json",
        data: {
            type: 'reg',
            email: $("#username").val()
        },
        success: function (data) {
            if (data.code == '200') {
                $("#send_email_btn").html("发送成功");
                $("#reg_email").attr("disabled", true);
            } else {
                $("#regMsg").css("color", "red").html(data.description);
                $("#send_email_btn").attr("disabled", false);
            }
        }
    });
}
