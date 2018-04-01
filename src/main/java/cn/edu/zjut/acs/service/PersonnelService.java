package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import com.sun.jna.Pointer;

import cn.edu.zjut.acs.dto.PersonnelDTO;
import cn.edu.zjut.acs.model.Face;
import cn.edu.zjut.acs.model.Personnel;
import cn.edu.zjut.acs.model.XT_LOG;



public interface PersonnelService {
	/**
     * 根据主键查询模块信息
     * @param personnelid
     * @return Personnel
     */
	Personnel getEntityByPK (Integer personnelid);
	
	/**
     * 根据map中的信息查找
     * @param map
     * @return List<Personnel>
     */
	List<Personnel> getPersonnelList(Map<String, Object> map);

	/**
     * 根据条件获取人员信息分页数据
     * @param RegisterDTO，path,feature
     * 
     */
	Map<String, Object> getPersonnelPageList(Map<String, Object> map,int nowpage);
	
	/**
     * 存储人员信息
     * @param Personnel
     * @return PERSONNELID
     */
	void t_insert(Personnel personnel,Face face,XT_LOG log);
	/**
	 * 更新人员信息记录日志
	 * @param personnel
	 * @param log
	 */
	void t_update(Personnel personnel, Face face, String oldPath, XT_LOG log, String physicalpath);
	/**
	 * 删除人员记录日志
	 * @param personnel
	 * @param faceList
	 * @param log
	 */
	//void t_delete(Personnel personnel,List<Face> faceList,XT_LOG log,String physicalpath);
	/**
	 * 更新人员信息
	 * @param personnel
	 */
	void updatePersonnel(Personnel personnel);
	/**
	 * 批量添加人员
	 * @param peraonnelList
	 * @param log
	 */
	void addMultiplePersonnel(List<Personnel> personnelList, XT_LOG log);
	/**
	 * 更新人员照片记录日志
	 * @param personnel
	 * @param log
	 */
	//void t_update(Personnel personnel,PersonnelDTO personnelDTO, XT_LOG log, String physicalpath,ServletContext sct,Pointer feature_pointer1);

}
