package cn.edu.zjut.acs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.model.XT_ROLE;
import cn.edu.zjut.acs.model.XT_USER;
import cn.edu.zjut.acs.service.RoleService;
import cn.edu.zjut.acs.service.UserService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.util.GetRemoteAddr;



@Controller
@RequestMapping("/manage")
public class UserController {
	
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	@RequestMapping(value = "/system-user-manage.html")
	public String userManage(
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "username", required = false ) String username,
			@RequestParam(value = "roleid", required = false ) Integer roleid,
			Model model,HttpSession session) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		//查询条件
		if(StringUtils.isNotBlank(username)){
			conditions.put("username", username);
		}
		if(roleid != null){
			conditions.put("roleid",roleid);
		}
		Integer session_role = (Integer) session.getAttribute("session_role");
		conditions.put("session_role",session_role);
		Map<String, Object> pageMap = userService.getUserList(nowPage,conditions);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("session_role", session_role);
		List<XT_ROLE> roleList = roleService.getRoleList(map);
		model.addAttribute("roleList", roleList);
		return "manage/user/system-user-manage";
	}
	
	@RequestMapping(value = "/system-user-add.html", method = RequestMethod.GET)
	public String userManageAdd(Model model,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		Integer session_role = (Integer) session.getAttribute("session_role");
		map.put("session_role", session_role);
		List<XT_ROLE> roleList = roleService.getRoleList(map);
		model.addAttribute("roleList", roleList);
		return "manage/user/system-user-add";
	}
	
	@RequestMapping(value = "/system-user-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn userManageAdd(HttpSession session, HttpServletRequest request,
			@Valid XT_USER user, BindingResult bindingresult) {
		if(bindingresult.hasFieldErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bindingresult.getAllErrors();  
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage());
			}
			return JSONReturn.buildFailure(sb.toString());
		}
		String ipaddress = GetRemoteAddr.getIpAddr(request);
		String session_loginname = (String) session.getAttribute("session_loginname");
		// 记录日志
		XT_LOG log = new XT_LOG();
		log.setUsername(session_loginname);
		log.setContent("添加用户:" + user.getUsername());
		log.setClientip(ipaddress);
		//加密密码
		user.setPassword(user.getPassword());
		//添加用户
		this.userService.t_insert(user, log);
		return JSONReturn.buildSuccessWithEmptyBody();
	}
	
	@RequestMapping(value = "/check-loginname.html", method = RequestMethod.GET)
	public String checkDlm(HttpServletResponse response,
			@RequestParam(value = "username", required = false) String loginname)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		if(StringUtils.isNotBlank(loginname)){
			List<XT_USER> user = userService.getUserByLoginName(loginname);
			if (user != null && user.size() > 0) {
				response.getWriter().println("false");
			} else {
				response.getWriter().println("true");
			}
		}else{
			response.getWriter().println("false");
		}
		return null;
	}
	
	@RequestMapping(value = { "system-user-del" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn userManageDelete(@RequestParam(value = "id", required = false) Integer id,
			HttpSession session, HttpServletRequest request) {
		if (id != null) {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			XT_USER user = this.userService.getUserByPK(id);
			if (user != null) {
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("删除用户:" + user.getUsername());
				log.setClientip(ipaddress);
				this.userService.t_delete(user, log);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-user-del" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONReturn systemModuleDelete(
			@RequestParam(value = "ids", required = false) Integer[] ids,
			HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			userService.t_delete(ids, session_loginname, ipaddress);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = "/system-user-edit.html", method = RequestMethod.GET)
	public String userManageEdit(Model model,HttpSession session,
			@RequestParam(value = "id", required = false) Integer id) {
		if (id != null) {
			model.addAttribute("update_own_message", 100001);
			XT_USER user = this.userService.getUserByPK(id);
			model.addAttribute("user", user);
			Map<String, Object> map = new HashMap<String, Object>();
			Integer session_role = (Integer) session.getAttribute("session_role");
			map.put("session_role", session_role);
			List<XT_ROLE> roleList = roleService.getRoleList(map);
			model.addAttribute("roleList", roleList);
		} else {
			Integer userid = (Integer) session.getAttribute("session_userid");
			if(userid != null){
				XT_USER user = this.userService.getUserByPK(userid);
				XT_ROLE role = this.roleService.getRoleByPK(user.getRoleid());
				model.addAttribute("user", user);
				model.addAttribute("role", role);
				model.addAttribute("update_own_message", 100002);
			}
		}
		return "manage/user/system-user-edit";
	}
	
	@RequestMapping(value = "/system-user-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn userManageEdit(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "userid", required = false) Integer userid,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "roleid", required = false) Integer roleid) {
		if (userid != null) {
			XT_USER user = this.userService.getUserByPK(userid);
			if (user != null) {
				boolean flag = false;
				String ipaddress = GetRemoteAddr.getIpAddr(request);
				String session_loginname = (String) session.getAttribute("session_loginname");
				StringBuffer content = new StringBuffer("修改了用户信息");
				if(StringUtils.isNotBlank(username) && !username.equals(user.getUsername())){
					flag = true;
					content.append("；将姓名由--").append(user.getUsername()).append("--修改为--").append(username);
					user.setUsername(username);
				}
				if(roleid != null && roleid != user.getRoleid()){
					flag = true;
					content.append("；将角色类型由--").append(roleService.getRoleByPK(user.getRoleid()).getRolename()).append("--修改为--").append(roleService.getRoleByPK(roleid).getRolename());
					user.setRoleid(roleid);
				}
				if(StringUtils.isNotBlank(password)){
					flag = true;
					user.setPassword(password);
				}
				if(flag){
					// 记录日志
					XT_LOG log = new XT_LOG();
					log.setUsername(session_loginname);
					log.setContent(content.toString());
					log.setClientip(ipaddress);
					//修改用户
					this.userService.t_update(user, log);
				}
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}

	@RequestMapping(value = "/user-password-edit.html", method = RequestMethod.GET)
	public String userPassword(@RequestParam(value = "userid", required = false) Integer userid,Model model) {
		model.addAttribute("userid", userid);
		return "manage/user/user-password-edit";
	}
	
	@RequestMapping(value = "/user-password-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn userPassword(HttpSession session,HttpServletRequest request,
			@RequestParam(value = "userid", required = false) Integer userid,
			@RequestParam(value = "password", required = false) String password) {
		if(StringUtils.isNoneBlank(password)){
			if (userid != null) {
				XT_USER user = this.userService.getUserByPK(userid);
				if (user != null) {
					String ipaddress = GetRemoteAddr.getIpAddr(request);
					String session_loginname = (String) session.getAttribute("session_loginname");
					Integer session_userid = (Integer) session.getAttribute("session_userid");
					// 记录日志
					XT_LOG log = new XT_LOG();
					log.setUsername(session_loginname);
					log.setContent("修改了用户--"+user.getUsername()+"--的密码");
					log.setClientip(ipaddress);
					//修改用户
					user.setPassword(password);
					this.userService.t_update(user,log);
					if(session_userid.intValue() == userid.intValue()){
						return JSONReturn.buildSuccess("100001");
					} else {
						return JSONReturn.buildSuccess("100002");
					}
				} else {
					return JSONReturn.buildFailure("找不到该用户!");
				}
			}
			return JSONReturn.buildFailure("数据错误!");		
		} else {
			return JSONReturn.buildFailure("新密码不能为空!");		
		}
	}
	
}
