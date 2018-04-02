package cn.edu.zjut.acs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.zjut.acs.model.AREA;
import cn.edu.zjut.acs.model.Face;
import cn.edu.zjut.acs.model.Gate;
import cn.edu.zjut.acs.model.Personnel;
import cn.edu.zjut.acs.model.Record;
import cn.edu.zjut.acs.model.Visitor;
import cn.edu.zjut.acs.service.AreaService;
import cn.edu.zjut.acs.service.FaceService;
import cn.edu.zjut.acs.service.GatesService;
import cn.edu.zjut.acs.service.PersonnelService;
import cn.edu.zjut.acs.service.RecordService;
import cn.edu.zjut.acs.service.VisitorService;
import cn.edu.zjut.acs.support.ResourceBean;

@Controller
@RequestMapping("/manage")
public class RecordController {

	@Resource
	private RecordService recordService;
	@Resource
	private ResourceBean resourceBean;
	@Resource
	private AreaService areaService;
	@Resource
	private PersonnelService personnelService;
	@Resource
	private VisitorService visitorService;
	@Resource
	private FaceService faceidService;
	@Resource
	private GatesService gatesService;
	
	@RequestMapping(value = "/system-record-manage.html")
	public String recordManage(
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "inorout", required = false) Integer inorout,
			@RequestParam(value = "areacode", required = false) Integer areacode,
			@RequestParam(value = "personneltype", defaultValue = "1") Integer personneltype,
			@RequestParam(value = "starttime", required = false) String starttime,
			@RequestParam(value = "endtime", required = false) String endtime,
			Model model,HttpSession session) {
		Integer areaNum = resourceBean.getAreaNum();
		StringBuffer area = new StringBuffer("(");
		if(areaNum == 1){
			area.append(resourceBean.getArea_1());
		} else if(areaNum == 2){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2());
		} else if(areaNum == 3){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2()).append("、").append(resourceBean.getArea_3());
		}
		area.append(")");
		model.addAttribute("area", area);
		model.addAttribute("areaList", areaService.getAreaList(null));
		Map<String, Object> conditions = new HashMap<String, Object>();
		//查询条件
		if(inorout != null){
			conditions.put("inorout", inorout);
		}
		if(areacode != null){
			conditions.put("areacode",areacode);
		}
		if(personneltype != null){
			conditions.put("personneltype",personneltype);
		}
		if(StringUtils.isNoneBlank(starttime)){
			conditions.put("starttime",starttime);
		}
		if(StringUtils.isNoneBlank(endtime)){
			conditions.put("endtime",endtime);
		}
		Map<String, Object> pageMap = recordService.getRecordList(conditions,nowPage);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		model.addAttribute("recordType", resourceBean.getRecordType());
		return "manage/record/system-record-manage";
	}
	
	@RequestMapping(value = "/personnel-record-info.html")
	public String personnelRecordInfo(Model model,
			@RequestParam(value = "id",required = false)Integer id){
		Integer areaNum = resourceBean.getAreaNum();
		StringBuffer areaname = new StringBuffer("(");
		if(areaNum == 1){
			areaname.append(resourceBean.getArea_1());
		} else if(areaNum == 2){
			areaname.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2());
		} else if(areaNum == 3){
			areaname.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2()).append("、").append(resourceBean.getArea_3());
		}
		areaname.append(")");
		model.addAttribute("areaname", areaname);
		Record record = recordService.getRecordByPK(id);
		Personnel personnel = personnelService.getEntityByPK(record.getPersonnelid());
		Face faceid = faceidService.getEntityByPK(record.getFaceid());
		Gate gates = gatesService.getGatesByPK(record.getGatesid());
		AREA area = areaService.getAreaByPK(personnel.getAreacode());
		model.addAttribute("record", record);
		model.addAttribute("personnel", personnel);
		model.addAttribute("faceid", faceid);
		model.addAttribute("gates", gates);
		model.addAttribute("area", area);
		return 	"manage/record/personnel-record-info";
	}
	
	@RequestMapping(value = "/visitor-record-info.html")
	public String visitorRecordInfo(Model model,
			@RequestParam(value = "id",required = false)Integer id){
		Record record = recordService.getRecordByPK(id);
		Visitor visitor = visitorService.getVisitorByPK(record.getPersonnelid());
		Personnel personnel = personnelService.getEntityByPK(visitor.getPersonnel_id());
		Gate gates = gatesService.getGatesByPK(record.getGatesid());
//		AREA area = areaService.getAreaByPK(visitor.getAreacode());
		model.addAttribute("record", record);
		model.addAttribute("visitor", visitor);
		model.addAttribute("personnel", personnel);
		model.addAttribute("gates", gates);
//		model.addAttribute("area", area);
		return 	"manage/record/visitor-record-info";
	}
	
	@RequestMapping(value = "/system-record-notout.html")
	public String recordNotOutManage(
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "areacode", required = false) Integer areacode,
			@RequestParam(value = "personneltype", defaultValue = "1") Integer personneltype,
			@RequestParam(value = "time", required = false) String time,
			Model model,HttpSession session) {
		Integer areaNum = resourceBean.getAreaNum();
		StringBuffer area = new StringBuffer("(");
		if(areaNum == 1){
			area.append(resourceBean.getArea_1());
		} else if(areaNum == 2){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2());
		} else if(areaNum == 3){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2()).append("、").append(resourceBean.getArea_3());
		}
		area.append(")");
		model.addAttribute("area", area);
		model.addAttribute("areaList", areaService.getAreaList(null));
		Map<String, Object> conditions = new HashMap<String, Object>();
		//查询条件
		if(areacode != null){
			conditions.put("areacode",areacode);
		}
		if(StringUtils.isNoneBlank(time)){
			conditions.put("time",time);
		}
		if(personneltype != null){
			conditions.put("personneltype",personneltype);
		}
		Map<String, Object> pageMap = recordService.getNotOutRecordList(nowPage,conditions);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		return "manage/record/system-record-notout";
	}
}
