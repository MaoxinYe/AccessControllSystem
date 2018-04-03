package cn.edu.zjut.acs.dto;

public class AuthorityDTO {
	private Integer roleid;
	private Integer moduleid;
	private Integer modulecode;
	private Integer supercode;
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
	public Integer getModulecode() {
		return modulecode;
	}
	public void setModulecode(Integer modulecode) {
		this.modulecode = modulecode;
	}
	public Integer getSupercode() {
		return supercode;
	}
	public void setSupercode(Integer supercode) {
		this.supercode = supercode;
	}
	
}
