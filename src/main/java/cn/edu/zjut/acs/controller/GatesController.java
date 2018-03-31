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

import cn.edu.zjut.acs.model.AREA;
import cn.edu.zjut.acs.model.Gate;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.AreaService;
import cn.edu.zjut.acs.service.GatesService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.support.ResourceBean;
import cn.edu.zjut.acs.util.GetRemoteAddr;



@Controller
@RequestMapping("/manage")
public class GatesController {

	@Resource
	private GatesService gatesService;
	@Resource
	private AreaService areaService;
	@Resource
	private ResourceBean resourceBean;

	@RequestMapping(value = "/system-gates-manage.html")
	public String gatesManage(
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "gatesname", required = false ) String gatesname,
			@RequestParam(value = "areacode", required = false ) Integer areacode,
			@RequestParam(value = "inorout", required = false ) Integer inorout,
			Model model,HttpSession session) {
		Integer areaNum = resourceBean.getAreaNum();
		/*StringBuffer area = new StringBuffer("(");
		if(areaNum == 1){
			area.append(resourceBean.getArea_1());
		} else if(areaNum == 2){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2());
		} else if(areaNum == 3){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2()).append("、").append(resourceBean.getArea_3());
		}
		area.append(")");*/
		model.addAttribute("area", null);
		Map<String, Object> conditions = new HashMap<String, Object>();
		//查询条件
		if(StringUtils.isNotBlank(gatesname)){
			conditions.put("gatesname",gatesname);
		}
		if(areacode != null){
			conditions.put("areacode",areacode);
		}
		if(inorout != null){
			conditions.put("inorout",inorout);
		}
		Map<String, Object> pageMap = gatesService.getGatesList(nowPage,conditions);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		List<AREA> areaList = areaService.getAreaList(null);
		model.addAttribute("areaList", areaList);
		return "manage/gates/system-gates-manage";
	}
	
	@RequestMapping(value = "/system-gates-add.html", method = RequestMethod.GET)
	public String gatesAdd(Model model,HttpSession session) {
		Integer areaNum = resourceBean.getAreaNum();
		StringBuffer area = new StringBuffer("(");
		
			area.append(resourceBean.getArea_1());
		 /*else if(areaNum == 2){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2());
		} else if(areaNum == 3){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2()).append("、").append(resourceBean.getArea_3());
		}*/
		area.append(")");
		model.addAttribute("area", area);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("level", 1);
		List<AREA> areaList = areaService.getAreaList(conditions);
		conditions.clear();
		conditions.put("level", 2);
		List<AREA> departmentList = areaService.getAreaList(conditions);
		model.addAttribute("areaList", areaList);
		model.addAttribute("departmentList", departmentList);
		
		return "manage/gates/system-gates-add";
	}
	
	@RequestMapping(value = "/system-gates-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn gatesAdd(HttpSession session, HttpServletRequest request,
			@Valid Gate gates, BindingResult bindingresult) {
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
		log.setContent("添加门禁闸机:" + gates.getUniqueid());
		log.setClientip(ipaddress);
		//添加门禁闸机
		this.gatesService.t_insert(gates, log);
		return JSONReturn.buildSuccessWithEmptyBody();
	}
	
	@RequestMapping(value = "/check-gatesname.html", method = RequestMethod.GET)
	public String checkUniqueid(HttpServletResponse response,
			@RequestParam(value = "gatesname", required = false) String gatesname,
			@RequestParam(value = "areacode", required = false) Integer areacode)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		if(areacode != null && StringUtils.isNotBlank(gatesname)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("areacode", areacode);
			map.put("gatesname", gatesname);
			List<Gate> gates = gatesService.getGatesList(map);
			if (gates != null && gates.size() > 0) {
				response.getWriter().println("false");
			} else {
				response.getWriter().println("true");
			}
		}else if(areacode == null || StringUtils.isBlank(gatesname)){
			response.getWriter().println("true");
		} else {
			response.getWriter().println("false");
		}
		return null;
	}
	
	@RequestMapping(value = { "system-gates-del" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn gatesDelete(@RequestParam(value = "id", required = false) Integer id,
			HttpSession session, HttpServletRequest request) {
		if (id != null) {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			Gate gates = this.gatesService.getGatesByPK(id);
			if (gates != null) {
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("删除门禁闸机:" + gates.getUniqueid());
				log.setClientip(ipaddress);
				this.gatesService.t_delete(gates, log);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-gates-del" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONReturn gatesDelete(
			@RequestParam(value = "ids", required = false) Integer[] ids,
			HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			gatesService.t_delete(ids, session_loginname, ipaddress);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = "/system-gates-edit.html", method = RequestMethod.GET)
	public String gatesEdit(Model model,HttpSession session,
			@RequestParam(value = "id", required = false) Integer id) {
		if (id != null) {
			Gate gates = this.gatesService.getGatesByPK(id);
			model.addAttribute("gates", gates);
		}
		Integer areaNum = resourceBean.getAreaNum();
		StringBuffer area = new StringBuffer("(");
		area.append(resourceBean.getArea_1());
		/*if(areaNum == 1){
			area.append(resourceBean.getArea_1());
		} else if(areaNum == 2){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2());
		} else if(areaNum == 3){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2()).append("、").append(resourceBean.getArea_3());
		}*/
		area.append(")");
		model.addAttribute("area", area);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("level", 1);
		List<AREA> areaList = areaService.getAreaList(conditions);
		conditions.clear();
		conditions.put("level", 2);
		List<AREA> departmentList = areaService.getAreaList(conditions);
		model.addAttribute("areaList", areaList);
		model.addAttribute("departmentList", departmentList);
		
		return "manage/gates/system-gates-edit";
	}
	
	@RequestMapping(value = "/system-gates-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn gatesEdit(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "gatesid", required = false) Integer gatesid,
			@RequestParam(value = "gatesname", required = false ) String gatesname,
			@RequestParam(value = "areacode", required = false) Integer areacode,
			@RequestParam(value = "areacode2", required = false) Integer areacode2,
			@RequestParam(value = "uniqueid", required = false) String uniqueid,
			@RequestParam(value = "type", required = false) Integer type,
			@RequestParam(value = "inorout", required = false) Integer inorout,
			@RequestParam(value = "address", required = false) String address) {
		if (gatesid != null) {
			Gate gates = this.gatesService.getGatesByPK(gatesid);
			if (gates != null) {
				boolean flag = false;
				String ipaddress = GetRemoteAddr.getIpAddr(request);
				String session_loginname = (String) session.getAttribute("session_loginname");
				StringBuffer content = new StringBuffer("修改了门禁闸机信息");
				if(StringUtils.isNotBlank(gatesname) && !gatesname.equals(gates.getGatesname())){
					flag = true;
					content.append("；将硬件名称由--").append(gates.getGatesname()).append("--修改为--").append(gatesname);
					gates.setGatesname(gatesname);
				}
				if(areacode != null && areacode != gates.getAreacode()){
					flag = true;
					AREA area1 = areaService.getAreaByPK(gates.getAreacode());
					AREA area2 = areaService.getAreaByPK(areacode);
					content.append("；将所属单位由--").append(area1.getAreaname()).append("--修改为--").append(area2.getAreaname());
					gates.setAreacode(areacode);
				}
				if(areacode2 != null && areacode2 != gates.getAreacode2()){
					flag = true;
					AREA area1 = areaService.getAreaByPK(gates.getAreacode2());
					AREA area2 = areaService.getAreaByPK(areacode2);
					content.append("；将所属部门由--").append(area1.getAreaname()).append("--修改为--").append(area2.getAreaname());
					gates.setAreacode2(areacode2);
				}
				if(StringUtils.isNotBlank(uniqueid) && !uniqueid.equals(gates.getUniqueid())){
					flag = true;
					content.append("；将硬件代码由--").append(gates.getUniqueid()).append("--修改为--").append(uniqueid);
					gates.setUniqueid(uniqueid);
				}
				if(type != null && type != gates.getType()){
					flag = true;
					content.append("；将终端类型由--").append(gates.getType()).append("--修改为--").append(type);
					gates.setType(type);
				}
				if(inorout != null && inorout != gates.getInorout()){
					flag = true;
					content.append("；将左右由--").append(type == 1 ? "进" : "出").append("--修改为--").append(type == 1 ? "进" : "出");
					gates.setType(type);
				}
				if(StringUtils.isNotBlank(address) && !address.equals(gates.getAddress())){
					flag = true;
					content.append("；将详细位置信息由--").append(gates.getAddress()).append("--修改为--").append(address);
					gates.setAddress(address);
				}
				if(flag){
					// 记录日志
					XT_LOG log = new XT_LOG();
					log.setUsername(session_loginname);
					log.setContent(content.toString());
					log.setClientip(ipaddress);
					//修改用户
					this.gatesService.t_update(gates, log);
				}
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
}
