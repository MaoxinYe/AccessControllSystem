package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class Attendance implements Serializable{
	private static final long serialVersionUID = -4899150281735591922L;

	private Integer id;
	private Integer personnel_id;
	private String personnel_name;
	private Integer status;
	private Date createtime;
	private Date starttime;
	private Date endtime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPersonnel_id() {
		return personnel_id;
	}
	public void setPersonnel_id(Integer personnel_id) {
		this.personnel_id = personnel_id;
	}
	public String getPersonnel_name() {
		return personnel_name;
	}
	public void setPersonnel_name(String personnel_name) {
		this.personnel_name = personnel_name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
}
