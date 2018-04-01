package cn.edu.zjut.acs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.mapper.FaceMapper;
import cn.edu.zjut.acs.model.Face;
import cn.edu.zjut.acs.service.FaceService;



@Service
public class FaceServiceImpl implements FaceService {
	
	@Resource
	private FaceMapper faceMapper;

	public Face getEntityByPK(Integer faceid) {
		return faceMapper.getEntityByPK(faceid);
	}

	@Transactional
	public void saveOfRegister(int personnelid, String path, String feature) {
		faceMapper.saveOfRegister(personnelid,path,feature);
	}

	public List<Face> getFaceidList(Map<String, Object> map) {
		return faceMapper.getFaceidList(map);
	}
	public List<Face> findFaceidList(Map<String, Object> map) {
		return faceMapper.findFaceidList(map);
	}

	@Transactional
	public int saveFaceid(Face face) {
		return faceMapper.saveFaceid(face);
	}


}
