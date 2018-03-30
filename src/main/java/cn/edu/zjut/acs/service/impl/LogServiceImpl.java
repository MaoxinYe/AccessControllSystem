/** 
* @Filename: ModuleService.java
* @package: cn.com.itsea.acs.dao
* Date: 2017年12月04日 下午2:14:49
* Copyright: Copyright (c) 2017, 杭州海量信息技术有限公司  All Rights Reserved.
*/
package cn.edu.zjut.acs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.LogService;
import cn.edu.zjut.acs.support.PageCommon;


/** 
 * @ClassName: ModuleService
 * @author  <a href="mailto:945922054@qq.com">余其刚</a> 
 * @date 创建时间：2017年12月04日 下午2:14:49
 * @version V1.0  
 * @since  JDK 1.7 
 */
@Service
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogMapper logMapper;

	public XT_LOG getLogByPK(Integer id) {
		return logMapper.getLogByPK(id);
	}

	public Map<String, Object> getLogList(Map<String, Object> map,int nowPage) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		pageCommon.setTotalRows(logMapper.getTotalNumber(map));
		map.put("pageCommon", pageCommon);
		List<XT_LOG> list = logMapper.getLogList(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", list);
		return pageMap;
	}

}
