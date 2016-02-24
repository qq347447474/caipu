$('#login_btn').bind("click",login);

function login() {
	$("#login_btn").attr("disabled", true);
	$.ajax({
		url : "user/login",
		async : false,
		cache : false,
		type : 'post',
		dataType : "json",
		data : {
			username : $("#username").val(),
			password : $("#password").val(),
			remember : $("#chkRemember").is(':checked'),
		},
		success : function(data) {
			if (data.code == '200') {
				alert('登录成功！');
				location.href = "login";
			} else {
				$("#errorInfos").css('display', 'block');
				$("#errorInfo").html(data.description);
				$("#login_btn").attr("disabled", false);
			}
		}
	});
}