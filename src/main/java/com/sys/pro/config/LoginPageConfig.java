package com.sys.pro.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

public class LoginPageConfig {
	@RequestMapping
	private RedirectView LoginPage() {
		// TODO Auto-generated method stub
		return new RedirectView("/login.html");		
	}
}
