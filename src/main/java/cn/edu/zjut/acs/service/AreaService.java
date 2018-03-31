package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import cn.edu.zjut.acs.model.AREA;
import cn.edu.zjut.acs.model.XT_LOG;




public interface AreaService {

	/**
     * 根据Map查找
     * @param Map<String, Object>
     * @return List<AREA>
     */
	List<AREA> getAreaList(Map<String, Object> map);

	/**
     * 根据主键查询模块信息
     * @param key
     * @return AREA
     */
	AREA getAreaByPK(Integer key);

	/**
     * 新增防区以及记录日志
     *@param AREA  XT_LOG
     */
	void t_insert(AREA area, XT_LOG log);

	/**
     * 修改防区信息以及记录日志
     * @param AREA
     */
	void t_update(AREA area, XT_LOG log);
	
	/**
     * 删除防区信息以及记录日志
     *@param AREA  XT_LOG
     */
	void t_delete(AREA area, XT_LOG log);
	
	/**
     * 批量删除防区以及记录日志
	 * @param session_loginname 
	 * @param ipaddress 
     * @param Integer[]
     */
	 List<AREA> t_delete(Integer[] ids, String ipaddress, String session_loginname,ServletContext sct);

	
}
