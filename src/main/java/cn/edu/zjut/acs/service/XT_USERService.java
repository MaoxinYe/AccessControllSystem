package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.XT_USER;

public interface XT_USERService {
	public List<XT_USER> getUserList(Map<String, Object> map);
	public List<XT_USER> getUserByUsername(String username);
	
}
