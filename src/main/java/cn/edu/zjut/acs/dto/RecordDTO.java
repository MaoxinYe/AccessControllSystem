package cn.edu.zjut.acs.dto;

import java.util.Date;

public class RecordDTO {

	private Integer recordid;
	private String areaname;
	private Integer inorout;
	private String name;
	private Integer personneltype;
	private Date createtime;
	
	public Integer getRecordid() {
		return recordid;
	}
	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public Integer getInorout() {
		return inorout;
	}
	public void setInorout(Integer inorout) {
		this.inorout = inorout;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPersonneltype() {
		return personneltype;
	}
	public void setPersonneltype(Integer personneltype) {
		this.personneltype = personneltype;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
