package cn.edu.zjut.acs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.dto.AttendanceStatisticsDTO;
import cn.edu.zjut.acs.mapper.AttendanceMapper;
import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.model.Attendance;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.AttendanceService;
import cn.edu.zjut.acs.support.PageCommon;

@Service
public class AttendanceServiceImpl implements AttendanceService {

	@Resource
	private AttendanceMapper attendanceMapper;
	@Resource
	private LogMapper logMapper;

	public List<Attendance> getAttendanceList(Map<String, Object> map) {
		return attendanceMapper.getAttendanceList(map);
	}
	
	public Map<String, Object> getAttendanceList(Integer nowPage,Map<String, Object> map) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		pageCommon.setTotalRows(attendanceMapper.getTotalNumber(map));
		map.put("pageCommon", pageCommon);
		List<Attendance> list = attendanceMapper.getAttendanceList(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", list);
		return pageMap;
	}

	public void saveAttendance(Attendance attendance) {
		attendanceMapper.saveAttendance(attendance);
	}

	public void updateAttendance(Attendance attendance) {
		attendanceMapper.updateAttendance(attendance);
	}

	public Attendance getAttendanceByPK(Integer key) {
		return attendanceMapper.getAttendanceByPK(key);
	}

	@Transactional
	public void t_update(Attendance attendance, XT_LOG log) {
		attendanceMapper.updateAttendance(attendance);
		logMapper.saveLog(log);
	}

	public Map<String, Object> getAttendanceStatistics(Integer nowPage,Map<String, Object> map) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		pageCommon.setTotalRows(attendanceMapper.getStatisticsTotalNum(map));
		map.put("pageCommon", pageCommon);
		List<AttendanceStatisticsDTO> list = attendanceMapper.getAttendanceStatistics(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", list);
		return pageMap;
	}

	
}
