package cn.edu.zjut.acs.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import cn.edu.zjut.acs.model.Worktime;
import cn.edu.zjut.acs.service.WorktimeService;
import cn.edu.zjut.acs.support.JSONReturn;



@Controller
@RequestMapping("/manage")
public class WorktimeController {
	
	@Resource
	private WorktimeService worktimeService;
	
	@RequestMapping(value = "/system-worktime-manage.html")
	public String worktimeManage(Model model){
		List<Worktime> worktimeList = worktimeService.getWorktimeList(null);
		model.addAttribute("worktimeList", worktimeList);
		return "manage/worktime/system-worktime-manage";
	}
	
	@RequestMapping(value = "/system-worktime-add.html", method = RequestMethod.GET)
	public String worktimeAdd(Model model) {
		return "manage/worktime/system-worktime-add";
	}
	
	@RequestMapping(value = "/system-worktime-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn worktimeAdd(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@Valid Worktime worktime,  BindingResult bingingresult) throws ParseException {
		if(bingingresult.hasErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bingingresult.getAllErrors();  
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage());
			}
			return JSONReturn.buildFailure(sb.toString());
		}
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		if(StringUtils.isNotBlank(startTime)){
			worktime.setStarttime(sdf.parse(startTime));
		} else {
			return JSONReturn.buildFailure("上班时间不能为空");
		}
		if(StringUtils.isNotBlank(endTime)){
			worktime.setEndtime(sdf.parse(endTime));
		} else {
			return JSONReturn.buildFailure("下班时间不能为空");
		}
		if(worktime.getStatus() != null && worktime.getStatus() == 1){
			Map<String, Object> map = new HashMap<String, Object>();
			List<Worktime> worktimeList = worktimeService.getWorktimeList(map);
			this.worktimeService.t_update(worktimeList, null);
		}
		//添加角色类型
		this.worktimeService.saveWorktime(worktime);
		return JSONReturn.buildSuccessWithEmptyBody();
	}
	
	@RequestMapping(value = "/system-worktime-edit.html", method = RequestMethod.GET)
	public String worktimeEdit(Model model,
			@RequestParam(value = "id", required = false) Integer id) {
		Worktime worktime = worktimeService.getWorktimeByPK(id);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		model.addAttribute("startTime", sdf.format(worktime.getStarttime()));
		model.addAttribute("endTime", sdf.format(worktime.getEndtime()));
		model.addAttribute("worktime", worktime);
		return "manage/worktime/system-worktime-edit";
	}
	
	@RequestMapping(value = "/system-worktime-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn worktimeEdit(@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "illustrate", required = false) String illustrate,
			HttpServletRequest request,HttpSession session) throws ParseException {
		if(id != null){
			Worktime worktime = worktimeService.getWorktimeByPK(id);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			if(StringUtils.isNotBlank(startTime)){
				worktime.setStarttime(sdf.parse(startTime));
			}
			if(StringUtils.isNotBlank(endTime)){
				worktime.setEndtime(sdf.parse(endTime));
			}
			if(StringUtils.isNotBlank(illustrate)){
				worktime.setIllustrate(illustrate);
			}
			this.worktimeService.updateWorktime(worktime);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = "/check-illustrate.html", method = RequestMethod.GET)
	public String checkDlm(HttpServletResponse response,
			@RequestParam(value = "illustrate", required = false) String illustrate)
			throws Exception {
		if(StringUtils.isNotBlank(illustrate)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("illustrate", illustrate);
			List<Worktime> worktimeList = worktimeService.getWorktimeList(map);
			if (worktimeList != null && worktimeList.size() > 0) {
				response.getWriter().println("false");
			} else {
				response.getWriter().println("true");
			}
		}else{
			response.getWriter().println("false");
		}
		return null;
	}
	
	@RequestMapping(value = { "system-worktime-del" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn worktimeDelete(
			@RequestParam(value = "id", required = false) Integer id,
			HttpSession session, HttpServletRequest request) {
		if (id != null) {
			Worktime worktime = worktimeService.getWorktimeByPK(id);
			if (worktime != null) {
				this.worktimeService.deleteWorktime(worktime);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-worktime-del" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONReturn worktimeDelete(
			@RequestParam(value = "ids", required = false) Integer[] ids,
			HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			this.worktimeService.t_delete(ids);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-worktime-status-operation" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn worktimeStatusOperation(
			@RequestParam(value = "id", required = false) Integer id,
			HttpSession session, HttpServletRequest request) {
		if (id != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			List<Worktime> worktimeList = worktimeService.getWorktimeList(map);
			Worktime worktime = worktimeService.getWorktimeByPK(id);
			this.worktimeService.t_update(worktimeList, worktime);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
}
