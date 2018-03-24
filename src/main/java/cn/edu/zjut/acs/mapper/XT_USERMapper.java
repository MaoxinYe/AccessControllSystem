package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.XT_USER;

public interface XT_USERMapper {
	public List<XT_USER> getUserList(Map<String, Object> map);
	public List<XT_USER> getUserByUsername(String username);
}
