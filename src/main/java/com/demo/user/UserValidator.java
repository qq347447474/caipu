package com.demo.user;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 * UserValidator.
 */
public class UserValidator extends Validator {
	
	//表单校验
	protected void validate(Controller controller) {
		validateRequiredString("user.username", "errorMsg", "请输入用户名!");
		if (controller.getAttr("errorMsg")==null) {
			validateRequiredString("user.password", "errorMsg", "请输入密码!");
			if (controller.getAttr("errorMsg")==null){
				validateRequiredString("repassword", "errorMsg", "请重复输入密码!");
				if (controller.getAttr("errorMsg")==null){
					String password = controller.getPara("user.password");
					String repassword = controller.getPara("repassword");
					if (!password.equals(repassword)) {
						addError("errorMsg", "两次密码输入不一致!");
					}
				}
			}
		}
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(User.class);
		controller.setAttr("isError", "true");
		String actionKey = getActionKey();
		if (actionKey.equals("/user/add"))
			controller.render("/signup.html");
		/*else if (actionKey.equals("/blog/update"))
			controller.render("edit.html");*/
	}
}
