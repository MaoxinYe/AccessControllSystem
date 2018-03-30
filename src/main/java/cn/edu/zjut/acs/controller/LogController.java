package cn.edu.zjut.acs.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.zjut.acs.service.LogService;



@Controller
@RequestMapping("/manage")
public class LogController {

	@Resource
	private LogService logService;
	
	@RequestMapping(value = "/system-log-manage.html")
	public String logManage(
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "loginname", required = false ) String loginname,
			@RequestParam(value = "starttime", required = false ) String starttime,
			@RequestParam(value = "endtime", required = false ) String endtime,
			Model model,HttpSession session) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		//查询条件
		if(StringUtils.isNoneBlank(loginname)){
			conditions.put("username", loginname);
		}
		if(StringUtils.isNoneBlank(starttime)){
			conditions.put("starttime",starttime);
		}
		if(StringUtils.isNoneBlank(endtime)){
			conditions.put("endtime",endtime);
		}
		Map<String, Object> pageMap = logService.getLogList(conditions,nowPage);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		return "manage/log/system-log-manage";
	}
	
}
