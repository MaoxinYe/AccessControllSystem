package cn.edu.zjut.acs.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;



import com.sun.jna.Pointer;


import cn.edu.zjut.acs.model.Face;
import cn.edu.zjut.acs.model.Personnel;
import cn.edu.zjut.acs.model.Visitor;
import cn.edu.zjut.acs.service.AreaService;
import cn.edu.zjut.acs.service.FaceService;
import cn.edu.zjut.acs.service.PersonnelService;
import cn.edu.zjut.acs.service.VisitorService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.support.ResourceBean;
import cn.edu.zjut.acs.util.DirsUtils;



@Controller
@RequestMapping("/manage")
public class VisitorController {

	@Resource
	private VisitorService visitorService;
	@Resource
	private PersonnelService personnelService;
	@Resource
	private ResourceBean resourceBean; 
	@Resource
	private AreaService areaService;
	@Resource
	private FaceService faceService;
	
	@RequestMapping(value = "/system-visitor-manage.html")
	public String visitorManage(
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "name", required = false ) String name,
			@RequestParam(value = "tel", required = false ) String tel,
			@RequestParam(value = "personnel_name", required = false ) String personnel_name,
			@RequestParam(value = "personnel_tel", required = false ) String personnel_tel,
			Model model,HttpSession session) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		//查询条件
		if(StringUtils.isNotBlank(name)){
			conditions.put("visitorName", name);
			conditions.put("name", name);
		}
		if(StringUtils.isNotBlank(tel)){
			conditions.put("tel",tel);
		}
		if(StringUtils.isNotBlank(personnel_name)){
			conditions.put("personnel_name",personnel_name);
		}
		if(StringUtils.isNotBlank(personnel_tel)){
			conditions.put("personnel_tel",personnel_tel);
		}
		conditions.put("effective",true);
		Map<String, Object> pageMap = visitorService.getVisitorList(nowPage,conditions);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		return "manage/visitor/system-visitor-manage";
	}
	
	@RequestMapping(value = "/system-visitor-add.html", method = RequestMethod.GET)
	public String visitorAdd(Model model,HttpSession session) {
		return "manage/visitor/system-visitor-add";
	}

	@RequestMapping(value = "/system-visitor-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn visitorAdd(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "time", required = false) String accesstime,
			
			@Valid Visitor visitor, BindingResult bindingresult) throws ParseException, IOException {
		if(bindingresult.hasFieldErrors()){
			StringBuilder sb = new StringBuilder();
			List<ObjectError> errors = bindingresult.getAllErrors();  
			for (ObjectError err : errors) {
				sb.append(err.getDefaultMessage());
			}
			return JSONReturn.buildFailure(sb.toString());
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		visitor.setAccesstime(sdf.parse(accesstime));
		/*if(StringUtils.isNotBlank(startTime)){
			visitor.setStarttime(new java.sql.Date(sdf.parse(startTime).getTime()));
		} else {
			visitor.setStarttime(new java.sql.Date(new Date().getTime()));
		}
		if(StringUtils.isNotBlank(endTime)){
			visitor.setEndtime(new java.sql.Date(sdf.parse(endTime).getTime()));
		} else {
			visitor.setEndtime(visitor.getStarttime());
		}*/
		//判断访客是否是正常人员
		map.clear();
		map.put("name",visitor.getName());
		map.put("tel",visitor.getTel());
		List<Personnel> personList = personnelService.getPersonnelList(map);
		if(personList != null && personList.size() > 0){
			return  JSONReturn.buildFailure("该人员是内部人员");
			/*Personnel person = personList.get(0);
			Date newDate = sdf.parse(sdf.format(new Date()));
			if(person.getType() == 1 && person.getExpirationdate() != null && person.getExpirationdate().getTime() > newDate.getTime()){
				return  JSONReturn.buildFailure("该人员是有效的正常人员");
			} else if(person.getType() == 3 && person.getExpirationdate() != null && person.getExpirationdate().getTime() > newDate.getTime()) {
				return  JSONReturn.buildFailure("该人员是有效的外聘人员");
			} else{
				return  JSONReturn.buildFailure("该人员是黑名单人员");
			}*/
		}
		//判断访客是否已有效存在
		map.clear();
		map.put("name",visitor.getName());
		map.put("tel",visitor.getTel());
		map.put("accesstime",accesstime);
		
		List<Visitor> visitorList = visitorService.getVisitorList(map);
		if(visitorList != null && visitorList.size() > 0){
			return  JSONReturn.buildFailure("在此访问时间内，该人员是已登记的访客");
			/*Visitor yw_visitor = visitorList.get(0);
			if(yw_visitor.getStatus() == 1){
				return  JSONReturn.buildFailure("在此访问时间内，该人员是已登记的访客");
			} else if(yw_visitor.getStatus() == 2){
				return  JSONReturn.buildFailure("在此访问时间内，该人员是已激活的访客");
			}*/
		} /*else {
			map.clear();
			map.put("name",visitor.getName());
			map.put("tel",visitor.getTel());
			map.put("accrsstime",visitor.getEndtime());
			visitorList = visitorService.getVisitorList(map);
			if(visitorList != null && visitorList.size() > 0){
				Visitor yw_visitor = visitorList.get(0);
				if(yw_visitor.getStatus() == 1){
					return  JSONReturn.buildFailure("在此访问时间内，该人员是已登记的访客");
				} else if(yw_visitor.getStatus() == 2){
					return  JSONReturn.buildFailure("在此访问时间内，该人员是已激活的访客");
				}
			}
		}*/
		//判断访问的对象是否是有效的
		map.clear();
		map.put("name", visitor.getPersonnel_name());
		map.put("tel", visitor.getPersonnel_tel());
		personList = personnelService.getPersonnelList(map);
		if(personList != null && personList.size() > 0){
			if(personList.size() > 1){
				return  JSONReturn.buildFailure("找到多个受访对象");
			}
			Personnel person = personList.get(0);
			//visitor.setAreacode(person.getArea().getAreacode());
			visitor.setPersonnel_id(person.getPersonnelid());
		} else {
			return  JSONReturn.buildFailure("未找到受访对象");
		}
		
		if (file.isEmpty()) {
			return JSONReturn.buildFailure("上传照片为空");
		}
		InputStream fInputStream = null;
		FileOutputStream fOutputStream = null;
		try {
			fInputStream = file.getInputStream();
			if(fInputStream.available() > 1024 * 1024){
				return JSONReturn.buildFailure("图片过大，请上传1M以下的图片");
			} else {
				String prefix = resourceBean.getPhysicalPath(); 
				String suffix = File.separator + "visitor"+ File.separator + DirsUtils.DirsString() + File.separator;
				new File(prefix + File.separator + "visitor"+ File.separator + DirsUtils.DirsString()).mkdirs();
				String fileName = UUID.randomUUID().toString() + ".jpg";
				String fullPath = prefix + suffix + fileName;
				fOutputStream = new FileOutputStream(new File(fullPath));
				byte[] by = new byte[1024];
				int len = 0;
				while((len = fInputStream.read(by)) != -1) {
					fOutputStream.write(by, 0, len);
				}
				//存照片地址
				visitor.setPath(suffix + fileName);
				
				/*// 提取FaceDetector和FaceVerifier
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
							faceMap.put("flag", 1);
							detector = (FaceDetector) faceMap.get("detector");
							verifier = (FaceVerifier) faceMap.get("verifier");
							break;
						}
					}
				}
				String feature_pic =null;
				BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
				// 如果FaceDetector和FaceVerifier不为空，则提取特征值
				if (detector != null && verifier != null) {
					try {
						feature_pic = faceAPI.getFeatureString(bufferedImage, verifier, detector);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						faceMap.put("flag", 0);
					}
				}
				//存特征值
*/				visitor.setFacefeature("临时的特征值");
				//添加访客
				//visitor.setStatus(1);
				this.visitorService.saveVisitor(visitor);
				
				//将照片特征值加入到内存中
				/*ServletContext servletContext = request.getServletContext();
				List<VisitorFaceFeatureDTO> visitorlist = (List<VisitorFaceFeatureDTO>) servletContext.getAttribute("visitorFaceFeature");
				FaceVerifier faceverifier = null;
				Pointer feature_pointer = null;
				synchronized (this) {
					while (it.hasNext()) {
						faceMap = (Map<String, Object>) it.next();
						Integer flag = (Integer) faceMap.get("flag");
						if (flag == 0) {
							faceMap.put("flag", 1);
							faceverifier = (FaceVerifier) faceMap.get("verifier");
							break;
						}
					}
				}*/
				// 如果FaceVerifier不为空
				/*if ( faceverifier != null) {
					try {
						feature_pointer = faceverifier.deserializeFeature(feature_pic);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						faceMap.put("flag", 0);
					}
				}
				VisitorFaceFeatureDTO dto = new VisitorFaceFeatureDTO();
				dto.setVisitorId(visitor.getVisitorid());
				dto.setFeature(feature_pointer);
				visitorlist.add(dto);
				servletContext.setAttribute("visitorFaceFeature", visitorlist);*/
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return JSONReturn.buildFailure("无法保存图片!");
		} catch (Exception e) {
			e.printStackTrace();
			return JSONReturn.buildFailure("保存失败");
		} finally {
			if(fInputStream != null) {
				fInputStream.close();
			}
			if(fOutputStream != null) {
				fOutputStream.close();
			}
		}
	}
	
	@RequestMapping(value = "/system-visitor-edit.html", method = RequestMethod.GET)
	public String visitorEdit(@RequestParam(value = "id", required = false)Integer id,
			Model model,HttpSession session) {
		if(id != null){
			Visitor visitor = visitorService.getVisitorByPK(id);
			model.addAttribute("visitor", visitor);
			/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			model.addAttribute("startTime", sdf.format(visitor.getStarttime()));
			model.addAttribute("endTime", sdf.format(visitor.getEndtime()));*/
		}
		return "manage/visitor/system-visitor-edit";
	}
	/*
	@RequestMapping(value = "/system-visitor-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn visitorEdit(HttpSession session, HttpServletRequest request,
			@RequestParam(value = "visitorid", required = false)Integer visitorid,
			@RequestParam(value = "sex", required = false)Integer sex,
			@RequestParam(value = "credentials", required = false ) String credentials,
			@RequestParam(value = "tel", required = false ) String tel,
			@RequestParam(value = "company", required = false ) String company,
			@RequestParam(value = "startTime", required = false ) String startTime,
			@RequestParam(value = "endTime", required = false ) String endTime,
			@RequestParam(value = "personnel_name", required = false ) String personnel_name,
			@RequestParam(value = "personnel_tel", required = false ) String personnel_tel,
			@RequestParam(value = "remarks", required = false ) String remarks,
			@RequestParam(value = "file", required = false) MultipartFile file) throws ParseException, IOException{
		if(visitorid != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Map<String,Object> map = new HashMap<String, Object>();
			boolean saveFlag = false;
			Visitor visitor = visitorService.getVisitorByPK(visitorid);
			if(sex != null && sex != visitor.getSex()){
				saveFlag = true;
				visitor.setSex(sex);
			}
			if(StringUtils.isNotBlank(credentials) && !credentials.equals(visitor.getCredentials())){
				saveFlag = true;
				visitor.setCredentials(credentials);
			}
			if(StringUtils.isNotBlank(tel) && !tel.equals(visitor.getTel())){
				saveFlag = true;
				visitor.setTel(tel);
			}
			if(StringUtils.isNotBlank(startTime) && !startTime.equals(sdf.format(visitor.getStarttime()))){
				saveFlag = true;
				visitor.setStarttime(new java.sql.Date(sdf.parse(startTime).getTime()));
			}
			if(StringUtils.isNotBlank(endTime) && !endTime.equals(sdf.format(visitor.getEndtime()))){
				saveFlag = true;
				visitor.setEndtime(new java.sql.Date(sdf.parse(endTime).getTime()));
			}
			if(StringUtils.isNotBlank(company) && !company.equals(visitor.getCompany())){
				saveFlag = true;
				visitor.setCompany(company);
			}
			if(StringUtils.isNotBlank(personnel_name) && !personnel_name.equals(visitor.getPersonnel_name())){
				saveFlag = true;
				visitor.setPersonnel_name(personnel_name);
			}
			if(StringUtils.isNotBlank(personnel_tel) && !personnel_tel.equals(visitor.getPersonnel_tel())){
				saveFlag = true;
				visitor.setPersonnel_tel(personnel_tel);
			}
			if(StringUtils.isNotBlank(remarks) && !remarks.equals(visitor.getRemarks())){
				saveFlag = true;
				visitor.setRemarks(remarks);
			}
			//判断访客是否是正常人员
			map.clear();
			map.put("name",visitor.getName());
			map.put("tel",visitor.getTel());
			List<Personnel> personList = personnelService.getPersonnelList(map);
			if(personList != null && personList.size() > 0){
				Personnel person = personList.get(0);
				Date newDate = sdf.parse(sdf.format(new Date()));
				if(person.getType() == 1 && person.getExpirationdate() != null && person.getExpirationdate().getTime() > newDate.getTime()){
					return  JSONReturn.buildFailure("该人员是有效的正常人员");
				} else if(person.getType() == 3 && person.getExpirationdate() != null && person.getExpirationdate().getTime() > newDate.getTime()) {
					return  JSONReturn.buildFailure("该人员是有效的外聘人员");
				} else{
					return  JSONReturn.buildFailure("该人员是黑名单人员");
				}
			}
			//判断访客是否已有效存在
			map.clear();
			map.put("name",visitor.getName());
			map.put("tel",visitor.getTel());
			map.put("accesstime",visitor.getStarttime());
			List<Visitor> visitorList = visitorService.getVisitorList(map);
			if(visitorList != null && visitorList.size() > 0){
				Visitor yw_visitor = visitorList.get(0);
				if(yw_visitor.getVisitorid() != visitor.getVisitorid()){
					if(yw_visitor.getStatus() == 1){
						return  JSONReturn.buildFailure("在此访问时间内，该人员是已登记的访客");
					} else if(yw_visitor.getStatus() == 2){
						return  JSONReturn.buildFailure("在此访问时间内，该人员是已激活的访客");
					}
				}
			}
			map.clear();
			map.put("name",visitor.getName());
			map.put("tel",visitor.getTel());
			map.put("accesstime",visitor.getEndtime());
			visitorList = visitorService.getVisitorList(map);
			if(visitorList != null && visitorList.size() > 0){
				Visitor yw_visitor = visitorList.get(0);
				if(yw_visitor.getVisitorid() != visitor.getVisitorid()){
					if(yw_visitor.getStatus() == 1){
						return  JSONReturn.buildFailure("在此访问时间内，该人员是已登记的访客");
					} else if(yw_visitor.getStatus() == 2){
						return  JSONReturn.buildFailure("在此访问时间内，该人员是已激活的访客");
					}
				}
			}
			//判断访问的对象是否是有效的
			map.clear();
			map.put("name", visitor.getPersonnel_name());
			map.put("tel", visitor.getPersonnel_tel());
			personList = personnelService.getPersonnelList(map);
			if(personList != null && personList.size() > 0){
				if(personList.size() > 1){
					return  JSONReturn.buildFailure("找到多个受访对象");
				}
				Personnel person = personList.get(0);
				//visitor.setAreacode(person.getArea().getAreacode());
				visitor.setPersonnel_id(person.getPersonnelid());
			} else {
				return  JSONReturn.buildFailure("未找到受访对象");
			}
			
			
			if (file != null && !file.isEmpty()) {
				saveFlag = true;
				InputStream fInputStream = null;
				FileOutputStream fOutputStream = null;
				try {
					fInputStream = file.getInputStream();
					if(fInputStream.available() > 1024 * 1024){
						return JSONReturn.buildFailure("图片过大，请上传1M以下的图片");
					} else {
						String prefix = resourceBean.getPhysicalPath(); 
						File oldPic = new File(prefix+visitor.getPath());
						if(oldPic.exists()){
							oldPic.delete();
						}
						String suffix = File.separator + "visitor"+ File.separator + DirsUtils.DirsString() + File.separator;
						new File(prefix + File.separator + "visitor"+ File.separator + DirsUtils.DirsString()).mkdirs();
						String fileName = UUID.randomUUID().toString() + ".jpg";
						String fullPath = prefix + suffix + fileName;
						fOutputStream = new FileOutputStream(new File(fullPath));
						byte[] by = new byte[1024];
						int len = 0;
						while((len = fInputStream.read(by)) != -1) {
							fOutputStream.write(by, 0, len);
						}
						//存照片地址
						visitor.setPath(suffix + fileName);
						
						// 提取FaceDetector和FaceVerifier
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
									faceMap.put("flag", 1);
									detector = (FaceDetector) faceMap.get("detector");
									verifier = (FaceVerifier) faceMap.get("verifier");
									break;
								}
							}
						}
						String feature_pic =null;
						BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
						// 如果FaceDetector和FaceVerifier不为空，则提取特征值
						if (detector != null && verifier != null) {
							try {
								feature_pic = faceAPI.getFeatureString(bufferedImage, verifier, detector);
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								faceMap.put("flag", 0);
							}
						}
						//存特征值
						visitor.setFacefeature(feature_pic);
						
						//将照片特征值加入到内存中
						ServletContext servletContext = request.getServletContext();
						FaceVerifier faceverifier = null;
						Pointer feature_pointer = null;
						synchronized (this) {
							while (it.hasNext()) {
								faceMap = (Map<String, Object>) it.next();
								Integer flag = (Integer) faceMap.get("flag");
								if (flag == 0) {
									faceMap.put("flag", 1);
									faceverifier = (FaceVerifier) faceMap.get("verifier");
									break;
								}
							}
						}
						// 如果FaceVerifier不为空
						if ( faceverifier != null) {
							try {
								feature_pointer = faceverifier.deserializeFeature(feature_pic);
							} catch (Exception e) {
								e.printStackTrace();
							} finally {
								faceMap.put("flag", 0);
							}
						}
						List<VisitorFaceFeatureDTO> visitorlist = (List<VisitorFaceFeatureDTO>) servletContext.getAttribute("visitorFaceFeature");
						List<VisitorFaceFeatureDTO> list = new ArrayList<VisitorFaceFeatureDTO>();
						VisitorFaceFeatureDTO dto = null;
						if (visitorlist != null && visitorlist.size() > 0) {
							Iterator<VisitorFaceFeatureDTO> dtoit = visitorlist.iterator();
							boolean flag = true;
							while (dtoit.hasNext()) {
								dto = dtoit.next();
								if (dto.getVisitorId().equals(visitor.getVisitorid())) {
									dto.setVisitorId(visitor.getVisitorid());
									if (feature_pointer != null) {
										dto.setFeature(feature_pointer);
									}
									list.add(dto);
								} else {
									list.add(dto);
								}
							}
						}
						servletContext.setAttribute("visitorFaceFeature", list);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return JSONReturn.buildFailure("无法保存图片!");
				} catch (Exception e) {
					e.printStackTrace();
					return JSONReturn.buildFailure("保存失败");
				} finally {
					if(fInputStream != null) {
						fInputStream.close();
					}
					if(fOutputStream != null) {
						fOutputStream.close();
					}
				}
			}
			if(saveFlag){
				//添加访客
				this.visitorService.updateVisitor(visitor);
			}
		}
		return JSONReturn.buildSuccessWithEmptyBody();
	}
	*/
	@RequestMapping(value = { "system-visitor-del" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn visitorDelete(@RequestParam(value = "id", required = false) Integer id,
			HttpSession session, HttpServletRequest request) {
		if (id != null) {
			Visitor visitor = visitorService.getVisitorByPK(id);
			if (visitor != null) {
				//visitor.setStatus(3);
				this.visitorService.updateVisitor(visitor);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-visitor-del" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONReturn visitorDelete(@RequestParam(value = "ids", required = false) Integer[] ids,
			HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			this.visitorService.t_update(ids);
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = { "system-visitor-show" }, method = { RequestMethod.GET })
	public String visitorShow(
			@RequestParam(value = "id", required = false) String id,
			HttpSession session, HttpServletRequest request, Model model) {
		Visitor visitor = visitorService.getVisitorByPK(Integer.valueOf(id));
		if (visitor != null) {
			if(visitor.getPersonnel_id() != null){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("personnelid", visitor.getPersonnel_id());
				List<Face> faceList = faceService.getFaceidList(map);
				Face face = null;
				if (faceList != null && faceList.size() > 0) {
					face = faceList.get(0);
				}
				model.addAttribute("face", face);
			}
			model.addAttribute("visitor", visitor);
		}
		return "manage/visitor/system-visitor-show";
		
	}
}
