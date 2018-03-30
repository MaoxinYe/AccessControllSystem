package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.XT_USER;



public interface UserMapper {
	
	List<XT_USER> getUserByLoginName(String loginname);

	List<XT_USER> getUserList(Map<String, Object> map);

	Integer getTotalNumber(Map<String, Object> map);

	void saveUser(XT_USER user);

	void updateUser(XT_USER user);

	void deleteUser(XT_USER user);

	XT_USER getUserByPK(Integer key);
	
}
