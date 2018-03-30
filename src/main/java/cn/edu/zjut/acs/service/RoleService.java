package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.model.XT_ROLE;



public interface RoleService {

	/**
     * 通过map查询角色类型
	 * @param map 
     * @return List<XT_ROLE>
     */
	List<XT_ROLE> getRoleList(Map<String, Object> map);

	/**
     * 新增角色类型以及记录日志
     * @param XT_ROLE  XT_LOG
     */
	void t_insert(XT_ROLE role, XT_LOG log);

	/**
     * 通过KEY获得ROLE
     * @param key
     * @return XT_ROLE
     */
	XT_ROLE getRoleByPK(Integer key);

	
	/**
     * 修改角色类型以及记录日志
     * @param XT_ROLE  XT_LOG
     */
	void t_update(XT_ROLE role, XT_LOG log);

	
	/**
     * 删除角色类型以及记录日志
     * @param XT_ROLE  XT_LOG
     */
	void t_delete(XT_ROLE role, XT_LOG log);

	/**
     * 批量删除角色类型以及记录日志
	 * @param session_loginname 
	 * @param ipaddress 
     * @param Integer[]
     */
	void t_delete(Integer[] ids, String ipaddress, String session_loginname);

}
