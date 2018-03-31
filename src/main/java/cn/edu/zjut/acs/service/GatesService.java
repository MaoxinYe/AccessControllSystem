package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Gate;
import cn.edu.zjut.acs.model.XT_LOG;



public interface GatesService {

	/**
     * 根据主键查询模块信息
     * @param key
     * @return Gate
     */
	Gate getGatesByPK(Integer key);

	/**
	 * 根据map查询门禁闸机信息
	 * 带分页
	 * @param map
	 * @return Map<String, Object>
	 */
	Map<String, Object> getGatesList(int nowPage, Map<String, Object> map);
	/**
	 * 根据map查询门禁闸机信息
	 * 不带带分页
	 * @param map
	 * @return Map<String, Object>
	 */
	List<Gate> getGatesList(Map<String, Object> map);
	
	/**
     * 新增门禁闸机以及记录日志
     * @param Gate  XT_LOG
     */
	void t_insert(Gate gates, XT_LOG log);
	
	/**
     * 修改门禁闸机以及记录日志
     * @param Gate  XT_LOG
     */
	void t_update(Gate gates, XT_LOG log);
	
	/**
     * 给注册闸机门禁绑定唯一硬件代码
     * @param Gate  XT_LOG
     */
	void updateGates(Gate gates);
	
	/**
     * 删除门禁闸机以及记录日志
     * @param Gate 
     */
	void t_delete(Gate gates, XT_LOG log);
	
	/**
     * 批量删除门禁闸机以及记录日志
     * @param session_loginname 
	 * @param ipaddress 
     * @param Integer[]
     */
	void t_delete(Integer[] ids, String session_loginname,String ipaddress);

}
