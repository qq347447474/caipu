package com.demo.user;

import com.demo.common.model.Blog;
import com.demo.common.model.User;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
//@Before(BlogInterceptor.class)
public class UserController extends Controller {
	public void index() {
		//setAttr("blogPage", Blog.me.paginate(getParaToInt(0, 1), 10));
		//render("blog.html");
		render("/signin.html");
	}
	
	@Before(UserValidator.class)
	public void add(){
		boolean issuccess = getModel(User.class).save();
		setAttr("msg", issuccess?"注册成功!":"注册失败");
		//redirect("/reg");
		render("/signup.html");
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


