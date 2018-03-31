package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;
import cn.edu.zjut.acs.model.AREA;

public class Gate implements Serializable{
	
	private static final long serialVersionUID = 6804891799179422116L;

	
	private Integer gatesid;
	private String gatesname;
	private Integer areacode;
	private Integer areacode2;
	
	private AREA area;
	private String uniqueid;
	private Integer type;
	private Integer inorout;
	private String address;
	private Date addtime;
	
	public Integer getAreacode2() {
		return areacode2;
	}
	public void setAreacode2(Integer areacode2) {
		this.areacode2 = areacode2;
	}
	public Integer getGatesid() {
		return gatesid;
	}
	public void setGatesid(Integer gatesid) {
		this.gatesid = gatesid;
	}
	public String getGatesname() {
		return gatesname;
	}
	public void setGatesname(String gatesname) {
		this.gatesname = gatesname;
	}
	public Integer getAreacode() {
		return areacode;
	}
	public void setAreacode(Integer areacode) {
		this.areacode = areacode;
	}
	public AREA getArea() {
		return area;
	}
	public void setArea(AREA area) {
		this.area = area;
	}
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getInorout() {
		return inorout;
	}
	public void setInorout(Integer inorout) {
		this.inorout = inorout;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
	
}
