package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class Gate implements Serializable{
	
	private static final long serialVersionUID = 6804891799179422116L;
	private Integer gateid;
	private String gatename;
	private Integer inorout;
	private Date addtime;
	private String uniqueid;
	public Integer getGateid() {
		return gateid;
	}
	public void setGateid(Integer gateid) {
		this.gateid = gateid;
	}
	public String getGatename() {
		return gatename;
	}
	public void setGatename(String gatename) {
		this.gatename = gatename;
	}
	public Integer getInorout() {
		return inorout;
	}
	public void setInorout(Integer inorout) {
		this.inorout = inorout;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	
	
}
