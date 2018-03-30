package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.model.XT_USER;


public interface UserService {

	/**
	 * 根据key查询用户信息
	 * @param key
	 * @return XT_USER
	 */
	XT_USER getUserByPK(Integer key);
	
	/**
	 * 根据loginname查询用户信息
	 * @param loginname
	 * @return XT_USER
	 */
	List<XT_USER> getUserByLoginName(String loginname);

	/**
	 * 根据map查询用户信息
	 * @param map
	 * @return Map<String, Object>
	 */
	Map<String, Object> getUserList(Integer nowPage,Map<String, Object> map);
	List<XT_USER> getUserList(Map<String, Object> map);

	/**
     * 新增用户以及记录日志
     * @param XT_USER  XT_LOG
     */
	void t_insert(XT_USER user, XT_LOG log);
	
	/**
     * 修改用户以及记录日志
     * @param XT_USER  XT_LOG
     */
	void t_update(XT_USER user, XT_LOG log);
	
	/**
     * 删除用户以及记录日志
     * @param XT_USER 
     */
	void t_delete(XT_USER user, XT_LOG log);
	
	/**
     * 批量删除用户以及记录日志
     * @param session_loginname 
	 * @param ipaddress 
     * @param Integer[]
     */
	void t_delete(Integer[] ids, String session_loginname,String ipaddress);

}
