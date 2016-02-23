package com.demo.common;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class CommonInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		controller.setAttr("baseUrl", Constants.BASEURL);
	}

}
