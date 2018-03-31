package cn.edu.zjut.acs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
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

/*import cn.com.itsea.acs.dto.FaceFeatureDTO;*/
/*import cn.com.itsea.acs.model.YW_FACEID;*/
/*import cn.com.itsea.acs.model.YW_VISITOR;*/
/*import cn.com.itsea.acs.service.FaceidService;
import cn.com.itsea.acs.utils.AreaRecursion;*/
/*import cn.com.itsea.face.sdk.FaceAPI;
import cn.com.itsea.face.sdk.FaceDetector;
import cn.com.itsea.face.sdk.FaceVerifier;*/
import cn.edu.zjut.acs.model.AREA;
import cn.edu.zjut.acs.model.Gate;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.AreaService;
import cn.edu.zjut.acs.service.GatesService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.support.ResourceBean;
import cn.edu.zjut.acs.util.GetRemoteAddr;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
//import com.sun.jna.Pointer;



@Controller
@RequestMapping("/manage")
public class AreaController {
	
	@Resource
	private AreaService areaService;
	/*@Resource
	private FaceidService faceidService;*/
	/*@Resource
	private VisitorService visitorService;*/
	@Resource
	private GatesService gatesService;
	@Resource
	private ResourceBean resourceBean;
	
	@RequestMapping(value = "/system-area-manage.html")
	public String areaManage(Model model,
			@RequestParam(value = "flag", required = false) Integer flag){
		Integer areaNum = resourceBean.getAreaNum();
		if(areaNum >= flag){
			Map<String,Object> map = new HashMap<String, Object>();
			model.addAttribute("flag", flag);
			if(flag == 1){
				model.addAttribute("area_1", resourceBean.getArea_1());
				model.addAttribute("area", resourceBean.getArea_1());
				map.clear();
				map.put("level", 1);
				List<AREA> area1List = areaService.getAreaList(map);
				model.addAttribute("area1List", area1List);
			} else if(flag == 2){
				model.addAttribute("area_1", resourceBean.getArea_1());
				model.addAttribute("area_2", resourceBean.getArea_2());
				model.addAttribute("area", resourceBean.getArea_2());
				map.clear();
				map.put("level", 1);
				List<AREA> area1List = areaService.getAreaList(map);
				model.addAttribute("area1List", area1List);
				map.clear();
				map.put("level", 2);
				List<AREA> area2List = areaService.getAreaList(map);
				model.addAttribute("area2List", area2List);
			} else if(flag == 3){
				model.addAttribute("area_1", resourceBean.getArea_1());
				model.addAttribute("area_2", resourceBean.getArea_2());
				model.addAttribute("area_3", resourceBean.getArea_3());
				model.addAttribute("area", resourceBean.getArea_3());
				map.clear();
				map.put("level", 1);
				List<AREA> area1List = areaService.getAreaList(map);
				model.addAttribute("area1List", area1List);
				map.clear();
				map.put("level", 2);
				List<AREA> area2List = areaService.getAreaList(map);
				model.addAttribute("area2List", area2List);
				map.clear();
				map.put("level", 3);
				List<AREA> area3List = areaService.getAreaList(map);
				model.addAttribute("area3List", area3List);
			}
			return "manage/area/system-area-manage";
		}
		return null;
	}
	
	@RequestMapping(value = "/system-area-add.html", method = RequestMethod.GET)
	public String areaAdd(Model model,
			@RequestParam(value = "flag", required = false) Integer flag) {
		model.addAttribute("flag", flag);
		Map<String,Object> conditions = new HashMap<String, Object>();
		if(flag == 1){
			model.addAttribute("area", resourceBean.getArea_1());
		} else if(flag == 2){
			model.addAttribute("area_1", resourceBean.getArea_1());
			model.addAttribute("area", resourceBean.getArea_2());
			conditions.clear();
			conditions.put("level", 1);
			List<AREA> areaList = areaService.getAreaList(conditions);
			model.addAttribute("areaList", areaList);
		} else if(flag == 3){
			model.addAttribute("area_1", resourceBean.getArea_2());
			model.addAttribute("area", resourceBean.getArea_3());
			conditions.clear();
			conditions.put("level", 2);
			List<AREA> areaList = areaService.getAreaList(null);
			model.addAttribute("areaList", areaList);
		}
		return "manage/area/system-area-add";
	}
	
	/*@RequestMapping(value = "/system-area-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn areaAdd(HttpSession session, HttpServletRequest request,
			@Valid AREA area,  BindingResult bingingresult) {
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
		log.setContent("添加防区:" + area.getAreaname());
		log.setClientip(ipaddress);
		//添加角色类型
		this.areaService.t_insert(area, log);
		ServletContext sct = request.getServletContext();
		FaceAPI faceAPI = (FaceAPI)sct.getAttribute("faceAPI");
		//获取现行版本号
		String faceVersion = faceAPI.getVersion();
		List<Object> facePool = (List<Object>) sct.getAttribute("faceList");
		Iterator<Object> it = facePool.iterator();
		FaceDetector detector = null;
		FaceVerifier verifier = null;
		Map<String, Object> faceMap = null;
		synchronized (this) {
			while (it.hasNext()) {
				faceMap = (Map<String, Object>) it.next();
				Integer flag = (Integer) faceMap.get("flag");
				if (flag == 0) {
					detector = (FaceDetector) faceMap.get("detector");
					verifier = (FaceVerifier) faceMap.get("verifier");
					faceMap.put("flag", 1);
					break;
				}
			}
		}
		Map<Integer,List<FaceFeatureDTO>> faceMaps = (Map<Integer,List<FaceFeatureDTO>>)sct.getAttribute("faceFeature");
		List<AREA> recursionList = Lists.newArrayList();
		recursionList.add(area);
		List<FaceFeatureDTO> facelist = Lists.newArrayList();
		//得到该区域以及下属区域的合集(针对正常人员，外聘人员)
		AreaRecursion.areaRecursionUp(area.getSupercode(),recursionList,areaService);
		AreaRecursion.areaRecursionDown(area.getAreacode(),recursionList,areaService);
		Iterator<AREA> recursionIt = recursionList.iterator();
		Map<String, Object> map = new HashMap<String, Object>();
		while(recursionIt.hasNext()){
			AREA recursionArea = recursionIt.next();
			//获取该区域的所有有效人员（正常人员，外聘人员），并存入List集合中
			map.put("areacode", recursionArea.getAreacode());
			map.put("status", "1");
			map.put("effectivePerson", true);
			List<YW_FACEID> faceidlList =  faceidService.findFaceidList(map);
			Iterator<YW_FACEID> faceidIt = faceidlList.iterator();
			while(faceidIt.hasNext()){
				YW_FACEID face = faceidIt.next();
				if (face != null && face.getPersonnel() != null) {
					Integer faceid = face.getFaceid();
					String feature = face.getFacefeature();
					if (!Strings.isNullOrEmpty(feature)) {
						Pointer feature_pointer = null;
						if (!Strings.isNullOrEmpty(feature)) {
							feature_pointer = verifier.deserializeFeature(feature);
						}
						FaceFeatureDTO dto = new FaceFeatureDTO();
						dto.setId(faceid);
						dto.setAreacode(face.getPersonnel().getAreacode());
						dto.setSource(1);
						dto.setFeature(feature_pointer);
						facelist.add(dto);
					}
				}
			}
			// 获取所有有效的激活访客
			map.clear();
			map.put("accrsstime", new Date());
			map.put("areacode", recursionArea.getAreacode());
			map.put("status", 2);
			List<YW_VISITOR> visitorList = visitorService.getVisitorList(map);
			if (visitorList != null && visitorList.size() > 0) {
				Iterator<YW_VISITOR> visitorIt = visitorList.iterator();
				while (it.hasNext()) {
					YW_VISITOR visitor = visitorIt.next();
					if (visitor != null) {
						Integer visitorid = visitor.getVisitorid();
						String feature = visitor.getFacefeature();
						if (!Strings.isNullOrEmpty(feature)) {
							Pointer feature_pointer = null;
							if (!Strings.isNullOrEmpty(feature)) {
								feature_pointer = verifier.deserializeFeature(feature);
							}
							FaceFeatureDTO dto = new FaceFeatureDTO();
							dto.setId(visitorid);
							dto.setAreacode(visitor.getAreacode());
							dto.setSource(2);
							dto.setFeature(feature_pointer);
							facelist.add(dto);
						}
					}
				}
			}
		}
		faceMaps.put(area.getAreacode(), facelist);
		sct.setAttribute("faceFeature", faceMaps);
		return JSONReturn.buildSuccessWithEmptyBody();
	}
	*/
	@RequestMapping(value = "/check-areaname.html", method = RequestMethod.GET)
	public String checkRoleName(HttpServletResponse response,
			@RequestParam(value = "areaname", required = false) String areaname)
					throws Exception {
		response.setCharacterEncoding("utf-8");
		if (StringUtils.isNotBlank(areaname)) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("areaname", areaname);
			List<AREA> list = this.areaService.getAreaList(map);
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
	
	@RequestMapping(value = "/system-area-edit.html", method = RequestMethod.GET)
	public String systemroleEdit(Model model,
			@RequestParam(value = "areacode", required = false) Integer areacode,
			@RequestParam(value = "flag", required = false) Integer flag ) {
		if (areacode != null) {
			model.addAttribute("flag", flag);
			Map<String,Object> conditions = new HashMap<String, Object>();
			AREA yw_area = areaService.getAreaByPK(areacode);
			model.addAttribute("yw_area", yw_area);
			if(flag == 1){
				model.addAttribute("area", resourceBean.getArea_1());
			} else if(flag == 2){
				model.addAttribute("area_1", resourceBean.getArea_1());
				model.addAttribute("area", resourceBean.getArea_2());
				conditions.clear();
				conditions.put("level", 1);
				List<AREA> areaList = areaService.getAreaList(conditions);
				model.addAttribute("areaList", areaList);
			} else if(flag == 3){
				model.addAttribute("area_1", resourceBean.getArea_2());
				model.addAttribute("area", resourceBean.getArea_3());
				conditions.clear();
				conditions.put("level", 2);
				List<AREA> areaList = areaService.getAreaList(null);
				model.addAttribute("areaList", areaList);
			}
		}
		return "manage/area/system-area-edit";
	}
	
	@RequestMapping(value = "/system-area-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn systemroleEdit(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "areacode", required = false) Integer areacode,
			@RequestParam(value = "areaname", required = false) String areaname,
			@RequestParam(value = "supercode", required = false) Integer supercode) {
		if (areacode != null) {
			AREA area = areaService.getAreaByPK(areacode);
			boolean flag = false;
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			StringBuffer content = new StringBuffer("修改了用户信息");
				//修改角色类型名称
			if(StringUtils.isNotBlank(areaname) && !areaname.equals(area.getAreaname())){
				flag = true;
				content.append("；将防区名由--").append(area.getAreaname()).append("--修改为--").append(areaname);
				area.setAreaname(areaname);
			}
			if(supercode != null && supercode.intValue() != area.getSupercode().intValue()){
				flag = true;
				AREA area1 = areaService.getAreaByPK(area.getSupercode());
				AREA area2 = areaService.getAreaByPK(supercode);
				if(area1 != null && area2 != null){
					content.append("；将上级防区由--").append(area1.getAreaname()).append("--修改为--").append(area2.getAreaname());
				} else if (area1 != null && area2 == null){
					content.append("；将上级防区由--").append(area1.getAreaname()).append("--修改为无上级防区--");
				} else if (area1 == null && area2 != null){
					content.append("；将上级防区由无上级防区--").append("--修改为--").append(area2.getAreaname());
				}
				area.setSupercode(supercode);
			}
			if(flag){
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent(content.toString());
				log.setClientip(ipaddress);
				//修改防区
				this.areaService.t_update(area, log);
			}
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-area-del" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn systemareaDelete(
			@RequestParam(value = "areacode", required = false) Integer areacode,
			HttpSession session, HttpServletRequest request) {
		if (areacode != null) {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			AREA area = areaService.getAreaByPK(areacode);
			if (area != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("areacode", area.getAreacode());
				List<Gate> gatesList = gatesService.getGatesList(map);
				if(gatesList != null && gatesList.size() > 0){
					return JSONReturn.buildFailure("请先删除该区域下绑定的门禁闸机!");
				} else {
					map.clear();
					map.put("superCode", areacode);
					List<AREA> areaList=areaService.getAreaList(map);
					if(areaList!=null && areaList.size()>0)
					{
						return JSONReturn.buildFailure("请先删除该区域下面的子区域");
					}
					else
					{
						ServletContext sct = request.getServletContext();
						/*Map<Integer,List<FaceFeatureDTO>> faceMaps = (Map<Integer,List<FaceFeatureDTO>>)sct.getAttribute("faceFeature");
						faceMaps.remove(area.getAreacode());
						sct.setAttribute("faceFeature", faceMaps);*/
						// 记录日志
						XT_LOG log = new XT_LOG();
						log.setUsername(session_loginname);
						log.setContent("将防区:" + area.getAreaname() + "删除了:");
						log.setClientip(ipaddress);
						this.areaService.t_delete(area, log);
						return JSONReturn.buildSuccessWithEmptyBody();
					}
				}
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-area-del" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONReturn systemareaDelete(
			@RequestParam(value = "ids", required = false) Integer[] ids,
			HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			ServletContext sct = request.getServletContext();
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			List<AREA> areaList = areaService.t_delete(ids,ipaddress,session_loginname,sct);
			if(areaList != null && areaList.size() > 0){
				StringBuffer sb = new StringBuffer("区域：");
				for (AREA area : areaList) {
					sb.append(area.getAreaname() + "、");
				}
				return JSONReturn.buildFailure(sb.toString().substring(0, sb.length() - 1) + "删除失败；请先删除区域下绑定的门禁闸机!");
			} else {
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
}
