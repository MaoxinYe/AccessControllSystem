package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;


public class Module implements Serializable {

	private static final long serialVersionUID = -2959522088459778056L;
	private Integer moduleid;
	private Integer modulecode;
	private String modulename;
	private Integer supercode;
	private String modulepage;
	private Integer modulelevel;
	private Date addtime;
	
	
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
	public String getModulename() {
		return modulename;
	}
	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	public Integer getSupercode() {
		return supercode;
	}
	public void setSupercode(Integer supercode) {
		this.supercode = supercode;
	}
	public String getModulepage() {
		return modulepage;
	}
	public void setModulepage(String modulepage) {
		this.modulepage = modulepage;
	}
	public Integer getModulelevel() {
		return modulelevel;
	}
	public void setModulelevel(Integer modulelevel) {
		this.modulelevel = modulelevel;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
