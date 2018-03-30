package cn.edu.zjut.acs.controller.validate;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;



public class ModuleValidation {

	private String modulename;
	private String modulecode;
	
	@NotNull(message="功能模块名称不能为空！")
	@Length(max=10, message="功能模块名称不能超过10个字符！")
	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	@NotNull(message="模块代码不能为空！")
	@Length(max=4, message="模块不能超过4个字符！")
	public String getModulecode() {
		return modulecode;
	}

	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}
}
