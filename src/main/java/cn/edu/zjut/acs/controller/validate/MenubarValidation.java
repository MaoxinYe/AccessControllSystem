package cn.edu.zjut.acs.controller.validate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class MenubarValidation {

	private String modulename;
	private String modulecode;
	private String supercode;
	private String modulepage;

	@NotNull(message="功能菜单名称不能为空！")
	@Length(max=10, message="功能菜单名称不能超过10个字符！")
	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	@NotNull(message="菜单代码不能为空！")
	@Length(max=6, message="菜单不能超过6个字符！")
	public String getModulecode() {
		return modulecode;
	}

	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}	
	
	@NotNull(message="所属模块不能为空！")
	public String getSupercode() {
		return supercode;
	}

	public void setSupercode(String supercode) {
		this.supercode = supercode;
	}

	@NotNull(message="功能菜单页面不能为空！")
	@Length(max=50, message="功能菜单页面不能超过50个字符！")
	public String getModulepage() {
		return modulepage;
	}

	public void setModulepage(String modulepage) {
		this.modulepage = modulepage;
	}

}
