package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.edu.zjut.acs.model.Face;



public interface FaceMapper {
	
	Face getEntityByPK (Integer faceid);

	Object saveOfRegister(@Param("personnelid")int personnelid, @Param("path")String path,@Param("feature")String feature);

	List<Face> findAllByQuery(Map<String, Object> query);

	List<Face> findFaceidList(Map<String, Object> map);

	List<Face> getFaceidList(Map<String, Object> map);
	
	int saveFaceid(Face face);
	
	void deleteFace(Integer faceid);
	
	void updateFaceid(Face face);

}
