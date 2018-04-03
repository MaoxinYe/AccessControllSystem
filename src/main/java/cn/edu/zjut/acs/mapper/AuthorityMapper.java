package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.dto.AuthorityDTO;
import cn.edu.zjut.acs.model.Authority;

public interface AuthorityMapper {
	
	void saveAuthority(Authority authority);
	
	public List<AuthorityDTO> getAuthorityList(Map<String, Object> map);
	
	void deleteAuthorityByRoleid(Integer roleid);
}
