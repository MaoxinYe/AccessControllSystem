package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Visitor;




public interface VisitorMapper {

	List<Visitor> getVisitorList(Map<String, Object> map);
	
	Integer getTotalNumber(Map<String, Object> map);
	
	Integer saveVisitor(Visitor visitor);

	Visitor getVisitorByPK(Integer key);

	void updateVisitor(Visitor visitor);

	void deleteVisitor(Visitor visitors);
	
}
