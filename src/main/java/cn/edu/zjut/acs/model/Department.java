package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class Department implements Serializable{
	private static final long serialVersionUID = -2959522088459778055L;
	private Integer departmentid;
	private String departmentname;
	private Date addtime;
	public Integer getDepartmentid() {
		return departmentid;
	}
	public void setDepartmentid(Integer departmentid) {
		this.departmentid = departmentid;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
}
