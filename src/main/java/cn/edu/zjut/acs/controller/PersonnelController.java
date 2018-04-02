package cn.edu.zjut.acs.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sun.jna.Pointer;

import cn.edu.zjut.acs.model.AREA;
import cn.edu.zjut.acs.model.Face;
import cn.edu.zjut.acs.model.Personnel;
import cn.edu.zjut.acs.model.Visitor;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.AreaService;
import cn.edu.zjut.acs.service.FaceService;
import cn.edu.zjut.acs.service.PersonnelService;
import cn.edu.zjut.acs.support.JSONReturn;
import cn.edu.zjut.acs.support.ResourceBean;
import cn.edu.zjut.acs.util.DirsUtils;
import cn.edu.zjut.acs.util.GetRemoteAddr;

@Controller
@RequestMapping("/manage")
public class PersonnelController {
	
	@Resource
	private PersonnelService personnelService;
	/*@Resource
	private PersonnelTypeService personnelTypeService;*/
	@Resource
	private FaceService faceService;
	@Resource
	private AreaService areaService;
	@Resource
	private ResourceBean resourceBean;
	
	@RequestMapping(value = "/personnel-manage.html")
	public String personnelManage(
			@RequestParam(value = "nowPage",defaultValue = "1")int nowPage,
			@RequestParam(value = "name", required = false ) String name,
			@RequestParam(value = "credentials", required = false ) String credentials,
			@RequestParam(value = "areacode", required = false ) String areacode,
			Model model,HttpServletRequest request,HttpSession session){
		Map<String, Object> conditions = new HashMap<String, Object>();
		Integer areaNum = resourceBean.getAreaNum();
		StringBuffer area = new StringBuffer("(");
		if(areaNum == 1){
			area.append(resourceBean.getArea_1());
		} else if(areaNum == 2){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2());
		} else if(areaNum == 3){
			area.append(resourceBean.getArea_1()).append("、").append(resourceBean.getArea_2()).append("、").append(resourceBean.getArea_3());
		}
		model.addAttribute("areaNum", areaNum);
		area.append(")");
		model.addAttribute("area", area);
		conditions.clear();
		//查询条件
		if( !Strings.isNullOrEmpty(name) ){
			conditions.put("name", name.trim());
		}
		if( !Strings.isNullOrEmpty(credentials) ){
			conditions.put("credentials", credentials.trim());
		}
		if( !Strings.isNullOrEmpty(areacode) ){
			conditions.put("areacode", areacode.trim());
		}
		Map<String, Object> pageMap = personnelService.getPersonnelPageList(conditions,nowPage);
		List<AREA> areaList = areaService.getAreaList(null);
		model.addAttribute("areaList", areaList);
		model.addAttribute("pageMap", pageMap);
		model.addAttribute("conditions", conditions);
		//model.addAttribute("personnelTypeList", personnelTypeService.getPersonnelTypeList(null));
		//model.addAttribute("areaList", areaService.getAreaList(null));
		return "manage/personnel/personnel-manage";
	}
	
	@RequestMapping(value = "/personnel-modeling.html", method = RequestMethod.GET)
	public String jzModeling(Model model) {
		return "manage/personnel/personnel-modeling";
	}
	
	@RequestMapping(value = "/personnel-add.html", method = RequestMethod.GET)
	public String personnelAdd(Model model) {
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
		List<AREA> areaList = areaService.getAreaList(null);
		model.addAttribute("areaList", areaList);
		//model.addAttribute("personnelTypeList", personnelTypeService.getPersonnelTypeList(null));
		return "manage/personnel/personnel-add";
	}
	
	@RequestMapping(value = "/personnel-add", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn personnelAdd(HttpSession session, HttpServletRequest request,
			@Valid Personnel personnel, BindingResult bingingresult,
			@RequestParam(value = "file", required = false) MultipartFile file,
			RedirectAttributes redirectAttributes){
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
		//照片1不能为空
		if ((file == null || file.isEmpty())) {
			return JSONReturn.buildFailure("照片不能为空，请拍摄或选择照片！");
		}
		//不能有重复的数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", personnel.getName());
		map.put("tel", personnel.getTel());
		List<Personnel> personnelsList = personnelService.getPersonnelList(map);
		if (personnelsList != null && personnelsList.size()>0) {
			return JSONReturn.buildFailure("数据已存在，请核对后添加！");
		}
		
		
		if ((file!=null&&!file.isEmpty())) {
			BufferedImage bufferedImage = null;
			String feature_pic = "这是临时的特征值";
			try {
				long filesize = 1024 * 1024;
				if(file.getSize()>filesize){
					return JSONReturn.buildFailure("照片选择的图片过大，请选择1M以下的图片！");
				}
				bufferedImage = ImageIO.read(file.getInputStream());
				if(bufferedImage == null){
					return JSONReturn.buildFailure("照片选择的文件不是图片，请选择1M以下的图片！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*// 提取FaceDetector和FaceVerifier
			ServletContext sct = request.getServletContext();
			FaceAPI faceAPI = (FaceAPI) sct.getAttribute("faceAPI");
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
			// 如果FaceDetector和FaceVerifier不为空，则提取特征值
			if (detector != null && verifier != null) {
				try {
					if (bufferedImage != null) {
						feature_pic = faceAPI.getFeatureString(bufferedImage,verifier, detector);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					faceMap.put("flag", 0);
				}
			}*/
			
			
			/*if (bufferedImage != null && Strings.isNullOrEmpty(feature_pic)) {
				// 无法提取特征值
				return JSONReturn.buildFailure("照片无法识别到人脸，请重新选择或拍摄照片！");
			}*/
			if (!Strings.isNullOrEmpty(feature_pic)) {
				// 存储上传文件文件
				String webInfoPath = resourceBean.getPhysicalPath();
				String facesearchPath = File.separator + "personPhoto"
						+ File.separator + DirsUtils.DirsString()
						+ File.separator + personnel.getCredentials()
						+ File.separator;
				new File(webInfoPath + facesearchPath).mkdirs();
				String picname = UUID.randomUUID().toString() + ".jpg";
				File picfile = new File(webInfoPath + facesearchPath + picname);
				try {
					file.transferTo(picfile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//保存人脸信息
				Face face = new Face();
				face.setPath(facesearchPath + picname);
				face.setFacefeature(feature_pic);
				face.setAddtime(new Date());
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("添加人员:" + personnel.getName() + "--" + personnel.getTel());
				log.setClientip(ipaddress);
				log.setAddtime(new Date());
				//保存数据,默认为状态有效
				//personnel.setStatus(1);
				personnelService.t_insert(personnel, face, log);
				/*//数据保存成功后将有效的特征值写入内存
				if(!Strings.isNullOrEmpty(face.getFacefeature())){
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
							if(!Strings.isNullOrEmpty(face.getFacefeature())){
								feature_pointer = faceverifier.deserializeFeature(face.getFacefeature());
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							faceMap.put("flag", 0);
						}
					}
					//保安人员存入保安人脸集合，普通人员存入map
					if (personnel.getType()==2) {
						List<GuardFaceFeatureDTO> guardFacelist = (List<GuardFaceFeatureDTO>) servletContext.getAttribute("guardFaceFeature");
						if (feature_pointer != null) {
							GuardFaceFeatureDTO dto = new GuardFaceFeatureDTO();
							dto.setGuardid(face.getFaceid());
							dto.setFeature(feature_pointer);
							guardFacelist.add(dto);
						}
						servletContext.setAttribute("guardFaceFeature", guardFacelist);
					} else {
						//新增内存到普通人员中
						addServletContext(servletContext, feature_pointer, personnel, face);
					}
				}*/
			}
			return JSONReturn.buildSuccessWithEmptyBody();
		} else {
			return JSONReturn.buildFailure("上传照片为空，请拍摄或选择上传照片！");
		}
	}
	@RequestMapping(value = { "personnel-show.html" }, method = { RequestMethod.GET })
	public String personnelShow(
			@RequestParam(value = "id", required = false) String id,
			HttpSession session, HttpServletRequest request, Model model) {
		Personnel personnel = personnelService.getEntityByPK(Integer.valueOf(id));
		if (personnel != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("personnelid", personnel.getPersonnelid());
			List<Face> faceList = faceService.getFaceidList(map);
			Face face = null;
			if (faceList != null && faceList.size() > 0) {
				face = faceList.get(0);
			}
			model.addAttribute("face", face);
			model.addAttribute("personnel", personnel);
		}
		return "manage/personnel/personnel-show";
		
	}
	@RequestMapping(value = "/personnel-edit.html", method = RequestMethod.GET)
	public String personnelEdit(@RequestParam(value = "id", required = false) Integer id,Model model) {
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
		Personnel personnel = personnelService.getEntityByPK(id);
		List<AREA> areaList = areaService.getAreaList(null);
		model.addAttribute("areaList", areaList);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("personnelid", personnel.getPersonnelid());
		List<Face> faceList=faceService.getFaceidList(map);
		if(faceList!=null && faceList.size()>0)
		{
			Face face=faceList.get(0);
			model.addAttribute("face", face);
		}
		if(personnel != null){
			model.addAttribute("personnel", personnel);
		}
		return "manage/personnel/personnel-edit";
	}
	
	@RequestMapping(value = "/personnel-edit", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn personnelEdit(HttpSession session, HttpServletRequest request,
			@Valid Personnel personnel, BindingResult bingingresult,
			@RequestParam(value = "file", required = false) MultipartFile file,
			RedirectAttributes redirectAttributes){
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
		if (personnel!=null) {
			Personnel entity = personnelService.getEntityByPK(personnel.getPersonnelid());
			if (entity != null) {
				//保存原人脸信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("personnelid", personnel.getPersonnelid());
				List<Face> faceList = faceService.getFaceidList(map);
 				Face face = faceList.get(0);
 				String oldPath = "";
 				Integer oldAreacode = entity.getAreacode();
				//如有修改姓名和电话则校验不能有重复的数据
				if (!entity.getName().equalsIgnoreCase(personnel.getName())
						|| !entity.getTel().equalsIgnoreCase(personnel.getTel())) {
					map.clear();
					map.put("name", personnel.getName());
					map.put("tel", personnel.getTel());
					List<Personnel> personnelsList = personnelService.getPersonnelList(map);
					if (personnelsList != null && personnelsList.size()>0) {
						return JSONReturn.buildFailure("数据已存在，请核对后添加！");
					}
				}
				//如证件号改变则校验证件号是否重复
				if (!personnel.getCredentials().equalsIgnoreCase(entity.getCredentials())) {
					map.clear();
					map.put("credentials", personnel.getCredentials());
					List<Personnel> pList = this.personnelService.getPersonnelList(map);
					if (pList != null && pList.size()>0) {
						return JSONReturn.buildFailure("该证件号已存在，请重新输入！");
					}else {
						entity.setCredentials(personnel.getCredentials());
					}
				}
				BufferedImage bufferedImage = null;
				String feature_pic = "这是临时的特征值";
				try {
					if(file!=null&&!file.isEmpty()){
						long filesize = 1024 * 1024;
						if(file.getSize()>filesize){
							return JSONReturn.buildFailure("照片选择的图片过大，请选择1M以下的图片！");
						}
						bufferedImage = ImageIO.read(file.getInputStream());
						if(bufferedImage == null){
							return JSONReturn.buildFailure("照片选择的文件不是图片，请选择1M以下的图片！");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				/*// 提取FaceDetector和FaceVerifier
				ServletContext sct = request.getServletContext();
				FaceAPI faceAPI = (FaceAPI) sct.getAttribute("faceAPI");
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
					// 如果FaceDetector和FaceVerifier不为空，则提取特征值
				if (detector != null && verifier != null) {
					try {
						if (bufferedImage != null) {
							feature_pic = faceAPI.getFeatureString(bufferedImage, verifier, detector);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						faceMap.put("flag", 0);
					}
				}
				
				if (bufferedImage != null && Strings.isNullOrEmpty(feature_pic)) {
						// 无法提取特征值则删除图片，并转发至原始页面，请求重新上传照片，可以提取特征值则进行添加操作
						return JSONReturn.buildFailure("照片无法识别到人脸，请重新选择或拍摄照片！");
				}
				*/
				if(!Strings.isNullOrEmpty(feature_pic)) {
					// 存储上传文件文件
					String webInfoPath = resourceBean.getPhysicalPath();
					String facesearchPath = File.separator + "personPhoto"
							+ File.separator + DirsUtils.DirsString()
							+ File.separator + personnel.getTel()
							+ File.separator;
					new File(webInfoPath + facesearchPath).mkdirs();
					String picname = UUID.randomUUID().toString() + ".jpg";
					File picfile = new File(webInfoPath + facesearchPath + picname);
					if (file != null && !file.isEmpty()) {
						try {
							file.transferTo(picfile);
							//保存原照片路径
							oldPath = face.getPath();
							//更新人脸信息
							face.setPath(facesearchPath + picname);
							face.setFacefeature(feature_pic);
							face.setAddtime(new Date());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
				//修改人员信息
				entity.setAddress(personnel.getAddress());
				entity.setAreacode(personnel.getAreacode());
				entity.setTel(personnel.getTel());
				entity.setName(personnel.getName());
				entity.setRemarks(personnel.getRemarks());
				entity.setSex(personnel.getSex());
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("更新人员:" + personnel.getName() + "--" + personnel.getTel());
				log.setClientip(ipaddress);
				log.setAddtime(new Date());
				personnelService.t_update(entity, face, oldPath, log, resourceBean.getPhysicalPath());
				//更新完成后更新人脸内存
				/*//数据保存成功后将有效的特征值写入内存
				if(!Strings.isNullOrEmpty(face.getFacefeature())){
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
							if(!Strings.isNullOrEmpty(face.getFacefeature())){
								feature_pointer = faceverifier.deserializeFeature(face.getFacefeature());
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							faceMap.put("flag", 0);
						}
					}
					//人员类型是否发生改变
					if (personnel.getType()!=oldType) {
						//改变为保安时
						if (personnel.getType()==2) {
							//由普通人员改变为保安则清除此人原区域代码在正常人员内存中的人脸信息
							clearServletContext(servletContext, face, oldAreacode);
							if (feature_pointer!=null) {
								//新增内存到保安中
								List<GuardFaceFeatureDTO> guardFacelist = (List<GuardFaceFeatureDTO>) servletContext.getAttribute("guardFaceFeature");
								GuardFaceFeatureDTO dto = new GuardFaceFeatureDTO();
							    dto.setGuardid(face.getFaceid());
							    dto.setFeature(feature_pointer);
							    guardFacelist.add(dto);
							    servletContext.setAttribute("guardFaceFeature", guardFacelist);
							} 
						} else {
						if (oldType==2) {
							//由保安改变为普通人员则清除此人在保安内存中的人脸信息
							List<GuardFaceFeatureDTO> guardFacelist = (List<GuardFaceFeatureDTO>) servletContext.getAttribute("guardFaceFeature");
							List<GuardFaceFeatureDTO> list = new ArrayList<GuardFaceFeatureDTO>();
							Iterator<GuardFaceFeatureDTO> guardIt = guardFacelist.iterator();
							while (guardIt.hasNext()) {
								GuardFaceFeatureDTO dto = guardIt.next();
								if (face.getFaceid()==dto.getGuardid()) {
									continue;
								} else {
									list.add(dto);
								}
							}
							servletContext.setAttribute("guardFaceFeature", list);
						} else {
							//普通人员类型相互转变则清除此人原区域代码在正常人员内存中的人脸信息
							clearServletContext(servletContext, face, oldAreacode);
						}
							if (feature_pointer!=null) {
								//新增内存到普通人员中
								addServletContext(servletContext, feature_pointer, personnel, face);
							}
						}
					} else {
						//人员类型未发生改变
						//照片更新过
						if (oldType==2) {
							if (StringUtils.isNotBlank(feature_pic)) {
								//保安人员更新保安人脸集合，普通人员存入map
								List<GuardFaceFeatureDTO> guardFacelist = (List<GuardFaceFeatureDTO>) servletContext.getAttribute("guardFaceFeature");
								List<GuardFaceFeatureDTO> list = new ArrayList<GuardFaceFeatureDTO>();
								GuardFaceFeatureDTO dto = null;
								if (guardFacelist != null && guardFacelist.size() > 0) {
									Iterator<GuardFaceFeatureDTO> guardIt = guardFacelist.iterator();
									boolean flag = true;
									while (guardIt.hasNext()) {
										dto = guardIt.next();
										//当前人员更新，否则直接插入
										if (dto.getGuardid()==face.getFaceid()) {
											flag = false;
											if (feature_pointer != null) {
												dto.setFeature(feature_pointer);
												list.add(dto);
											}
										} else {
											list.add(dto);
										}
									}
									if (flag) {
										dto = new GuardFaceFeatureDTO();
										dto.setGuardid(face.getFaceid());
										dto.setFeature(feature_pointer);
										list.add(dto);
									}
								}
								servletContext.setAttribute("guardFaceFeature", list);
							}
						} else {
							//普通人员类型则清除此人原区域代码在正常人员内存中的人脸信息
							clearServletContext(servletContext, face, oldAreacode);
							//新增内存到普通人员中
							addServletContext(servletContext, feature_pointer, personnel, face);
						}
					}
				}*/
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	@RequestMapping(value = "/check-credentials.html", method = RequestMethod.GET)
	public String checkJzzjh(HttpServletResponse response,
			@RequestParam(value = "credentials", required = false) String credentials)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("credentials", credentials);
		List<Personnel> entity = this.personnelService.getPersonnelList(map);
		if (entity != null && entity.size()>0) {
			response.getWriter().println("false");
		} else {
			response.getWriter().println("true");
		}
		return null;
	}
	
	/*@RequestMapping(value = { "personnel-del" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn personnelDelete(
			@RequestParam(value = "id", required = false) String id,
			HttpSession session, HttpServletRequest request) {
		if (!Strings.isNullOrEmpty(id)) {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			Personnel entity = this.personnelService.getEntityByPK(Integer.valueOf(id));
			if (entity != null) {
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("删除人员:" + entity.getName() + "--" + entity.getTel());
				log.setClientip(ipaddress);
				log.setAddtime(new Date());
				//删除人员信息以及人脸照片信息
				String physicalpath = resourceBean.getPhysicalPath();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("personnelid", id);
				List<Face> faceList = faceService.getFaceidList(map);
				this.personnelService.t_delete(entity, faceList, log, physicalpath);
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}*/
	
	@RequestMapping(value = { "personnel-return" }, method = { RequestMethod.GET })
	@ResponseBody
	public JSONReturn personnelReturn(
			@RequestParam(value = "id", required = false) String id,
			HttpSession session, HttpServletRequest request) {
		if (!Strings.isNullOrEmpty(id)) {
			String ipaddress = GetRemoteAddr.getIpAddr(request);
			String session_loginname = (String) session.getAttribute("session_loginname");
			Personnel entity = this.personnelService.getEntityByPK(Integer.valueOf(id));
			if (entity != null) {
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("恢复人员状态:" + entity.getName() + "--" + entity.getTel());
				log.setClientip(ipaddress);
				log.setAddtime(new Date());
				//恢复为有效人员信息并将人脸信息写入内存
				//entity.setStatus(1);
				this.personnelService.updatePersonnel(entity);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("personnelid", entity.getPersonnelid());
				/*List<Face> faceList = faceidService.getFaceidList(map);
				Face face = null;
				if (faceList!=null && faceList.size()>0) {
					face = faceList.get(0);
				}
				if (face!=null) {
					// 提取FaceDetector和FaceVerifier
					ServletContext sct = request.getServletContext();
					FaceAPI faceAPI = (FaceAPI) sct.getAttribute("faceAPI");
					List<Object> facePool = (List<Object>) sct.getAttribute("faceList");
					Iterator<Object> it = facePool.iterator();
					Map<String, Object> faceMap = null;
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
							if(!Strings.isNullOrEmpty(face.getFacefeature())){
								feature_pointer = faceverifier.deserializeFeature(face.getFacefeature());
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							faceMap.put("flag", 0);
						}
					}
					if (entity.getType()==2) {
						//保安将人脸信息写入保安内存
						List<GuardFaceFeatureDTO> guardFacelist = (List<GuardFaceFeatureDTO>) servletContext.getAttribute("guardFaceFeature");
						List<GuardFaceFeatureDTO> list = new ArrayList<GuardFaceFeatureDTO>();
						Iterator<GuardFaceFeatureDTO> guardIt = guardFacelist.iterator();
						boolean flag = true;
						GuardFaceFeatureDTO dto = null;
						while (guardIt.hasNext()) {
							dto = guardIt.next();
							if (face.getFaceid()==dto.getGuardid()) {
								flag = false;
								list.add(dto);
							} else {
								list.add(dto);
							}
						}
						if (flag) {
							dto = new GuardFaceFeatureDTO();
							dto.setGuardid(face.getFaceid());
							dto.setFeature(feature_pointer);
							list.add(dto);
						}
						servletContext.setAttribute("guardFaceFeature", list);
					} else {
						//普通人员人脸信息写入map中
						//普通人员类型相互转变则清除此人原区域代码在正常人员内存中的人脸信息
						clearServletContext(servletContext, face, entity.getAreacode());
						//重写内存
						addServletContext(servletContext, feature_pointer, entity, face);
					}
				}*/
				return JSONReturn.buildSuccessWithEmptyBody();
			}
		}
		return JSONReturn.buildFailure("数据错误!");
	}
	
	/*@RequestMapping(value = { "personnel-del" }, method = { RequestMethod.POST })
	@ResponseBody
	public JSONReturn personnelDelete(
			@RequestParam(value = "ids", required = false) String[] ids,
			HttpSession session, HttpServletRequest request) {
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				String id = ids[i];
				if (!Strings.isNullOrEmpty(id)) {
					String ipaddress = GetRemoteAddr.getIpAddr(request);
					String session_loginname = (String) session.getAttribute("session_loginname");
					Personnel entity = this.personnelService.getEntityByPK(Integer.valueOf(id));
					if (entity != null) {
						// 记录日志
						XT_LOG log = new XT_LOG();
						log.setUsername(session_loginname);
						log.setContent("删除人员:" + entity.getName() + "--" + entity.getTel());
						log.setClientip(ipaddress);
						log.setAddtime(new Date());
						//删除人员信息以及人脸照片信息
						String physicalpath = resourceBean.getPhysicalPath();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("personnelid", id);
//						List<Face> faceList = faceidService.getFaceidList(map);
//						this.personnelService.t_delete(entity, faceList, log, physicalpath);
					}
				}
			}
			return JSONReturn.buildSuccessWithEmptyBody();
		}
		return JSONReturn.buildFailure("数据错误!");
	}*/
/*	
	@RequestMapping(value = { "personnel-show" }, method = { RequestMethod.GET })
	public String personnelShow(
			@RequestParam(value = "id", required = false) String id,
			HttpSession session, HttpServletRequest request, Model model) {
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
		Personnel personnel = personnelService.getEntityByPK(Integer.valueOf(id));
		if (personnel != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("personnelid", personnel.getPersonnelid());
			List<Face> faceList = faceidService.getFaceidList(map);
			Face face = null;
			if (faceList != null && faceList.size() > 0) {
				face = faceList.get(0);
			}
			model.addAttribute("face", face);
			model.addAttribute("personnel", personnel);
		}
		model.addAttribute("personnelTypeList", personnelTypeService.getPersonnelTypeList(null));
		return "manage/personnel/personnel-show";
		
	}
	*/
	/*@RequestMapping(value = "/hl-detectocx-download.html",method = RequestMethod.GET)
	public void downloadDetectOcx(HttpServletRequest request, HttpServletResponse response) {
		String templateName = resourceBean.getOCXPath();
		String path = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "assets" + File.separator + templateName;
		FileUtils.downloadFile(path, response, ContentType.ZIP);
	}
	*/
	/*@RequestMapping(value = "/personnel-excel-export.html",method = RequestMethod.GET)
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
		String templateName = resourceBean.getPersonnelExcelPath();
		String path = request.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "assets" + File.separator + templateName;
		File file = new File(path);
		String filename = "人员信息导入模板.xlsx";
		FileUtils.outputExcel(response, file, filename);
	}*/
	
	//人员信息excel批量导入
	@RequestMapping(value = "/personnel-excel-import.html", method = RequestMethod.GET)
	public String excelimportys(Model model) throws IOException {
		return "manage/personnel/personnel-excel";
	}
/*
	@RequestMapping(value = "/personnel-excel-import", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn addMultiplePersonnel(
			@RequestParam(value = "excelFile") MultipartFile excelFile,
			HttpSession session, HttpServletResponse response,
			HttpServletRequest request) throws IOException,
			InstantiationException, IllegalAccessException {
		if (!excelFile.isEmpty()) {
			String name = excelFile.getOriginalFilename();
			if (excelFile.getSize() > 50 * 1024 * 1024) {
				return JSONReturn.buildFailure("文件过大，请上传小于50MB的文件");
			}
			if (!Strings.isNullOrEmpty(name) && name.lastIndexOf(".") != -1
					&& name.lastIndexOf(".") != name.length() - 1) {
				String suffix = name.substring(name.lastIndexOf(".") + 1);
				if (ContentType.XLSX.name().equalsIgnoreCase(suffix)) {
					InputStream in = excelFile.getInputStream();
					XSSFWorkbook xssfWorkbook = new XSSFWorkbook(in);
					List<Personnel> personnelList = new ArrayList<Personnel>();
					String result = new ParseExcelFile<Personnel>() {
						@Override
						public String doParse(Row row, Personnel t) {
							Map<String, Object> map = new HashMap<String, Object>();
							Cell cell = row.getCell(1);
							if(cell != null){
								cell.setCellType(CellType.STRING);
								t.setName(cell.getStringCellValue().trim());
								
							} else {
								return "人员姓名不能为空;第" + rowNum + "行";
							}
							cell = row.getCell(2);
							if(cell != null){
								cell.setCellType(CellType.STRING);
								String sex = cell.getStringCellValue().trim();
								if("男".equals(sex)){
									t.setSex(1);
								} else if("女".equals(sex)) {
									t.setSex(2);
								}
							}else{
								return "性别不能为空;第" + rowNum + "行";
							}
							cell = row.getCell(3);
							List<Personnel> list = null;
							if(cell != null){
								cell.setCellType(CellType.STRING);
								String credentials = cell.getStringCellValue().trim().replaceAll("\\u00A0","").toUpperCase();
								if(Strings.isNullOrEmpty(credentials)) {
									return "人员证件号不能为空;第" + rowNum + "行:" + credentials;
								}
								map.put("credentials", credentials);
								list = personnelService.getPersonnelList(map);
								if(CollectionUtils.isNotEmpty(list)) {
									return "证件号已存在:;第" + rowNum + "行:" + credentials;
								} else {
									t.setCredentials(credentials);
								}
							}else{
								return "人员证件号不能为空;第" + rowNum + "行";
							}
							cell = row.getCell(4);
							List<Personnel> list1 = null;
							if (cell != null) {
								cell.setCellType(CellType.STRING);
								String tel = cell.getStringCellValue().trim();
								map.clear();
								map.put("name", t.getName());
								map.put("tel", tel);
								list1 = personnelService.getPersonnelList(map);
								if(CollectionUtils.isNotEmpty(list1)) {
									return "该条数据已存在:;第" + rowNum + "行:" + t.getCredentials();
								} else {
									t.setTel(tel);
								}
							} else {
								return "联系电话不能为空;第" + rowNum + "行";
							}
							cell = row.getCell(5);
							if (cell != null) {
								cell.setCellType(CellType.STRING);
								String areaname = cell.getStringCellValue().trim();
								Integer areacode = null;
								List<AREA> areaList = areaService.getAreaList(null);
								for (AREA area:areaList) {
									if (areaname.equalsIgnoreCase(area.getAreaname())) {
										areacode = area.getAreacode();
									}
								}
								if (areacode != null) {
									t.setAreacode(areacode);
								} else {
									return "所属区域填写错误;第" + rowNum + "行";
								}
							} else {
								return "所属区域不能为空;第" + rowNum + "行";
							}
							cell = row.getCell(6);
							if (cell != null) {
								cell.setCellType(CellType.STRING);
								String typename = cell.getStringCellValue().trim();
								if (typename.equalsIgnoreCase("正常人员")) {
									t.setType(1);
								} else if (typename.equalsIgnoreCase("保安门卫")) {
									t.setType(2);
								} else if (typename.equalsIgnoreCase("外聘人员")) {
									t.setType(3);
								} else {
									return "人员类型填写错误;第" + rowNum + "行";
								}
							} else {
								return "人员类型不能为空;第" + rowNum + "行";
							}
							cell = row.getCell(7);
							if (cell != null ) {
								cell.setCellType(CellType.STRING);
								t.setCardid(cell.getStringCellValue().trim());
							}
							cell = row.getCell(8);
							if (cell != null ) {
								cell.setCellType(CellType.STRING);
								t.setAddress(cell.getStringCellValue().trim());
							}
							cell = row.getCell(9);
							if (cell != null ) {
								//外聘人员添加截止日期
								if (t.getType()==3) {
									cell.setCellType(CellType.STRING);
									String time = cell.getStringCellValue().trim();
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									try {
										t.setExpirationdate(sdf.parse(time));
									} catch (ParseException e) {
										e.printStackTrace();
										return "截止日期格式转换错误;第" + rowNum + "行";
									}
								}
							} else {
								if (t.getType()==3) {
									return "外聘人员类型截止日期不能为空;第" + rowNum + "行";
								}
							}
							t.setAddtime(new Date());
							t.setStatus(0);
							return null;
						}
					}.parseExcel(xssfWorkbook, personnelList);
					Set<Personnel> sets = new TreeSet<Personnel>(
							new Comparator<Personnel>() {
								public int compare(Personnel o1, Personnel o2) {
									if (Objects.equals(o1.getCredentials(),o2.getCredentials())
											||(Objects.equals(o1.getName(),o2.getName())&& Objects.equals(o1.getTel(),o2.getTel()))) {
										return 0;
									} else {
										return o1.getCredentials().hashCode()- o2.getCredentials().hashCode();
									}
								}
							});
					sets.addAll(personnelList);
					if (sets.size() != personnelList.size()) {
						return JSONReturn.buildFailure("Excel中存在人员证件号重复的数据，请处理后再重试！");
					} else if (Constants.SUCCESS.equals(result)) {
						// 记录日志
						String ipaddress = GetRemoteAddr.getIpAddr(request);
						String session_loginname = (String) session.getAttribute("session_loginname");
						XT_LOG log = new XT_LOG();
						log.setUsername(session_loginname);
						log.setClientip(ipaddress);
						log.setAddtime(new Date());
						personnelService.addMultiplePersonnel(personnelList, log);
						return JSONReturn.buildSuccessWithEmptyBody();
					} else if (Constants.FAIL.equals(result)) {
						return JSONReturn.buildFailure("Excel文件有误！");
					} else {
						return JSONReturn.buildFailure(result);
					}
				} else {
					return JSONReturn.buildFailure("文件格式有误");
				}
			} else {
				return JSONReturn.buildFailure("文件格式有误");
			}
		} else {
			return JSONReturn.buildFailure("上传文件为空");
		}
	}
	*/
	// 批量导入照片
	@RequestMapping(value = "/personnel-photo-import.html", method = RequestMethod.GET)
	public String uploadPersonnelPhoto() {
		return "manage/personnel/personnel-photo";
	}
	/*		
	@RequestMapping(value = "/personnel-photo-import", method = RequestMethod.POST)
	@ResponseBody
	public JSONReturn uploadPersonnelPhoto(
			@RequestParam(value = "zipFile") MultipartFile zipFile,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			InstantiationException, IllegalAccessException {
		if (!zipFile.isEmpty()) {
			String name = zipFile.getOriginalFilename();
			if (zipFile.getSize() > 50 * 1024 * 1024) {
				return JSONReturn.buildFailure("文件过大，请上传小于50MB的文件");
			}
			if (!Strings.isNullOrEmpty(name) && name.lastIndexOf(".") != -1
					&& name.lastIndexOf(".") != name.length() - 1) {
				String suffix = name.substring(name.lastIndexOf(".") + 1);
				if (ContentType.ZIP.name().equalsIgnoreCase(suffix)) {
					String zipSavePath = resourceBean.getPhysicalPath();
					String zipName = System.nanoTime() + ".zip";
					File file = new File(zipSavePath + File.separator + zipName);
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String result = null;
					LogRecord.info("人员照片开始导入！" + sdf.format(new Date()));
					try {
						FileUtils.saveFile(zipFile.getInputStream(),zipSavePath, zipName);
						ZipFile zip = new ZipFile(file, Charset.forName("GBK"));
						result = parseZipPackage(session, zip, request,resourceBean.getPhysicalPath());
					} finally {
						file.delete();
					}
					LogRecord.info("人员照片导入结束！" + sdf.format(new Date()));
					return JSONReturn.buildSuccess(result);
				} else {
					return JSONReturn.buildFailure("文件格式有误");
				}
			} else {
				return JSONReturn.buildFailure("文件格式有误");
			}
		} else {
			return JSONReturn.buildFailure("上传文件为空");
		}
	}
	*/		/*
	private String parseZipPackage(HttpSession session, ZipFile zipFile,
			HttpServletRequest request, String picPath) {
		boolean isSuccess = true;
		Map<String, Object> conditionsMap = new HashMap<String, Object>();
		int countAll = 0, countValid = 0;
		StringBuffer falseMessage = new StringBuffer("");
		List<PersonnelDTO> jzList = new ArrayList<PersonnelDTO>();
		try {
			for (Enumeration<? extends ZipEntry> entries = zipFile.entries(); entries
					.hasMoreElements();) {
				ZipEntry entry = entries.nextElement();
				String zipEntryName = entry.getName();
				if (!Strings.isNullOrEmpty(zipEntryName) && zipEntryName.toLowerCase().endsWith(".jpg")) {
					countAll++;
					int startPos = zipEntryName.lastIndexOf("/") + 1;
					int endPos = zipEntryName.lastIndexOf(".");
					String picName = null;
					String credentials = null;
					if (startPos >= 0 && startPos < zipEntryName.length()
							&& endPos >= 0 && endPos < zipEntryName.length()
							&& startPos < endPos) {
						picName = zipEntryName.substring(startPos, endPos);
						if (!Strings.isNullOrEmpty(picName)) {
							credentials = picName;
						}
					} else {
						falseMessage.append(picName).append(".jpg:照片命名错误;");
						continue;
					}

					PersonnelDTO jz = new PersonnelDTO();
					String webInfoPath = resourceBean.getPhysicalPath();
					String facesearchPath = File.separator + "personPhoto"
							+ File.separator + DirsUtils.DirsString()
							+ File.separator + picName
							+ File.separator;
					new File(webInfoPath + facesearchPath).mkdirs();
					String picname = UUID.randomUUID().toString() + ".jpg";
					
					FileUtils.saveFile(zipFile.getInputStream(entry), webInfoPath + facesearchPath,
							picname);

					// 提取FaceDetector和FaceVerifier
					ServletContext sct = request.getServletContext();
					FaceAPI faceAPI = (FaceAPI) sct.getAttribute("faceAPI");
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
					File file = new File(webInfoPath + facesearchPath + picname);
					FileInputStream fin = null;
					String feature_pic = null;
					// 如果FaceDetector和FaceVerifier不为空，则提取特征值
					if (detector != null && verifier != null) {
						try {
							fin = new FileInputStream(file);
							if (fin.available() > 1024 * 1024) {
								// 删除当前不合格的照片
								if (file.exists()) {
									file.delete();
								}
								falseMessage.append(picName).append(".jpg:照片大于1M;");
								continue;
							}
							BufferedImage bufferedImage = ImageIO.read(fin);
							feature_pic = faceAPI.getFeatureString(bufferedImage, verifier, detector);
							if (Strings.isNullOrEmpty(feature_pic)) {
								// 删除当前不合格的照片
								if (file.exists()) {
									file.delete();
								}
								falseMessage.append(picName).append(".jpg:照片无法识别到人脸;");
								continue;
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							faceMap.put("flag", 0);
							if (fin != null) {
								fin.close();
							}
						}
					}
					jz.setPicpath(facesearchPath + picname);
					jz.setFeature(feature_pic);
					if (!Strings.isNullOrEmpty(credentials)) {
						jz.setCredentials(credentials);
					} else {
						falseMessage.append(picName).append(".jpg:照片命名错误;");
						continue;
					}
					jzList.add(jz);
					countValid++;
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			LogRecord.error("人员批量导入照片出错：" + e);
			isSuccess = false;
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (jzList != null && jzList.size() > 0) {
			for (PersonnelDTO jzdto : jzList) {
				conditionsMap.clear();
				if (!Strings.isNullOrEmpty(jzdto.getCredentials())) {
					conditionsMap.put("credentials", jzdto.getCredentials());
				}
				List<Personnel> personnelList = personnelService.getPersonnelList(conditionsMap);
				Personnel jz = null;
				if (personnelList != null && personnelList.size() > 0) {
					jz = personnelList.get(0);
				}
				if (jz == null) {
					if (countValid > 0) {
						countValid = countValid - 1;
					}
					falseMessage.append(jzdto.getCredentials()).append(".jpg:找不到证件号对应的人员;");
					File file = new File(resourceBean.getPhysicalPath() + jzdto.getPicpath());
					if (file.exists()) {
						file.delete();
					}
					continue;
				}
				// 提取FaceDetector和FaceVerifier
				ServletContext sct = request.getServletContext();
				List<Object> facePool = (List<Object>) sct.getAttribute("faceList");
				Iterator<Object> it = facePool.iterator();
				Map<String, Object> faceMap = null;
				// 更新人员特征值
				ServletContext servletContext = request.getServletContext();
				FaceVerifier faceverifier = null;
				Pointer feature_pointer1 = null;
				synchronized (this) {
					while (it.hasNext()) {
						faceMap = (Map<String, Object>) it.next();
						Integer flag = (Integer) faceMap.get("flag");
						if (flag == 0) {
							faceMap.put("flag", 1);
							faceverifier = (FaceVerifier) faceMap
									.get("verifier");
							break;
						}
					}
				}
				// 如果FaceVerifier不为空
				if (faceverifier != null) {
					try {
						if (!Strings.isNullOrEmpty(jzdto.getFeature())) {
							feature_pointer1 = faceverifier.deserializeFeature(jzdto.getFeature());
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						faceMap.put("flag", 0);
					}
				}
				
				// 记录日志
				String ipaddress = GetRemoteAddr.getIpAddr(request);
				String session_loginname = (String) session.getAttribute("session_loginname");
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setClientip(ipaddress);
				log.setAddtime(new Date());
				log.setContent("批量导入人员照片:" + jz.getCredentials());
				// 更新人员照片
				personnelService.t_update(jz, jzdto, log, resourceBean.getPhysicalPath(), servletContext, feature_pointer1);
			}
		}

		String result = "";
		if (isSuccess) {
			if (falseMessage != null && falseMessage.length() > 0) {
				result = "上传" + countAll + "张图片:成功" + countValid + "张;"
						+ falseMessage.substring(0, falseMessage.length() - 1);
			} else {
				result = "上传" + countAll + "张图片:成功" + countValid + "张";
			}
		} else {
			result = "上传出现异常，请重试";
		}
		return result;
	}
	*/
	@RequestMapping(value = "/importPhotoResult.html", method = RequestMethod.POST)
	public String importPhotoResult(
			@RequestParam(value = "falseMessage", required = false) String falseMessage,
			HttpSession session, HttpServletRequest request, Model model) {
		if(falseMessage != null){
			String[] sss = falseMessage.split(";");
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (String ss : sss) {
				String[] s = ss.split(":");
				if(s.length == 2){
					Map<String, String> map = new HashMap<String, String>();
					map.put("sflag", s[0]);
					map.put("fflag", s[1]);
					list.add(map);
				}
			}
			model.addAttribute("messageList", list);
		}
		return "manage/personnel/importPhotoResult";
	}
	
	private List<AREA> areaRecursionDown(Integer areacode,List<AREA> returnList,AreaService areaService){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("superCode", areacode);
		List<AREA> list = areaService.getAreaList(map);
		if(list != null && list.size() > 0){
			for (AREA area : list) {
				returnList.add(area);
				areaRecursionDown(area.getAreacode(),returnList,areaService);
			}
		}
		return returnList;
	}
	
	private List<AREA> areaRecursionUp(Integer superCode,List<AREA> returnList,AreaService areaService){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("areaCode", superCode);
		List<AREA> list = areaService.getAreaList(map);
		if(list != null && list.size() > 0){
			for (AREA area : list) {
				returnList.add(area);
				areaRecursionUp(area.getSupercode(),returnList,areaService);
			}
		}
		return returnList;
	}
	/*
	private void clearServletContext(ServletContext servletContext,Face face,Integer oldAreacode) {
		//由普通人员改变为保安则清除此人原区域代码在正常人员内存中的人脸信息
		Map<Integer,List<FaceFeatureDTO>> faceMap1 = (Map<Integer, List<FaceFeatureDTO>>) servletContext.getAttribute("faceFeature");
		List<AREA> recursionList = Lists.newArrayList();
		//取原来的区域代码
		AREA area = areaService.getAreaByPK(oldAreacode);
		recursionList.add(area);
		//得到该区域以及下属区域的合集(针对正常人员，外聘人员)
		areaRecursionUp(oldAreacode,recursionList,areaService);
		areaRecursionDown(oldAreacode,recursionList,areaService);
		Iterator<AREA> recursionIt = recursionList.iterator();
		while(recursionIt.hasNext()){
			AREA recursionArea = recursionIt.next();
			//判断是否有该区域的map
			List<FaceFeatureDTO> facelist = Lists.newArrayList();
			List<FaceFeatureDTO> list = Lists.newArrayList();
			FaceFeatureDTO dto = null;
			if (faceMap1.containsKey(recursionArea.getAreacode())) {
				//取得人脸信息集合
				facelist = faceMap1.get(recursionArea.getAreacode());
				Iterator<FaceFeatureDTO> faceIt = facelist.iterator();
				while (faceIt.hasNext()) {
					dto = faceIt.next();
					if (face.getFaceid()==dto.getId()) {
						continue;
					} else {
						list.add(dto);
					}
				}
			}
			faceMap1.put(recursionArea.getAreacode(), list);
		}
		servletContext.setAttribute("faceFeature", faceMap1);
	}
	
	private void addServletContext(ServletContext servletContext,Pointer feature_pointer,Personnel personnel,Face face) {
		if (feature_pointer!=null) {
			//新增内存到普通人员中
			Map<Integer,List<FaceFeatureDTO>> faceMap1 = (Map<Integer, List<FaceFeatureDTO>>) servletContext.getAttribute("faceFeature");
			FaceFeatureDTO dto = new FaceFeatureDTO();
			dto.setAreacode(personnel.getAreacode());
			dto.setFeature(feature_pointer);					
			dto.setId(face.getFaceid());
			dto.setSource(1);
			List<AREA> recursionList = Lists.newArrayList();
			AREA area = areaService.getAreaByPK(personnel.getAreacode());
			recursionList.add(area);
			//得到该区域以及下属区域的合集(针对正常人员，外聘人员)
			areaRecursionUp(personnel.getAreacode(),recursionList,areaService);
			areaRecursionDown(personnel.getAreacode(),recursionList,areaService);
			Iterator<AREA> recursionIt = recursionList.iterator();
			List<FaceFeatureDTO> facelist = Lists.newArrayList();
			while(recursionIt.hasNext()){
				AREA recursionArea = recursionIt.next();
				//判断是否有该区域的map
				if (faceMap1.containsKey(recursionArea.getAreacode())) {
					//取得人脸信息集合
					facelist = faceMap1.get(recursionArea.getAreacode());
					facelist.add(dto);
				} else {
					facelist.add(dto);
				}
				faceMap1.put(recursionArea.getAreacode(), facelist);
			}
		servletContext.setAttribute("faceFeature", faceMap1);
		}
	}
*/
}
