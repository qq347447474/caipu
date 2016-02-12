package com.demo.user;

import com.demo.common.model.Blog;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * BlogValidator.
 */
public class UserValidator extends Validator {
	
	//表单校验
	protected void validate(Controller controller) {
		String errorMsgFrist = "<div class='alert alert-danger alert-custom alert-dismissible' role='alert'>	<button type='button' class='close' data-dismiss='alert'><span aria-hidden='true'>&times;</span><span class='sr-only'>Close</span></button><i class='fa fa-times-circle m-right-xs'></i> <strong>错误!</strong>";
		String errorMsgLast = "</div>";
		validateRequiredString("username", "errorMsg", errorMsgFrist+"请输入用户名!"+errorMsgLast);
		validateRequiredString("password", "errorMsg", errorMsgFrist+"请输入密码!"+errorMsgLast);
		validateRequiredString("repassword", "errorMsg", errorMsgFrist+"请重复输入密码!"+errorMsgLast);
		String password = controller.getPara("password");
		String repassword = controller.getPara("repassword");
		if (!password.equals(repassword)) {
			addError("errorMsg", errorMsgFrist+"两次密码输入不一致!"+errorMsgLast);
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Blog.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/user/reg"))
			controller.render("signup.html");
		else if (actionKey.equals("/blog/update"))
			controller.render("edit.html");
	}
}
