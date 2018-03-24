package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class XT_USER implements Serializable{
	private Integer xt_userid;
	private String username;
	private String password;
	private Date addtime;
	private XT_ROLE xt_role;
	public Integer getXt_userid() {
		return xt_userid;
	}
	public void setXt_userid(Integer xt_userid) {
		this.xt_userid = xt_userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public XT_ROLE getXt_role() {
		return xt_role;
	}
	public void setXt_role(XT_ROLE xt_role) {
		this.xt_role = xt_role;
	}
	
}
