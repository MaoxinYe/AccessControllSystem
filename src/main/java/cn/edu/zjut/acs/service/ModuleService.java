package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Module;
import cn.edu.zjut.acs.model.XT_LOG;

public interface ModuleService {
	/**
     * 根据主键查询模块信息
     * @param id
     * @return Module
     */
	Module getEntityByPK (Integer modulecode);
	/**
     * 根据moduleid查询模块信息
     * @param id
     * @return Module
     */
	Module getEntityByModuleid (Integer moduleid);
	/**
     * 查询模块信息列表
     * @param map
     * @return List<Module>
     */
	List<Module> getModuleList (Map<String,Object> map);

	List<Module> getModuleListWithAhthority (Map<String,Object> map);
	
	/**
	 * 通过模块名称查询模块信息
	 * @param modulename
	 * @return
	 */
	Module getModuleByModulename(String modulename);

	/**
	 * 通过菜单对应页面查询模块信息
	 * @param modulepage
	 * @return
	 */
	Module getModuleByModulepage(String modulepage);
	 /**
     * 添加模块信息
     * @param module
     * @param log
     */
    void t_insert(Module module, XT_LOG log);
    /**
     * 修改模块信息列表
     * @param module
     * @param log
     */
    void t_update(Module module, XT_LOG log);
    /**
     * 删除模块信息及其下属模块信息
     * @param module
     * @param rzjl
     */
    void t_delete(Module module, XT_LOG log);

}
