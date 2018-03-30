package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class XT_ROLE implements Serializable{
	
	private static final long serialVersionUID = 6804891799179422118L;
	private Integer roleid;
	private String rolename;
	private Date addtime;
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
}
