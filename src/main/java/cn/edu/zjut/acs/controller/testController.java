package cn.edu.zjut.acs.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.zjut.acs.model.Test;
import cn.edu.zjut.acs.service.TestService;


@Controller
public class testController {
	
	@Resource
	private TestService testService;
	
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public String login() {
		
		
		List<Test> list=testService.getTestList();
		for(Test t: list)
		{
			System.out.println(t.getName());
		}
		
		return "login";
	}
}
