package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Worktime;



public interface WorktimeService {

	/**
     * 根据Map查找
     * @param Map<String, Object>
     * @return List<Worktime>
     */
	List<Worktime> getWorktimeList(Map<String, Object> map);

	/**
     * 根据主键查询模块信息
     * @param key
     * @return Worktime
     */
	Worktime getWorktimeByPK(Integer key);

	/**
     * 新增作息时间
     *@param Worktime
     */
	void saveWorktime(Worktime worktime);

	/**
     * 修改作息时间信息
     * @param Worktime
     */
	void updateWorktime(Worktime worktime);
	
	/**
     * 修改作息时间的状态，规定只能由一个时间是启用状态，其他为停用
     * @param Worktime
     */
	void t_update(List<Worktime> worktimeList, Worktime worktime);
	
	/**
     * 删除作息时间信息
     *@param Worktime  XT_LOG
     */
	void deleteWorktime(Worktime worktime);
	
	/**
     * 批量删除作息时间
	 * @param session_loginname 
	 * @param ipaddress 
     * @param Integer[]
     */
	void t_delete(Integer[] ids);
	
}
