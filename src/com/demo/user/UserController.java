package com.demo.user;

import com.demo.base.BaseController;
import com.demo.common.Constants;
import com.demo.common.model.Blog;
import com.demo.common.model.User;
import com.demo.utils.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
//@Before(BlogInterceptor.class)
public class UserController extends BaseController {
	public void index() {
		//setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 10));
		//render("blog.html");
		render("/signin.html");
	}
	
	//@Before(UserValidator.class)
	public void add(){
		String method = getRequest().getMethod();
		if (method.equalsIgnoreCase(Constants.GET)){
			
		}else if (method.equalsIgnoreCase(Constants.POST)) {
			String username = getPara("username");
            String password = getPara("password");
            String repassword = getPara("repassword");
            if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(repassword)) {
                error("请完善注册信息");
            }else{
            	if (!password.equals(repassword)) {
            		error("两次密码输入不一致!");
				}else{
					if (User.dao.findByUsername(username) != null){
						error("用户名已被注册，请直接登录");
					}else{
						User user = new User();
						user.set("username", username).set("password", password).save();
						setSessionAttr(Constants.USER_SESSION, user);
						success();
					}
				}
            }
		}
		/*boolean issuccess = getModel(User.class).save();
		setAttr("msg", issuccess?"注册成功!":"注册失败");*/
		//redirect("/reg");
		//render("/signup.html");
	}
	
	public void save() {
		getModel(Blog.class).save();
		redirect("/blog");
	}
	
	public void edit() {
		setAttr("blog", Blog.me.findById(getParaToInt()));
	}
	
	public void update() {
		getModel(Blog.class).update();
		redirect("/blog");
	}
	
	public void delete() {
		Blog.me.deleteById(getParaToInt());
		redirect("/blog");
	}
}


