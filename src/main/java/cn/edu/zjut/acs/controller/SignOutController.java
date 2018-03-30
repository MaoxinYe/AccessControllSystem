package cn.edu.zjut.acs.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignOutController {
	
	@RequestMapping(value = "/sign-out.html")
	public String signOut(HttpSession session) {
		 Enumeration<String> em = session.getAttributeNames();
		 while(em.hasMoreElements()){
			 session.removeAttribute(em.nextElement().toString());
		 }
		// 清除session
		session.invalidate();
		return "redirect:/login.html";
	}
}
