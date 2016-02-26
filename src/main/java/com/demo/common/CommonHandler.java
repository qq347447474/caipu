package com.demo.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class CommonHandler extends Handler{

	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		System.out.println(request.getServletContext().getRealPath("/"));
		request.getSession().setAttribute("rootPath", request.getServletContext());
		
	}

}
