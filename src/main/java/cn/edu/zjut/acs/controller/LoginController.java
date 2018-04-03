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
import cn.edu.zjut.acs.service.UserService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.support.ResourceBean;


@Controller
public class LoginController {
	
	@Resource
	private UserService userService;
	@Resource
	private ResourceBean resourceBean;
	
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	/*public ModelAndView login() {
		return new ModelAndView("redirect:/manage/index.html");
	}*/
	public String login() {
		return "login";
	}
	//这里要是之后登陆界面改回来
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemEmployeeEdit(Model model,HttpSession session,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "psw", required = false) String password
			)
	{
		List<XT_USER> list=userService.getUserByLoginName(username);
		if(list!=null && list.size()!=0)
		{
			XT_USER user=list.get(0);
			//System.out.println(user.getXt_role().getRolename());
			if(user.getPassword().equals(password.trim()))
			{
				session.setAttribute("session_loginname",user.getUsername());
				session.setAttribute("session_userid",user.getUserid());
				session.setAttribute("session_photoVirtualPath", resourceBean.getVirtualPath());
				session.setAttribute("roleid", user.getRoleid());
				return JSONReturn.buildSuccessWithEmptyBody();
			}
			else
			{
				return JSONReturn.buildFailure(new String("账号或密码错误"));
			}
		}
		return JSONReturn.buildFailure(new String("账号或密码错误"));
	}
	
}
