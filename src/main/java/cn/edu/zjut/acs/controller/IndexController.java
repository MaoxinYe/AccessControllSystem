package cn.edu.zjut.acs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.zjut.acs.dto.MenuBarDTO;
import cn.edu.zjut.acs.model.Module;
import cn.edu.zjut.acs.service.ModuleService;


@Controller
@RequestMapping("/manage")
public class IndexController {
	@Resource
	private ModuleService moduleService;
	
	@RequestMapping(value = "/index.html")
	public String index(Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		//菜单列表
		List<MenuBarDTO> menuList = new ArrayList<MenuBarDTO>();
		// 取主菜单
		map.put("modulelevel", 0);
		List<Module> moduleList = moduleService.getModuleList(map);
		if(moduleList != null && moduleList.size()>0 ){
			for (Module module : moduleList) {
				//取主菜单下级菜单
				map.clear();
				map.put("supercode", module.getModulecode());
				List<Module> mList = moduleService.getModuleList(map);
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
}
