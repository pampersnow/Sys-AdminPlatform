package com.sys.pro.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author JYB 
 * @interfaceName：LoginPageConfig
 * @date 2018.10
 * @version 1.00
 */
@Controller
public class LoginPageConfig {
	@RequestMapping
	private RedirectView LoginPage() {
		// TODO Auto-generated method stub
		return new RedirectView("/login.html");		
	}
}
