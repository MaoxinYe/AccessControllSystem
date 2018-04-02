package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class Worktime implements Serializable{
	
	private Integer worktimeid;
	private Date starttime;
	private Date endtime;
	private String illustrate;
	private Integer status;
	public Integer getWorktimeid() {
		return worktimeid;
	}
	public void setWorktimeid(Integer worktimeid) {
		this.worktimeid = worktimeid;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getIllustrate() {
		return illustrate;
	}
	public void setIllustrate(String illustrate) {
		this.illustrate = illustrate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
