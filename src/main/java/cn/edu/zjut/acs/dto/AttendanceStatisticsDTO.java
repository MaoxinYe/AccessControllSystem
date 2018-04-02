package cn.edu.zjut.acs.dto;


public class AttendanceStatisticsDTO {
	
	private Integer personnel_id;
	private String personnel_name;
	private Integer totalNum = 0;//总数
	private Integer abnormalNum = 0;//异常	-3
	private Integer earlyLateNum = 0;//迟到+早退	-2
	private Integer absenceNum = 0;//缺勤	-1
	private Integer normalNum = 0;//正常	0
	private Integer lateNum = 0;//迟到	1
	private Integer earlyNum = 0;//早退	2
	private Integer outNum = 0;//出差	3
	private Integer leaveNum = 0;//请假	4
	private Integer offNum = 0;//调休	5
	
}
