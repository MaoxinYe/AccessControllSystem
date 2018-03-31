package cn.edu.zjut.acs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.mapper.GatesMapper;
import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.model.Gate;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.GatesService;
import cn.edu.zjut.acs.support.PageCommon;




@Service
public class GatesServiceImpl implements GatesService {

	@Resource
	private GatesMapper gatesMapper;
	@Resource
	private LogMapper logMapper;

	public Gate getGatesByPK(Integer key) {
		return gatesMapper.getGatesByPK(key);
	}

	public List<Gate> getGatesList(Map<String, Object> map) {
		return gatesMapper.getGatesList(map);
	}
	
	public Map<String, Object> getGatesList(int nowPage,Map<String, Object> map) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		pageCommon.setTotalRows(gatesMapper.getTotalNumber(map));
		map.put("pageCommon", pageCommon);
		List<Gate> list = gatesMapper.getGatesList(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", list);
		return pageMap;
	}

	@Transactional
	public void t_insert(Gate gates, XT_LOG log) {
		gatesMapper.saveGates(gates);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_update(Gate gates, XT_LOG log) {
		gatesMapper.updateGates(gates);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_delete(Gate gates, XT_LOG log) {
		gatesMapper.deleteGates(gates);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_delete(Integer[] ids, String session_loginname,String ipaddress) {
		for (Integer id : ids) {
			Gate entity = this.gatesMapper.getGatesByPK(id);
			// 记录日志
			XT_LOG log = new XT_LOG();
			log.setUsername(session_loginname);
			log.setContent("将门禁闸机:" + entity.getUniqueid() + "删除了:");
			log.setClientip(ipaddress);
			gatesMapper.deleteGates(entity);
			logMapper.saveLog(log);
		}
	}

	public void updateGates(Gate gates) {
		gatesMapper.updateGates(gates);
	}
	
}
