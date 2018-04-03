package cn.edu.zjut.acs.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zjut.acs.dto.AuthorityDTO;
import cn.edu.zjut.acs.model.Authority;
import cn.edu.zjut.acs.model.Module;
import cn.edu.zjut.acs.service.AuthorityService;
import cn.edu.zjut.acs.service.ModuleService;
import cn.edu.zjut.acs.support.JSONReturn;

@Controller
@RequestMapping("/manage")
public class AuthorityController {
	
	@Resource
	private ModuleService moduleService;
	@Resource
	private AuthorityService authorityService;
	
	
	@RequestMapping(value = "/system-authority-manage.html")
	public String authorityManage(
			@RequestParam(value = "roleid", required = false) String roleid,
			Model model)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("modulelevel", 0);
		List<Module> list = moduleService.getModuleList(map);
		model.addAttribute("moduleList", list);
		model.addAttribute("size", list.size());
		
		map.clear();
		map.put("modulelevel", 1);
		List<Module> menuList = moduleService.getModuleList(map);
		
		map.clear();
		map.put("roleid", roleid);
		List<AuthorityDTO> authorityList=authorityService.getAuthorityList(map);
		model.addAttribute("authorityList",authorityList);
		for(Module module: menuList)
		{
			boolean flag=false;
			for(AuthorityDTO authority:authorityList)
			{
				if(Integer.compare(authority.getModuleid(), module.getModuleid())==0)
					flag=true;
			}
			if(flag==true)
				module.setModulelevel(0);
			else
				module.setModulelevel(1);
		}
		model.addAttribute("menuList",menuList);
		model.addAttribute("roleid",roleid);
		
		return "manage/authority/system-authority-manage";
	}
	@RequestMapping(value = "/system-authority-edit")
	@ResponseBody
	public JSONReturn authorityEdit(
			@RequestParam(value = "roleid", required = false) Integer roleid,
			@RequestParam(value = "menu", required = false) int[] menu,
			
			
			HttpSession session,
			Model model)
	{
		/*for(int i=0;i<menu1.length;i++)
			System.out.println(menu1[i]);
		//int[] count=(int[]) session.getAttribute("count");
		for(int i=0;i<count.length;i++)
			System.out.println(count[i]);
		for(int i=0;i<module.length;i++)
			System.out.println(module[i]);
		System.out.println(roleid+"---------------");*/
		List<Integer > moduleid=new LinkedList<Integer>();
		Set<Integer> se=new HashSet<Integer>();
		for(int i=0;i<menu.length;i++)
		{
			Module module=moduleService.getEntityByModuleid(menu[i]);
			moduleid.add(menu[i]);
			if(Integer.compare(module.getModulelevel(), 0)!=0)
			{
				se.add(moduleService.getEntityByPK(module.getSupercode()).getModuleid());
			}
			
		}
		for(Integer i:se)
		{
			moduleid.add(i);
		}
		authorityService.t_update(roleid, moduleid);
		return JSONReturn.buildSuccessWithEmptyBody();
	}
}
