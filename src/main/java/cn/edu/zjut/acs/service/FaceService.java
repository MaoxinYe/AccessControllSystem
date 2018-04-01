package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Face;



public interface FaceService {
	/**
     * 根据主键查询模块信息
     * @param id
     * @return JC_MODULE
     */
	Face getEntityByPK (Integer faceid);

	/**
     * 存储访客照片
     * @param personnelid，path，feature
     *
     */
	void saveOfRegister(int personnelid, String path, String feature);

	/**
     * 根据map中的信息查找
     * @param map
     * @return List<Face>
     */
	List<Face> getFaceidList(Map<String, Object> map);
	List<Face> findFaceidList(Map<String, Object> map);
	
	/**
     * 存储人员人脸信息
     * @param Face
     *
     */
	int saveFaceid(Face face);


}
