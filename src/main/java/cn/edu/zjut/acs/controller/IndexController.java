package cn.edu.zjut.acs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zjut.acs.dto.MenuBarDTO;
import cn.edu.zjut.acs.model.Module;
import cn.edu.zjut.acs.service.ModuleService;
import cn.edu.zjut.acs.support.JSONReturn;


@Controller
@RequestMapping("/manage")
public class IndexController {
	@Resource
	private ModuleService moduleService;
	
	@RequestMapping(value = "/index.html")
	public String index(Model model,HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		//菜单列表
		List<MenuBarDTO> menuList = new ArrayList<MenuBarDTO>();
		// 取主菜单
		Integer roleid=(Integer) session.getAttribute("roleid");
		map.put("roleid", roleid);
		List<Module> allModuleList = moduleService.getModuleListWithAhthority(map);
		List<Module> moduleList=new ArrayList<Module>();
		for(Module module:allModuleList)
		{
			if(Integer.compare(module.getModulelevel(), 0)==0)
			{
				moduleList.add(module);
			}
		}
		if(moduleList != null && moduleList.size()>0 ){
			//System.out.println(moduleList.size());
			for (Module module : moduleList) {
				//取主菜单下级菜单
				/*map.clear();
				map.put("supercode", module.getModulecode());*/
				List<Module> mList = new ArrayList<Module>();
				for(Module menu:allModuleList)
				{
					if(Integer.compare(menu.getSupercode(), module.getModulecode())==0)
					{
						mList.add(menu);
					}
				}
				//菜单栏
				MenuBarDTO menuBarDto = new MenuBarDTO();
				menuBarDto.setModulecode(module.getModulecode());
				menuBarDto.setModulename(module.getModulename());
				menuBarDto.setModule(mList);
				
				menuList.add(menuBarDto);
			}
		}
		model.addAttribute("menuList", menuList);
		
		return "manage/index";
	}
	@RequestMapping(value = "/welcome.html")
	public String welcome() {
		return "manage/welcome";
	}
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public JSONReturn systemEmployeeEdit(Model model,HttpSession session
			
			)
	{
		return JSONReturn.buildSuccess("我传来的消息");
	}
}
