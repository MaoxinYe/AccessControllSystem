package cn.edu.zjut.acs.support;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import cn.edu.zjut.acs.dto.RecordDTO;
import cn.edu.zjut.acs.mapper.AttendanceMapper;
import cn.edu.zjut.acs.mapper.PersonnelMapper;
import cn.edu.zjut.acs.mapper.RecordMapper;
import cn.edu.zjut.acs.mapper.WorktimeMapper;
import cn.edu.zjut.acs.model.Attendance;
import cn.edu.zjut.acs.model.Personnel;
import cn.edu.zjut.acs.model.Worktime;




public class GainWorkAttendanceTimerTask extends TimerTask {

	private AttendanceMapper attendanceMapper;
	private PersonnelMapper personnelMapper;
	private RecordMapper recordMapper;
	private WorktimeMapper worktimeMapper;
	
	public GainWorkAttendanceTimerTask(AttendanceMapper attendanceMapper,PersonnelMapper personnelMapper,RecordMapper recordMapper,WorktimeMapper worktimeMapper) {
		this.attendanceMapper = attendanceMapper;
		this.personnelMapper = personnelMapper;
		this.recordMapper = recordMapper;
		this.worktimeMapper = worktimeMapper;
	}
	
    public void run() {
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        	String startTime = "9:00";
        	String endTime = "6:00";
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("status", 1);
        	List<Worktime> worktimeList = worktimeMapper.getWorktimeList(map);
        	if(worktimeList != null && worktimeList.size() > 0){
        		Worktime worktime = worktimeList.get(0);
        		startTime = sdf.format(worktime.getStarttime());
        		endTime =sdf.format(worktime.getEndtime());
        	}
        	
        	map.clear();
//        	map.put("effectivePersonnel", true);
//        	map.put("status", "1");
        	List<Personnel> personnelList = personnelMapper.getPersonnelList(map);
        	Iterator<Personnel> it = personnelList.iterator();
        	while(it.hasNext()){
        		Personnel personnel = it.next();
        		map.clear();
        		map.put("personnelid", personnel.getPersonnelid());
        		map.put("starttime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        		map.put("endtime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        		map.put("inorout", 1);
        		map.put("personneltype", 1);
        		List<RecordDTO> inList = recordMapper.getRecordList(map);
        		RecordDTO inRecord = null;
        		RecordDTO outRecord = null;
        		if(inList != null && inList.size() > 0){
        			inRecord = inList.get(0);
        		}
        		map.put("inorout", 2);
        		List<RecordDTO> outList = recordMapper.getRecordList(map);
        		if(outList != null && outList.size() > 0){
        			outRecord = outList.get(outList.size() - 1);
        		}
        		Attendance attendance = new Attendance();
        		attendance.setPersonnel_id(personnel.getPersonnelid());
        		attendance.setPersonnel_name(personnel.getName());
        		if(inRecord != null && outRecord != null){
        			attendance.setStarttime(inRecord.getCreatetime());
        			attendance.setEndtime(outRecord.getCreatetime());
        			if(sdf.parse(startTime).getTime() >= sdf.parse(sdf.format(inRecord.getCreatetime())).getTime()
        					&& sdf.parse(endTime).getTime() <= sdf.parse(sdf.format(outRecord.getCreatetime())).getTime()){
        				attendance.setStatus(0);//正常
        			} else if(sdf.parse(startTime).getTime() < sdf.parse(sdf.format(inRecord.getCreatetime())).getTime()
        					&& sdf.parse(endTime).getTime() < sdf.parse(sdf.format(outRecord.getCreatetime())).getTime()){
        				attendance.setStatus(1);//迟到
        			} else if(sdf.parse(startTime).getTime() > sdf.parse(sdf.format(inRecord.getCreatetime())).getTime()
        					&& sdf.parse(endTime).getTime() > sdf.parse(sdf.format(outRecord.getCreatetime())).getTime()){
        				attendance.setStatus(2);//早退
        			} else if(sdf.parse(startTime).getTime() < sdf.parse(sdf.format(inRecord.getCreatetime())).getTime()
        					&& sdf.parse(endTime).getTime() > sdf.parse(sdf.format(outRecord.getCreatetime())).getTime()){
        				attendance.setStatus(-2);//迟到加早退
        			}
        		} else if(inRecord == null && outRecord != null) {
        			attendance.setEndtime(outRecord.getCreatetime());
        			attendance.setStatus(-3);//异常
        		} else if(inRecord != null && outRecord == null){
        			attendance.setStarttime(inRecord.getCreatetime());
        			attendance.setStatus(-3);//异常
        		} else if(inRecord == null && outRecord == null){
        			attendance.setStatus(-1);//缺勤
        		}
        		attendanceMapper.saveAttendance(attendance);
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
}
