package com.demo.common;


/***
 * 系统变量
 * @author Administrator
 *
 */
public class Constants {

    public static final String BASEURL = "http://localhost:90/";
    // http请求方式
    public static final String GET = "get";
    public static final String POST = "post";

    // 接口返回状态码
    public static final String CODE_SUCCESS = "200";
    public static final String CODE_FAILURE = "201";

    // 接口返回描述
    public static final String DESC_SUCCESS = "success";
    public static final String DESC_FAILURE = "error";
    public static final String OP_ERROR_MESSAGE = "非法操作";
    public static final String DELETE_FAILURE = "删除失败";

    public static final String USER_SESSION = "user";

    public static final String USER_COOKIE = "user_cookie";

    // 验证码类型
    public static final String FORGET_PWD = "forgetpwd";
    public static final String REG = "reg";
    public static String getValue(String key) {
        //return SysConfig.me.findByKey(key);
    	return null;
    }
}
