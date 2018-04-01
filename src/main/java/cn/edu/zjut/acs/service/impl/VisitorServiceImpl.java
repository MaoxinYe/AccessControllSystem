package cn.edu.zjut.acs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.mapper.VisitorMapper;
import cn.edu.zjut.acs.model.Visitor;
import cn.edu.zjut.acs.service.VisitorService;
import cn.edu.zjut.acs.support.PageCommon;



@Service
public class VisitorServiceImpl implements VisitorService {

	@Resource
	private VisitorMapper visitorMapper;
	

	public List<Visitor> getVisitorList(Map<String, Object> map) {
		return visitorMapper.getVisitorList(map);
	}

	public Integer saveVisitor(Visitor visitor) {
		return visitorMapper.saveVisitor(visitor);
	}

	public Visitor getVisitorByPK(Integer key) {
		return visitorMapper.getVisitorByPK(key);
	}

	public void updateVisitor(Visitor visitor) {
		visitorMapper.updateVisitor(visitor);
	}
	
	@Transactional
	public void t_update(List<Visitor> visitors) {
		for (Visitor visitor : visitors) {
			visitorMapper.updateVisitor(visitor);
		}
	}
	@Transactional
	public void t_update(Integer[] ids) {
		for (Integer id : ids) {
			Visitor visitor = visitorMapper.getVisitorByPK(id);
			//visitor.setStatus(3);
			visitorMapper.updateVisitor(visitor);
		}
	}

	public Map<String, Object> getVisitorList(int nowPage,Map<String, Object> map) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		pageCommon.setTotalRows(visitorMapper.getTotalNumber(map));
		map.put("pageCommon", pageCommon);
		List<Visitor> list = visitorMapper.getVisitorList(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", list);
		return pageMap;
	}

	public void deleteVisitor(Visitor visitor) {
		visitorMapper.deleteVisitor(visitor);
	}

	public void t_delete(Integer[] ids) {
		for (Integer id : ids) {
			Visitor visitor = visitorMapper.getVisitorByPK(id);
			if(visitor != null){
				visitorMapper.deleteVisitor(visitor);
			}
		}
	}
}
