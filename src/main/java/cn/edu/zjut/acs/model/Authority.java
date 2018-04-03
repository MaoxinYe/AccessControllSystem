package cn.edu.zjut.acs.model;

import java.io.Serializable;

public class Authority implements Serializable{
	
	
	
	private Integer authotityid;
	private XT_ROLE role;
	private Module module;
	private Integer roleid;
	private Integer moduleid;
	
	public XT_ROLE getRole() {
		return role;
	}
	public void setRole(XT_ROLE role) {
		this.role = role;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Integer getAuthotityid() {
		return authotityid;
	}
	public void setAuthotityid(Integer authotityid) {
		this.authotityid = authotityid;
	}
	public Integer getRoleid() {
		return roleid;
	}
	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}
	public Integer getModuleid() {
		return moduleid;
	}
	public void setModuleid(Integer moduleid) {
		this.moduleid = moduleid;
	}
	
}
