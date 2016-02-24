package com.demo.user;

import java.util.Date;

import com.demo.base.BaseController;
import com.demo.blog.Blog;
import com.demo.common.Constants;
import com.demo.utils.DateUtil;
import com.demo.utils.EmailSender;
import com.demo.utils.StrUtil;
import com.demo.valicode.ValiCode;

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
            boolean chkAccept = getParaToBoolean("chkAccept");
            String token = StrUtil.getUUID();
            if (StrUtil.isBlank(username) || StrUtil.isBlank(password) || StrUtil.isBlank(repassword)) {
                error("请完善注册信息");
            }else if (!chkAccept){
            	error("协议必须同意");
            }else{
            	if(!StrUtil.isEmail(username)){
            		error("请输入正确的邮箱地址");
            	}else if (!password.equals(repassword)) {
            		error("两次密码输入不一致!");
				}else{
					if (User.dao.findByUsername(username) != null){
						error("用户名已被注册，请直接登录");
					}else{
						User user = new User();
						user.set("username", username).set("password", password).set("token", token).save();
						//setSessionAttr(Constants.USER_SESSION, user);
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
	
	public void login(){
		String method = getRequest().getMethod();
		if (method.equalsIgnoreCase(Constants.GET)){
			
		}else if (method.equalsIgnoreCase(Constants.POST)){
			String username = getPara("username");
            String password = getPara("password");
            boolean remember = getParaToBoolean("remember");
            if (StrUtil.isBlank(username)) {
                error("请输入用户名");
            }else if(StrUtil.isBlank(password)){
            	error("请输入密码");
            }else{
            	User user = User.dao.findByUser(username, password);
				if (user == null){
					error("用户名不存在或密码错误");
				}else{
					setSessionAttr(Constants.USER_SESSION, user);
					if (remember) {
	                    setCookie(Constants.USER_COOKIE, StrUtil.getEncryptionToken(user.getStr("token")), 30 * 24 * 60 * 60);
					}
					success();
				}
            }
		}
	}
	
	public void sendValiCode() {
        String email = getPara("email");
        if (StrUtil.isBlank(email)) {
            error("邮箱不能为空");
        } else if (!StrUtil.isEmail(email)) {
            error("邮箱格式不正确");
        } else {
            String type = getPara("type");
            String valicode = StrUtil.randomString(6);
            if (type.equalsIgnoreCase(Constants.FORGET_PWD)) {
                User user = User.dao.findByUsername(email);
                if (user == null) {
                    error("该邮箱未被注册，请先注册");
                } else {
                    ValiCode code = new ValiCode();
                    code.set("code", valicode)
                            .set("type", type)
                            .set("in_time", new Date())
                            .set("status", 0)
                            .set("expire_time", DateUtil.getMinuteAfter(new Date(), 30))
                            .set("target", email)
                            .save();
                    StringBuffer mailBody = new StringBuffer();
                    mailBody.append("You retrieve the password verification code is: ")
                            .append(valicode)
                            .append("<br/>The code can only be used once, and only valid for 30 minutes.");
                    EmailSender.sendMail("JFinalbbs－Forgot password codes",
                            new String[]{email}, mailBody.toString());
                    success();
                }
            } else if (type.equalsIgnoreCase(Constants.REG)) {
                User user = User.me.findByEmail(email);
                if (user != null) {
                    error("邮箱已经注册，请直接登录");
                } else {
                    ValiCode code = new ValiCode();
                    code.set("code", valicode)
                            .set("type", type)
                            .set("in_time", new Date())
                            .set("status", 0)
                            .set("expire_time", DateUtil.getMinuteAfter(new Date(), 30))
                            .set("target", email)
                            .save();
                    StringBuffer mailBody = new StringBuffer();
                    mailBody.append("Register your account verification code is: ")
                            .append(valicode)
                            .append("<br/>The code can only be used once, and only valid for 30 minutes.");
                    EmailSender.sendMail("JFinalbbs－Registered Account codes", new String[]{email}, mailBody.toString());
                    success();
                }
            }
        }
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


