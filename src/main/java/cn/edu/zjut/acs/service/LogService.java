package cn.edu.zjut.acs.service;

import java.util.Map;

import cn.edu.zjut.acs.model.XT_LOG;


public interface LogService {
	
	XT_LOG getLogByPK (Integer id);


	Map<String, Object> getLogList(Map<String, Object> map, int nowPage);

}
