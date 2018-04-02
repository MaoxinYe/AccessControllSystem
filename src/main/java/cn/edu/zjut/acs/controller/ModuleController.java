package cn.edu.zjut.acs.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zjut.acs.controller.validate.MenubarValidation;
import cn.edu.zjut.acs.controller.validate.ModuleValidation;
import cn.edu.zjut.acs.model.Module;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.ModuleService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.util.GetRemoteAddr;

import com.google.common.base.Strings;

@Controller
@RequestMapping("/manage")
public class ModuleController {
	
	@Resource
	private ModuleService moduleService;
	
	@RequestMapping(value = "/system-module-manage.html")
	public String systemModule(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modulelevel", 0);
		List<Module> list = moduleService.getModuleList(map);
		model.addAttribute("moduleList", list);
		return "manage/module/system-module-manage";
	}
	
	@RequestMapping(value = "/system-module-add.html", method = RequestMethod.GET)
	public String systemModuleAdd() {
		return "manage/module/system-module-add";
	}
	
	@RequestMapping(value = "/system-module-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemModuleAdd(HttpSession session, HttpServletRequest request,
			@Valid ModuleValidation mv,  BindingResult bingingresult) {
		if(bingingresult.hasErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bingingresult.getAllErrors();  
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage());
			}
			return JSONReturn.buildFailure(sb.toString());
		}
		Module entity = this.moduleService.getEntityByPK(Integer.valueOf(mv.getModulecode()));
		if (entity != null) {
			return JSONReturn.buildFailure("系统模块已存在!");
		} else {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			// 记录日志
			XT_LOG log = new XT_LOG();
			log.setUsername(session_loginname);
			log.setContent("添加系统模块:" + mv.getModulecode() + "--" + mv.getModulename());
			log.setClientip(ipaddress);
			log.setAddtime(new Date());
			//添加模块
			Module module = new Module();
			module.setModulecode(Integer.valueOf(mv.getModulecode()));
			module.setModulename(mv.getModulename());
			module.setSupercode(0);
			module.setModulelevel(0);
			module.setAddtime(new Date());
			this.moduleService.t_insert(module, log);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
	}
	
	@RequestMapping(value = "/check-modulecode.html", method = RequestMethod.GET)
	public String checkMkid(HttpServletResponse response,
			@RequestParam(value = "modulecode", required = false) String modulecode)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		if (!Strings.isNullOrEmpty(modulecode)) {
			Module entity = this.moduleService.getEntityByPK(Integer.valueOf(modulecode));
			if (entity != null) {
				response.getWriter().println("false");
			} else {
				response.getWriter().println("true");
			}
		} else {
			response.getWriter().println("false");
		}
		return null;
	}
	
	@RequestMapping(value = "/check-modulecode.html", method = RequestMethod.POST)
	public void checkMkidPost(HttpServletResponse response,
			@RequestParam(value = "id", required = false) String id)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		if (!Strings.isNullOrEmpty(id)) {
			Module entity = this.moduleService.getEntityByPK(Integer.valueOf(id));
			if (entity != null) {
				if (Integer.compare(entity.getModulecode(), Integer.valueOf(id)) == 0 ) {
					response.getWriter().println("true");
				} else {
					response.getWriter().println("false");
				}
			} else {
				response.getWriter().println("true");
			}
		} else {
			response.getWriter().println("false");
		}
	}
	
	@RequestMapping(value = "/check-modulename.html", method = RequestMethod.GET)
	public void checkMkmc(HttpServletResponse response,
			@RequestParam(value = "modulename", required = false) String modulename)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		if (!Strings.isNullOrEmpty(modulename)) {
			Module entity = this.moduleService.getModuleByModulename(modulename);
			if (entity != null) {
				response.getWriter().println("false");
			} else {
				response.getWriter().println("true");
			}
		} else {
			response.getWriter().println("false");
		}
	}
	
	@RequestMapping(value = "/check-modulepage.html", method = RequestMethod.GET)
	public void checkMklj(HttpServletResponse response,
			@RequestParam(value = "modulepage", required = false) String modulepage)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		if (!Strings.isNullOrEmpty(modulepage)) {
			Module entity = this.moduleService.getModuleByModulepage(modulepage);
			if (entity != null) {
				response.getWriter().println("false");
			} else {
				response.getWriter().println("true");
			}
		} else {
			response.getWriter().println("false");
		}
	}
	
	@RequestMapping(value = "/system-module-edit.html", method = RequestMethod.GET)
	public String systemModuleEdit(Model model,
			@RequestParam(value = "id", required = false) String id) {
		if (!Strings.isNullOrEmpty(id)) {
			Module entity = this.moduleService.getEntityByPK(Integer.valueOf(id));
			model.addAttribute("module", entity);
		}
		return "manage/module/system-module-edit";
	}
	
	@RequestMapping(value = "/system-module-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemModuleEdit(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id,
			@Valid ModuleValidation mv,  BindingResult bingingresult) {
		if(bingingresult.hasErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bingingresult.getAllErrors();  
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage());
			}
			return JSONReturn.buildFailure(sb.toString());
		}
		if (!Strings.isNullOrEmpty(id)) {
			Module entity = this.moduleService.getEntityByPK(Integer.valueOf(id));
			if (entity != null) {
				String ipaddress = GetRemoteAddr.getIpAddr(request);
				String session_loginname = (String) session.getAttribute("session_loginname");
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("修改系统模块名称:" + entity.getModulecode() + "--" + mv.getModulename());
				log.setClientip(ipaddress);
				log.setAddtime(new Date());
				//修改模块名称
				entity.setModulename(mv.getModulename());
				this.moduleService.t_update(entity, log);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-module-del" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn systemModuleDelete(
			@RequestParam(value = "id", required = false) String id,
			HttpSession session, HttpServletRequest request) {
		if (!Strings.isNullOrEmpty(id)) {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			Module entity = this.moduleService.getEntityByPK(Integer.valueOf(id));
			if (entity != null) {
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("删除系统模块及其下属模块:" + entity.getModulecode());
				log.setClientip(ipaddress);
				log.setAddtime(new Date());
				this.moduleService.t_delete(entity, log);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-module-del" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONReturn systemModuleDelete(
			@RequestParam(value = "ids", required = false) String[] ids,
			HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if (!Strings.isNullOrEmpty(id)) {
					String ipaddress = GetRemoteAddr.getIpAddr(request);
					String session_loginname = (String) session.getAttribute("session_loginname");
					Module entity = this.moduleService.getEntityByPK(Integer.valueOf(id));
					if (entity != null) {
						// 记录日志
						XT_LOG log = new XT_LOG();
						log.setUsername(session_loginname);
						log.setContent("删除系统模块及其下属模块:" + entity.getModulecode());
						log.setClientip(ipaddress);
						log.setAddtime(new Date());
						this.moduleService.t_delete(entity, log);
					}
				}
			}
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = "/system-menubar-manage.html")
	public String systemMenubar(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modulelevel", 1);
		List<Module> menuList = moduleService.getModuleList(map);
		//取所有菜单
		map.clear();
		List<Module> moduleList = moduleService.getModuleList(map);
		model.addAttribute("menuList", menuList);
		model.addAttribute("moduleList",moduleList);
		return "manage/menubar/system-menubar-manage";
	}
	
	@RequestMapping(value = "/system-menubar-add.html", method = RequestMethod.GET)
	public String systemMenubarAdd(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modulelevel", 0);
		List<Module> moduleList = moduleService.getModuleList(map);
		model.addAttribute("moduleList", moduleList);
		return "manage/menubar/system-menubar-add";
	}
	
	@RequestMapping(value = "/system-menubar-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemMenubarAdd(HttpSession session, HttpServletRequest request,
			@Valid MenubarValidation mv,  BindingResult bingingresult) {
		if(bingingresult.hasErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bingingresult.getAllErrors();  
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage());
			}
			return JSONReturn.buildFailure(sb.toString());
		}
		Module entity = this.moduleService.getEntityByPK(Integer.valueOf(mv.getModulecode()));
		if (entity != null) {
			return JSONReturn.buildFailure("该菜单已存在!");
		} else {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			// 记录日志
			XT_LOG log = new XT_LOG();
			log.setUsername(session_loginname);
			log.setContent("添加系统菜单:" + mv.getModulename());
			log.setClientip(ipaddress);
			log.setAddtime(new Date());
			//添加菜单
			Module module = new Module();
			module.setModulename(mv.getModulename());
			module.setModulecode(Integer.valueOf(mv.getModulecode()));
			module.setSupercode(Integer.valueOf(mv.getSupercode()));
			module.setModulepage(mv.getModulepage());
			module.setModulelevel(1);
			module.setAddtime(new Date());
			this.moduleService.t_insert(module, log);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
	}
	
	@RequestMapping(value = "/system-menubar-edit.html", method = RequestMethod.GET)
	public String systemMenubarEdit(Model model,
			@RequestParam(value = "id", required = false) String id) {
		if (!Strings.isNullOrEmpty(id)) {
			Module entity = this.moduleService.getEntityByPK(Integer.valueOf(id));
			model.addAttribute("menu", entity);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modulelevel", 0);
		List<Module> moduleList = moduleService.getModuleList(map);
		model.addAttribute("moduleList", moduleList);
		return "manage/menubar/system-menubar-edit";
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/system-menubar-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemMenubarEdit(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "id", required = false) String id,
			@Valid MenubarValidation mv,  BindingResult bingingresult) {
		if(bingingresult.hasErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bingingresult.getAllErrors();  
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage());
			}
			return JSONReturn.buildFailure(sb.toString());
		}
		if (!Strings.isNullOrEmpty(id)) {
			Module entity = this.moduleService.getEntityByModuleid(Integer.parseInt(id.trim()));
			if(entity!=null && Integer.compare(entity.getModulecode(), Integer.valueOf(mv.getModulecode())) != 0){
				return JSONReturn.buildFailure("该菜单已存在!");
			} else {
				Module editEntity = this.moduleService.getEntityByModuleid(Integer.parseInt(id.trim()));
				String ipaddress = GetRemoteAddr.getIpAddr(request);
				String session_loginname = (String) session.getAttribute("session_loginname");
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("修改系统菜单信息");
				log.setClientip(ipaddress);
				log.setAddtime(new Date());
				//修系统菜单信息
				editEntity.setModulename(mv.getModulename());
				editEntity.setModulecode(Integer.valueOf(mv.getModulecode()));
				editEntity.setSupercode(Integer.valueOf(mv.getSupercode()));
				editEntity.setModulepage(mv.getModulepage());
				this.moduleService.t_update(editEntity, log);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
}
