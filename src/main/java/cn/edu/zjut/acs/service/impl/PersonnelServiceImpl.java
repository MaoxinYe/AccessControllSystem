package cn.edu.zjut.acs.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.sun.jna.Pointer;

import cn.edu.zjut.acs.dto.PersonnelDTO;
import cn.edu.zjut.acs.mapper.AreaMapper;
import cn.edu.zjut.acs.mapper.FaceMapper;
import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.mapper.PersonnelMapper;
import cn.edu.zjut.acs.mapper.VisitorMapper;
import cn.edu.zjut.acs.model.AREA;
import cn.edu.zjut.acs.model.Face;
import cn.edu.zjut.acs.model.Personnel;
import cn.edu.zjut.acs.model.Visitor;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.PersonnelService;
import cn.edu.zjut.acs.support.PageCommon;

@Service
public class PersonnelServiceImpl implements PersonnelService {
	
	@Resource
	private PersonnelMapper personnelMapper;
	@Resource
	private FaceMapper faceMapper;
	@Resource
	private LogMapper logMapper;
	/*@Resource
	private RecordDao recordDao;*/
	@Resource
	private VisitorMapper visitorMapper;
	@Resource
	private AreaMapper areaMapper;

	public Personnel getEntityByPK(Integer personnelid) {
		return personnelMapper.getEntityByPK(personnelid);
	}

	public List<Personnel> getPersonnelList(Map<String, Object> map) {
		return personnelMapper.getPersonnelList(map);
	}

	public Map<String, Object> getPersonnelPageList(Map<String, Object> map,int nowPage) {
		//分页查询条件
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		int totalRows = personnelMapper.getRowsCount(map);
		pageCommon.setTotalRows(totalRows);
		int startRow = pageCommon.getStartRow();
		int pagesize = pageCommon.getPageSize();
		if (startRow >= 0) {
			map.put("startindex", startRow);
		}
		if (pagesize >= 0) {
			map.put("pagesize", pagesize);
		}
		Map<String, Object> pageMap = new HashMap<String, Object>();
		List<Personnel> personnelList = personnelMapper.getPersonnelPageList(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", personnelList);
		return pageMap;
	}

	@Transactional
	public void t_insert(Personnel personnel, Face face, XT_LOG log) {
		personnelMapper.savePersonnel(personnel);
		face.setPersonnelid(personnel.getPersonnelid());
		faceMapper.saveFaceid(face);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_update(Personnel personnel, Face face, String oldPath, XT_LOG log, String physicalpath) {
		personnelMapper.updatePersonnel(personnel);
		if (StringUtils.isNotBlank(oldPath)) {
			faceMapper.updateFaceid(face);
			File f = new File(physicalpath + oldPath);
			if (f.exists()) {
				f.delete();
			}
		}
		logMapper.saveLog(log);
	}
/*
	@Transactional
	public void t_delete(Personnel personnel, List<Face> faceList,
			XT_LOG log,String physicalpath) {
		//查人员访问记录
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("personneltype", 1);
		map.put("personnelid", personnel.getPersonnelid());
		List<RecordDTO> recordList = recordDao.getRecordList(map);
		//查该人员访客
		map.clear();
		map.put("personnelid", personnel.getPersonnelid());
		List<Visitor> visitorsList = visitorMapper.getVisitorList(map);
		//当有访客或出入记录时假删除
		if ((recordList!=null&&recordList.size()>0)||(visitorsList!=null&&visitorsList.size()>0)) {
			//人员标记为无效状态
			personnel.setStatus(0);
			personnelMapper.updatePersonnel(personnel);
			//记录日志
			log.setContent("假删除人员:" + personnel.getName() + "--" + personnel.getTel());
			logMapper.saveLog(log);
		}else {
			//先删除所有人脸记录
			Iterator<Face> faceIt = faceList.iterator();
			while (faceIt.hasNext()) {
				Face face = faceIt.next();
				if (!Strings.isNullOrEmpty(face.getPath())) {
					File f = new File(physicalpath +face.getPath());
					if (f.exists()) {
						f.delete();
					}
				}
				faceMapper.deleteFace(face.getFaceid());
			}
			//删除该人员
			personnelMapper.deletePersonnel(personnel.getPersonnelid());
			//记录日志
			logMapper.saveLog(log);
		}
	}
*/
	@Transactional
	public void updatePersonnel(Personnel personnel) {
		personnelMapper.updatePersonnel(personnel);
	}

	@Transactional
	public void addMultiplePersonnel(List<Personnel> personnelList, XT_LOG log) {
		if(CollectionUtils.isNotEmpty(personnelList)) {
			for(Personnel personnel : personnelList) {
				personnelMapper.savePersonnel(personnel);
				log.setContent("批量导入人员信息:" + personnel.getTel() + "--" + personnel.getName());
				logMapper.saveLog(log);
			}
		}
	}

	/*public void t_update(Personnel personnel, PersonnelDTO personnelDTO,XT_LOG log, String physicalpath,ServletContext servletContext,Pointer feature_pointer1) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("personnelid", personnel.getPersonnelid());
		List<Face> faceList = faceMapper.getFaceidList(map);
		Face face = null;
		if (faceList != null && faceList.size() > 0) {
			face = faceList.get(0);
		}
		if (face != null) {
			if (!Strings.isNullOrEmpty(face.getPath())) {
				File file = new File(physicalpath + face.getPath());
				if (file.exists()) {
					file.delete();
				}
			}
			face.setFacefeature(personnelDTO.getFeature());
			face.setPath(personnelDTO.getPicpath());
			faceMapper.updateFaceid(face);
			//写入内存
			if (personnel.getType()==2) {
				if (StringUtils.isNotBlank(personnelDTO.getFeature())) {
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
								if (feature_pointer1 != null) {
									dto.setFeature(feature_pointer1);
									list.add(dto);
								}
							} else {
								list.add(dto);
							}
						}
						if (flag) {
							dto = new GuardFaceFeatureDTO();
							dto.setGuardid(face.getFaceid());
							dto.setFeature(feature_pointer1);
							list.add(dto);
						}
					}
					servletContext.setAttribute("guardFaceFeature", list);
				}
			} else {
				//普通人员类型则清除此人原区域代码在正常人员内存中的人脸信息
				clearServletContext(servletContext, face, personnel.getAreacode());
				//新增内存到普通人员中
				addServletContext(servletContext, feature_pointer1, personnel, face);
			}
		} else {
			face = new Face();
			face.setFacefeature(personnelDTO.getFeature());
			face.setPath(personnelDTO.getPicpath());
			face.setPersonnelid(personnel.getPersonnelid());
			faceMapper.saveFaceid(face);
			//保安人员存入保安人脸集合，普通人员存入map
			if (personnel.getType()==2) {
				List<GuardFaceFeatureDTO> guardFacelist = (List<GuardFaceFeatureDTO>) servletContext.getAttribute("guardFaceFeature");
				if (feature_pointer1 != null) {
					GuardFaceFeatureDTO dto = new GuardFaceFeatureDTO();
					dto.setGuardid(face.getFaceid());
					dto.setFeature(feature_pointer1);
					guardFacelist.add(dto);
				}
				servletContext.setAttribute("guardFaceFeature", guardFacelist);
			} else {
				//新增内存到普通人员中
				addServletContext(servletContext, feature_pointer1, personnel, face);
			}
		}
		personnelMapper.updatePersonnel(personnel);
		logMapper.saveLog(log);
	}
	*/
	private List<AREA> areaRecursionDown(Integer areacode,List<AREA> returnList,AreaMapper areaDao){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("superCode", areacode);
		List<AREA> list = areaDao.getAreaList(map);
		if(list != null && list.size() > 0){
			for (AREA area : list) {
				returnList.add(area);
				areaRecursionDown(area.getAreacode(),returnList,areaDao);
			}
		}
		return returnList;
	}
	
	private List<AREA> areaRecursionUp(Integer superCode,List<AREA> returnList,AreaMapper areaDao){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("areaCode", superCode);
		List<AREA> list = areaDao.getAreaList(map);
		if(list != null && list.size() > 0){
			for (AREA area : list) {
				returnList.add(area);
				areaRecursionUp(area.getSupercode(),returnList,areaDao);
			}
		}
		return returnList;
	}


	
	/*private void clearServletContext(ServletContext servletContext,Face face,Integer oldAreacode) {
		//由普通人员改变为保安则清除此人原区域代码在正常人员内存中的人脸信息
		Map<Integer,List<FaceFeatureDTO>> faceMap1 = (Map<Integer, List<FaceFeatureDTO>>) servletContext.getAttribute("faceFeature");
		List<AREA> recursionList = Lists.newArrayList();
		//取原来的区域代码
		AREA area = areaDao.getAreaByPK(oldAreacode);
		recursionList.add(area);
		//得到该区域以及下属区域的合集(针对正常人员，外聘人员)
		areaRecursionUp(oldAreacode,recursionList,areaDao);
		areaRecursionDown(oldAreacode,recursionList,areaDao);
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
			AREA area = areaDao.getAreaByPK(personnel.getAreacode());
			recursionList.add(area);
			//得到该区域以及下属区域的合集(针对正常人员，外聘人员)
			areaRecursionUp(personnel.getAreacode(),recursionList,areaDao);
			areaRecursionDown(personnel.getAreacode(),recursionList,areaDao);
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
