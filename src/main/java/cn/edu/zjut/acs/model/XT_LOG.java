/** 
* @Filename: YW_PERSONNEL.java
* @package: cn.com.itsea.acs.model
* Date: 2017年11月30日 下午10:22:22
* Copyright: Copyright (c) 2017, 杭州海量信息技术有限公司  All Rights Reserved.
*/
package cn.edu.zjut.acs.model;

import java.io.Serializable;
import java.util.Date;


public class XT_LOG implements Serializable {

	private static final long serialVersionUID = 6804891799179422119L;
	
	private Integer xt_logid;
	private String username;
	private String content;
	private String clientip;
	private Date addtime;
	public Integer getXt_logid() {
		return xt_logid;
	}
	public void setXt_logid(Integer xt_logid) {
		this.xt_logid = xt_logid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getClientip() {
		return clientip;
	}
	public void setClientip(String clientip) {
		this.clientip = clientip;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	
}
