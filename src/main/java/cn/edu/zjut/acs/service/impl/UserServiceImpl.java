package cn.edu.zjut.acs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.mapper.UserMapper;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.model.XT_USER;
import cn.edu.zjut.acs.service.UserService;
import cn.edu.zjut.acs.support.PageCommon;


@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private LogMapper logMapper;
	
	public List<XT_USER> getUserByLoginName(String loginname) {
		return userMapper.getUserByLoginName(loginname);
	}

	public List<XT_USER> getUserList(Map<String, Object> map) {
		return userMapper.getUserList(map);
	}
	
	public Map<String, Object> getUserList(Integer nowPage,Map<String, Object> map) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		pageCommon.setTotalRows(userMapper.getTotalNumber(map));
		map.put("pageCommon", pageCommon);
		List<XT_USER> list = userMapper.getUserList(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", list);
		return pageMap;
	}

	@Transactional
	public void t_insert(XT_USER user, XT_LOG log) {
		userMapper.saveUser(user);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_update(XT_USER user, XT_LOG log) {
		userMapper.updateUser(user);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_delete(XT_USER user, XT_LOG log) {
		userMapper.deleteUser(user);
		logMapper.saveLog(log);
	}
	
	@Transactional
	public void t_delete(Integer[] ids, String session_loginname,String ipaddress) {
		for (Integer id : ids) {
			XT_USER entity = this.userMapper.getUserByPK(id);
			// 记录日志
			XT_LOG log = new XT_LOG();
			log.setUsername(session_loginname);
			log.setContent("将用户:" + entity.getUsername() + "删除了:");
			log.setClientip(ipaddress);
			userMapper.deleteUser(entity);
			logMapper.saveLog(log);
		}
	}

	public XT_USER getUserByPK(Integer key) {
		return userMapper.getUserByPK(key);
	}



}
