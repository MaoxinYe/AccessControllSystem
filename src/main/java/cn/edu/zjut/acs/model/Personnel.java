package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;

public class Personnel implements Serializable{
	private static final long serialVersionUID = -2959522088459778054L;
	private Integer personnelid;
	private Integer departmentid;
	private String name;
	private Integer sex;
	private String tel;
	private String remarks;
	private String identification;
	private Date addtime;
	
}
