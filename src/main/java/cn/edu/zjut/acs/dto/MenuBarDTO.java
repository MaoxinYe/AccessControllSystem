/**   
 * @Filename: MenuBarDTO.java
 * @Package cn.com.itsea.sps.dto
 * Date:2017-8-15 下午4:16:22
 * Copyright: Copyright (c) 2017, 杭州海量信息技术有限公司  All Rights Reserved.
 */
package cn.edu.zjut.acs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zjut.acs.model.Module;


/**
 * @ClassName: MenuBarDTO
 * @Description: TODO(用一句话描述该文件做什么)
 * @author <a href="mailto:93330710@qq.com">陈士春</a>
 * @date 2017-8-15 下午4:16:22
 * @version V1.0
 * @since JDK 1.6
 */

public class MenuBarDTO implements Serializable {

	private static final long serialVersionUID = 5387205250927174203L;
	
	private String modulename;
	private Integer modulecode;
	private List<Module> module = new ArrayList<Module>();

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public Integer getModulecode() {
		return modulecode;
	}

	public void setModulecode(Integer modulecode) {
		this.modulecode = modulecode;
	}

	public List<Module> getModule() {
		return module;
	}

	public void setModule(List<Module> module) {
		this.module = module;
	}
}
