package cn.edu.zjut.acs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.mapper.RoleMapper;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.model.XT_ROLE;
import cn.edu.zjut.acs.service.RoleService;


@Service(value="RoleServiceImpl")
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;
	@Resource
	private LogMapper logMapper;

	public List<XT_ROLE> getRoleList(Map<String, Object> map) {
		return roleMapper.getRoleList(map);
	}

	@Transactional
	public void t_insert(XT_ROLE role, XT_LOG log) {
		roleMapper.saveRole(role);
		logMapper.saveLog(log);
	}

	public XT_ROLE getRoleByPK(Integer key) {
		return roleMapper.getRoleByPK(key);
	}

	@Transactional
	public void t_update(XT_ROLE role, XT_LOG log) {
		roleMapper.updateRole(role);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_delete(XT_ROLE role, XT_LOG log) {
		roleMapper.deleteRole(role);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_delete(Integer[] ids, String ipaddress,String session_loginname) {
		for (Integer id : ids) {
			XT_ROLE entity = this.roleMapper.getRoleByPK(id);
			// 记录日志
			XT_LOG log = new XT_LOG();
			log.setUsername(session_loginname);
			log.setContent("将角色类型:" + entity.getRolename() + "删除了:");
			log.setClientip(ipaddress);
			roleMapper.deleteRole(entity);
			logMapper.saveLog(log);
		}
	}
	
	
}
