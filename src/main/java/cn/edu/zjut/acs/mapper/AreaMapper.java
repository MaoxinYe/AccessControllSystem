package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.AREA;



public interface AreaMapper {

	List<AREA> getAreaList(Map<String, Object> map);

	AREA getAreaByPK(Integer key);

	void saveArea(AREA area);
	
	void updateArea(AREA area);
	
	void deleteArea(AREA area);

}
