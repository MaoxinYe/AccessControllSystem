package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.dto.AttendanceStatisticsDTO;
import cn.edu.zjut.acs.model.Attendance;


public interface AttendanceMapper {

	List<Attendance> getAttendanceList(Map<String, Object> map);
	
	Integer getTotalNumber(Map<String, Object> map);

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
     * 通过主键获得考勤记录
     * @param Integer
     */
	Attendance getAttendanceByPK(Integer key);

	/**
     * 根据Map进行考勤统计
     * @param Map<String, Object>
     * @return List<AttendanceStatisticsDTO>
     */
	List<AttendanceStatisticsDTO> getAttendanceStatistics(Map<String, Object> map);

	/**
     * 根据Map查找考勤统计
     * @param Map<String, Object>
     * @return 总记录数
     */
	Integer getStatisticsTotalNum(Map<String, Object> map);

	
}
