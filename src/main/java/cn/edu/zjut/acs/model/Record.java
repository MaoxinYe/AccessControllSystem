package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class Record implements Serializable {

	private static final long serialVersionUID = -3614827631842760923L;
	
	private Integer recordid;
	private Integer gatesid;
	private Gate gates;//需内嵌实体
	private Integer inorout;
	private Integer type;
	private Integer personnelid;
	private Integer personneltype;
	private Personnel personnel;//内嵌实体
	private String path;
	private Integer faceid;
	private Face face;
	private float score;
	private Date createtime;
	public Integer getRecordid() {
		return recordid;
	}
	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}
	public Integer getGatesid() {
		return gatesid;
	}
	public void setGatesid(Integer gatesid) {
		this.gatesid = gatesid;
	}
	public Gate getGates() {
		return gates;
	}
	public void setGates(Gate gates) {
		this.gates = gates;
	}
	public Integer getInorout() {
		return inorout;
	}
	public void setInorout(Integer inorout) {
		this.inorout = inorout;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPersonnelid() {
		return personnelid;
	}
	public void setPersonnelid(Integer personnelid) {
		this.personnelid = personnelid;
	}
	public Integer getPersonneltype() {
		return personneltype;
	}
	public void setPersonneltype(Integer personneltype) {
		this.personneltype = personneltype;
	}
	public Personnel getPersonnel() {
		return personnel;
	}
	public void setPersonnel(Personnel personnel) {
		this.personnel = personnel;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getFaceid() {
		return faceid;
	}
	public void setFaceid(Integer faceid) {
		this.faceid = faceid;
	}
	public Face getFace() {
		return face;
	}
	public void setFace(Face face) {
		this.face = face;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
