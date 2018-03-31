package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Gate;




public interface GatesMapper {

	Gate getGatesByPK (Integer key);

	List<Gate> getGatesList(Map<String, Object> map);

	Integer getTotalNumber(Map<String, Object> map);

	void saveGates(Gate gates);

	void updateGates(Gate gates);

	void deleteGates(Gate gates);
	
}
