package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Module;

public interface ModuleMapper {
	/**
     * 保存菜单
     * @param module
     */
    void saveModule(Module module);
    /**
     * 通过父级模块编号删除模块信息
     * @param supercode
     */
    void deleteModuleBysupercode(Integer supercode);
    /**
     * 删除模块信息
     * @param module
     */
    void deleteModule(Module module);
    /**
     * 修改模块
     * @param module
     */
    void updateModule(Module module);
	/**
     * 根据主键查询模块信息
     * @param id
     * @return JC_MODULE
     */
    Module getEntityByPK (Integer modulecode);
    /**
     * 根据moduleid查询模块信息
     * @param id
     * @return JC_MODULE
     */
    Module getEntityByMoudleid (Integer modulecode);
	/**
     * 查询模块信息列表
     * @param map
     * @return List<JC_MODULE>
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

}
