package cn.edu.zjut.acs.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.zjut.acs.model.XT_USER;
import cn.edu.zjut.acs.service.XT_USERService;
import cn.edu.zjut.acs.support.JSONReturn;


@Controller
public class LoginController {
	
	@Resource
	private XT_USERService xT_USERService;
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("redirect:/manage/index.html");
	}
	/*public String login() {
		return "manage/index.html";
	}*/
	//这里要是之后登陆界面改回来
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemEmployeeEdit(Model model,HttpSession session,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "psw", required = false) String password
			)
	{
		List<XT_USER> list=xT_USERService.getUserByUsername(username);
		if(list!=null)
		{
			XT_USER user=list.get(0);
			if(user.getPassword().equals(password.trim()))
			{
				return JSONReturn.buildSuccessWithEmptyBody();
			}
			else
			{
				return JSONReturn.buildFailure(new String("密码错误"));
			}
		}
		return JSONReturn.buildFailureWithEmptyBody();
	}
	
}
