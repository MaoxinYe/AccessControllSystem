package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Worktime;


public interface WorktimeMapper {

	/**
     * 根据Map查找
     * @param Worktime
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
     * 新增作息时间信息
     * @param Worktime
     */
	void saveWorktime(Worktime worktime);

	/**
     * 修改作息时间信息
     * @param Worktime
     */
	void updateWorktime(Worktime worktime);
	
	/**
     * 删除作息时间信息
     * @param Worktime
     */
	void deleteWorktime(Worktime worktime);
	
}
