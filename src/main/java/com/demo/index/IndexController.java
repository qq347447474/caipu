package com.demo.index;

import com.jfinal.core.Controller;

/**
 * IndexController
 */
public class IndexController extends Controller {
	public void index() {
		render("/signin.html");
	}
	
	public void reg(){
		render("/signup.html");
	}
	
	public void login(){
		render("/signin.html");
	}
}





