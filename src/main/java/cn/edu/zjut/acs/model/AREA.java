package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class AREA implements Serializable {

	private static final long serialVersionUID = -3190408017809027142L;
	
	private Integer areacode;
	private String areaname;
	private Integer supercode = 0;
	private Date addtime;
	private Integer arealevel; 
	
	private AREA area;

	public Integer getAreacode() {
		return areacode;
	}

	public void setAreacode(Integer areacode) {
		this.areacode = areacode;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public Integer getSupercode() {
		return supercode;
	}

	public void setSupercode(Integer supercode) {
		this.supercode = supercode;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Integer getArealevel() {
		return arealevel;
	}

	public void setArealevel(Integer arealevel) {
		this.arealevel = arealevel;
	}

	public AREA getArea() {
		return area;
	}

	public void setArea(AREA area) {
		this.area = area;
	}
	
}
