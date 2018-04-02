package cn.edu.zjut.acs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.mapper.ModuleMapper;
import cn.edu.zjut.acs.model.Module;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.ModuleService;


@Service
public class ModuleServiceImpl implements ModuleService {
	
	@Resource
	private ModuleMapper moduleMapper;
	@Resource
	private LogMapper logMapper;

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
	public void t_insert(Module module, XT_LOG log) {
		moduleMapper.saveModule(module);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_update(Module module, XT_LOG log) {
		moduleMapper.updateModule(module);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_delete(Module module, XT_LOG log) {
		//删除模块下的菜单
		moduleMapper.deleteModuleBysupercode(module.getModulecode());
		//删除模块
		moduleMapper.deleteModule(module);
		//记录日志
		logMapper.saveLog(log);
	}

	@Override
	public Module getEntityByModuleid(Integer moduleid) {
		// TODO Auto-generated method stub
		return this.moduleMapper.getEntityByMoudleid(moduleid);
	}
}
