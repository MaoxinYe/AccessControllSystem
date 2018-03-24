package cn.edu.zjut.acs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zjut.acs.mapper.XT_USERMapper;
import cn.edu.zjut.acs.model.XT_USER;
import cn.edu.zjut.acs.service.XT_USERService;


@Service
public class XY_USERServiceImpl implements XT_USERService{

	@Resource
	private XT_USERMapper Xt_usermapper;
	
	@Override
	public List<XT_USER> getUserList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return Xt_usermapper.getUserList(map);
	}

	@Override
	public List<XT_USER> getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return Xt_usermapper.getUserByUsername(username);
	}

}
