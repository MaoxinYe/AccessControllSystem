/** 
* @Filename: ModuleService.java
* @package: cn.com.itsea.acs.dao
* Date: 2017年12月04日 下午2:14:49
* Copyright: Copyright (c) 2017, 杭州海量信息技术有限公司  All Rights Reserved.
*/
package cn.edu.zjut.acs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.mapper.ModuleMapper;
import cn.edu.zjut.acs.model.Module;
import cn.edu.zjut.acs.service.ModuleService;

/** 
 * @ClassName: ModuleService
 * @author  <a href="mailto:945922054@qq.com">余其刚</a> 
 * @date 创建时间：2017年12月04日 下午2:14:49
 * @version V1.0  
 * @since  JDK 1.7 
 */
@Service
public class ModuleServiceImpl implements ModuleService {
	
	@Resource
	private ModuleMapper moduleMapper;

	public Module getEntityByPK(Integer modulecode) {
		return moduleMapper.getEntityByPK(modulecode);
	}

	public List<Module> getModuleList(Map<String, Object> map) {
		return moduleMapper.getModuleList(map);
	}

	public Module getModuleByModulename(String modulename) {
		return moduleMapper.getModuleByModulename(modulename);
	}

	public Module getModuleByModulepage(String modulepage) {
		return moduleMapper.getModuleByModulepage(modulepage);
	}
	
	@Transactional
	public void t_insert(Module module) {
		moduleMapper.saveModule(module);
	}

	@Transactional
	public void t_update(Module module) {
		moduleMapper.updateModule(module);
	}

	@Transactional
	public void t_delete(Module module) {
		//删除模块下的菜单
		moduleMapper.deleteModuleBysupercode(module.getModulecode());
		//删除模块
		moduleMapper.deleteModule(module);
		//记录日志
	}
}
