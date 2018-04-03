package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.dto.AuthorityDTO;
import cn.edu.zjut.acs.model.Authority;

public interface AuthorityService {
	public List<AuthorityDTO> getAuthorityList(Map<String, Object> map);
	public void t_update(Integer roleid,List<Integer> moduleid);
}
