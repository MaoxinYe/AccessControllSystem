package cn.edu.zjut.acs.controller;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zjut.acs.model.Attendance;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.AttendanceService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.util.GetRemoteAddr;



@Controller
@RequestMapping("/manage")
public class AttendanceController {

	@Resource
	private AttendanceService attendanceService;
	
	@RequestMapping(value = "/system-attendance-manage.html")
	public String attendanceManage(
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "personnel_name", required = false) String personnel_name,
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "endtime", required = false) String endtime,
			@RequestParam(value = "status", required = false) Integer status,
			Model model,HttpSession session) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		//查询条件
		if(StringUtils.isNoneBlank(starttime)){
			conditions.put("starttime",starttime);
		}
		if(StringUtils.isNoneBlank(endtime)){
			conditions.put("endtime",endtime);
		}
		if(StringUtils.isNoneBlank(personnel_name)){
			conditions.put("personnel_name",personnel_name);
		}
		if(status != null && status != 100){
			conditions.put("status",status);
		}
		Map<String, Object> pageMap = attendanceService.getAttendanceList(nowPage,conditions);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		return "manage/attendance/system-attendance-manage";
	}
	
	@RequestMapping(value = "/system-attendance-edit.html", method = RequestMethod.GET)
	public String attendanceEdit(Model model,
			@RequestParam(value = "id", required = false) Integer id) {
		if(id != null){
			Attendance attendance = attendanceService.getAttendanceByPK(id);
			model.addAttribute("attendance", attendance);
		}
		return "manage/attendance/system-attendance-edit";
	}
	
	@RequestMapping(value = "/system-attendance-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn attendanceEdit(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "status", required = false) Integer status) {
		if(id != null){
			Attendance attendance = attendanceService.getAttendanceByPK(id);
			if(status != null){
				if(status != attendance.getStatus()){
					SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
					String ipaddress = GetRemoteAddr.getIpAddr(request);
					String session_loginname = (String) session.getAttribute("session_loginname");
					String a = null;
					if(status == -3){
						a = "异常";
					} else if(status == -2){
						a = "迟到+早退";
					} else if(status == -1){
						a = "缺勤";
					} else if(status == 0){
						a = "正常";
					} else if(status == 1){
						a = "迟到";
					} else if(status == 2){
						a = "早退";
					} else if(status == 3){
						a = "出差";
					} else if(status == 4){
						a = "请假";
					} else if(status == 5){
						a = "调休";
					} 
					String b = null;
					if(attendance.getStatus() == -3){
						b = "异常";
					} else if(attendance.getStatus() == -2){
						b = "迟到+早退";
					} else if(attendance.getStatus() == -1){
						b = "缺勤";
					} else if(attendance.getStatus() == 0){
						b = "正常";
					} else if(attendance.getStatus() == 1){
						b = "迟到";
					} else if(attendance.getStatus() == 2){
						b = "早退";
					} else if(attendance.getStatus() == 3){
						b = "出差";
					} else if(attendance.getStatus() == 4){
						b = "请假";
					} else if(attendance.getStatus() == 5){
						b = "调休";
					}
					XT_LOG log = new XT_LOG();
					log.setUsername(session_loginname);
					log.setContent("将"+attendance.getPersonnel_name()+"在"+sdf.format(attendance.getCreatetime())+"的考勤状态由:"+b+"修改为:"+a);
					log.setClientip(ipaddress);
					attendance.setStatus(status);
					attendanceService.t_update(attendance,log);
				}
			}
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = "/system-attendance-statistics.html")
	public String statisticsManage(Model model,HttpSession session,
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "personnel_name", required = false) String personnel_name,
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "endtime", required = false) String endtime) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		//查询条件
		if(StringUtils.isNoneBlank(starttime)){
			conditions.put("starttime",starttime);
		}
		if(StringUtils.isNoneBlank(endtime)){
			conditions.put("endtime",endtime);
		}
		if(StringUtils.isNoneBlank(personnel_name)){
			conditions.put("personnel_name",personnel_name);
		}
		Map<String, Object> pageMap = attendanceService.getAttendanceStatistics(nowPage,conditions);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		return "manage/attendance/system-attendance-statistics";
	}
}
