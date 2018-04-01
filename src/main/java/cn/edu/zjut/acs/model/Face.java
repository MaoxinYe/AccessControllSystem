package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;


public class Face implements Serializable {

	private static final long serialVersionUID = -1450348354650313039L;
	
	private Integer faceid;
	private Personnel personnel;//内嵌实体
	private Integer personnelid;
	private String path;
	private String facefeature;
	private Date addtime;
	public Integer getFaceid() {
		return faceid;
	}
	public void setFaceid(Integer faceid) {
		this.faceid = faceid;
	}
	public Personnel getPersonnel() {
		return personnel;
	}
	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
	public Integer getPersonnelid() {
		return personnelid;
	}
	public void setPersonnelid(Integer personnelid) {
		this.personnelid = personnelid;
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
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
}
