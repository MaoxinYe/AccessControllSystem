package cn.edu.zjut.acs.dto;

import java.io.Serializable;





public class PersonnelDTO implements Serializable {

	private static final long serialVersionUID = -7523386950375076744L;
	private String credentials;
	private String picpath;
	private String feature;
	private Integer status;
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}