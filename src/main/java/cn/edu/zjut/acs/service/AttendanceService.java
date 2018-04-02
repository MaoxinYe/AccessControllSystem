package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Attendance;
import cn.edu.zjut.acs.model.XT_LOG;



public interface AttendanceService {

	
	/**
     * 根据Map查找
     * @param Map<String, Object>
     * @return List<Attendance>
     */
	List<Attendance> getAttendanceList(Map<String, Object> map);
	
	/**
     * 根据Map查找带分页
     * @param Map<String, Object> 
     * @return Map<String, Object> 
     */
	Map<String, Object> getAttendanceList(Integer nowPage,Map<String, Object> map);
	
	/**
     * 新增考勤信息
     * @param Attendance
     */
	void saveAttendance(Attendance attendance);

	/**
     * 修改考勤信息
     * @param Attendance
     */
	void updateAttendance(Attendance attendance);

	/**
     * 修改考勤信息并记录日志
     * @param Attendance XT_LOG
     */
	void t_update(Attendance attendance, XT_LOG log);
	
	/**
     * 通过主键获得考勤记录
     * @param Integer
     */
	Attendance getAttendanceByPK(Integer key);

	/**
     * 根据Map进行考勤统计
     * @param Map<String, Object>
     * @return Map<String, Object>
     */
	Map<String, Object> getAttendanceStatistics(Integer nowPage,Map<String, Object> map);


}
