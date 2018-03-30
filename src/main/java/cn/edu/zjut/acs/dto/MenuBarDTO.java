package cn.edu.zjut.acs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zjut.acs.model.Module;


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
