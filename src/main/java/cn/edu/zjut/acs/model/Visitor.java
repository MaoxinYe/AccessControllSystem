package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;



public class Visitor implements Serializable {

	private static final long serialVersionUID = -477179197604538354L;
	
	private Integer visitorid;
	private String name;
	private Integer sex;
	private String credentials;
	private String tel;
	private String personnel_name;
	private String personnel_tel;
	private Integer personnel_id;
	private String path;
	private String facefeature;
	private String remarks;
	private Date addtime;
	private Date accesstime;
	public Integer getVisitorid() {
		return visitorid;
	}
	public void setVisitorid(Integer visitorid) {
		this.visitorid = visitorid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPersonnel_name() {
		return personnel_name;
	}
	public void setPersonnel_name(String personnel_name) {
		this.personnel_name = personnel_name;
	}
	public String getPersonnel_tel() {
		return personnel_tel;
	}
	public void setPersonnel_tel(String personnel_tel) {
		this.personnel_tel = personnel_tel;
	}
	public Integer getPersonnel_id() {
		return personnel_id;
	}
	public void setPersonnel_id(Integer personnel_id) {
		this.personnel_id = personnel_id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFacefeature() {
		return facefeature;
	}
	public void setFacefeature(String facefeature) {
		this.facefeature = facefeature;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Date getAccesstime() {
		return accesstime;
	}
	public void setAccesstime(Date accesstime) {
		this.accesstime = accesstime;
	}
	
}
