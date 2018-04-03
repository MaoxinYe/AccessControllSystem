package cn.edu.zjut.acs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.dto.AuthorityDTO;
import cn.edu.zjut.acs.mapper.AuthorityMapper;
import cn.edu.zjut.acs.model.Authority;
import cn.edu.zjut.acs.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{
	
	@Resource
	private AuthorityMapper authorityMapper;
	@Override
	public List<AuthorityDTO> getAuthorityList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return authorityMapper.getAuthorityList(map);
	}
	@Override
	@Transactional
	public void t_update(Integer roleid, List<Integer> moduleid) {
		// TODO Auto-generated method stub
		authorityMapper.deleteAuthorityByRoleid(roleid);
		for(int i=0;i<moduleid.size();i++)
		{
			Authority authority=new Authority();
			authority.setRoleid(roleid);
			authority.setModuleid(moduleid.get(i));
			authorityMapper.saveAuthority(authority);
		}
	}

}
