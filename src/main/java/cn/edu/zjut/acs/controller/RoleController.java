package cn.edu.zjut.acs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
public class RoleController {

	@Resource
	private RoleService roleService;
	@Resource
	private UserService userService;
	
	@RequestMapping(value = "/system-role-manage.html")
	public String systemRole(Model model) {
		List<XT_ROLE> roleList = roleService.getRoleList(null);
		model.addAttribute("roleList", roleList);
		return "manage/role/system-role-manage";
	}
	
	@RequestMapping(value = "/system-role-add.html", method = RequestMethod.GET)
	public String systemRoleAdd() {
		return "manage/role/system-role-add";
	}
	
	@RequestMapping(value = "/system-role-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemroleAdd(HttpSession session, HttpServletRequest request,
			@Valid XT_ROLE role,  BindingResult bingingresult) {
		if(bingingresult.hasErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bingingresult.getAllErrors();  
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
		log.setContent("添加角色类型:" + role.getRolename());
		log.setClientip(ipaddress);
		//添加角色类型
		this.roleService.t_insert(role, log);
		return JSONReturn.buildSuccessWithEmptyBody();
	}
	
	@RequestMapping(value = "/check-rolename.html", method = RequestMethod.GET)
	public String checkRoleName(HttpServletResponse response,
			@RequestParam(value = "rolename", required = false) String rolename)
					throws Exception {
		response.setCharacterEncoding("utf-8");
		if (StringUtils.isNotBlank(rolename)) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("rolename", rolename);
			List<XT_ROLE> list = this.roleService.getRoleList(map);
			if (list != null && list.size() > 0) {
				response.getWriter().println("false");
			} else {
				response.getWriter().println("true");
			}
		} else {
			response.getWriter().println("false");
		}
		return null;
	}
	
	@RequestMapping(value = "/system-role-edit.html", method = RequestMethod.GET)
	public String systemroleEdit(Model model,
			@RequestParam(value = "roleid", required = false) Integer roleid) {
		if (roleid != null) {
			XT_ROLE role = this.roleService.getRoleByPK(roleid);
			model.addAttribute("role", role);
		}
		return "manage/role/system-role-edit";
	}
	
	@RequestMapping(value = "/system-role-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemroleEdit(HttpSession session, HttpServletRequest request,
			@Valid XT_ROLE role,  BindingResult bingingresult) {
		if(bingingresult.hasErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bingingresult.getAllErrors();  
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage());
			}
			return JSONReturn.buildFailure(sb.toString());
		}
		if (role != null && role.getRoleid() != null) {
			XT_ROLE entity = this.roleService.getRoleByPK(role.getRoleid());
			if (entity != null) {
				String ipaddress = GetRemoteAddr.getIpAddr(request);
				String session_loginname = (String) session.getAttribute("session_loginname");
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("将角色类型:" + entity.getRolename() + "修改为:" + role.getRolename());
				log.setClientip(ipaddress);
				//修改角色类型名称
				entity.setRolename(role.getRolename());
				this.roleService.t_update(entity, log);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-role-del" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn systemroleDelete(
			@RequestParam(value = "roleid", required = false) Integer roleid,
			HttpSession session, HttpServletRequest request) {
		if (roleid != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("roleid",roleid);
			List<XT_USER> userList = userService.getUserList(map);
			if(userList != null && userList.size() > 0){
				return JSONReturn.buildFailure("该角色类型已被引用！");
			}
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			XT_ROLE entity = this.roleService.getRoleByPK(roleid);
			if (entity != null) {
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("将角色类型:" + entity.getRolename() + "删除了:");
				log.setClientip(ipaddress);
				this.roleService.t_delete(entity, log);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-role-del" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONReturn systemroleDelete(
			@RequestParam(value = "ids", required = false) Integer[] ids,
			HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			for (Integer roleid : ids) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("roleid",roleid);
				List<XT_USER> userList = userService.getUserList(map);
				if(userList != null && userList.size() > 0){
					XT_ROLE role = roleService.getRoleByPK(roleid);
					return JSONReturn.buildFailure("角色类型"+role.getRolename()+"已被引用！");
				}
			}
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			this.roleService.t_delete(ids,ipaddress,session_loginname);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
}
