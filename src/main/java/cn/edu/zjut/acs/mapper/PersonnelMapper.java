package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Personnel;



public interface PersonnelMapper {
	
	Personnel getEntityByPK (Integer personnelid);
	
	/**
     * 根据DTO中的姓名和证件号码查找
     * @param YW_VISITOR
     * @return Personnel
     */
	List<Personnel> getPersonnelList(Map<String, Object> map);

	/**
	 * 根据查询条件获取人员的分页数据
	 * @param map
	 * @return
	 */
	List<Personnel> getPersonnelPageList(Map<String, Object> map);
	
	/**
	 * 获取人员信息的总记录数
	 * @return integer
	 * @param map
	 */
	Integer getRowsCount(Map<String, Object> map);
	
	/**
     * 存储人员信息
     * @param personnel
     * @return PERSONNELID
     */
	int savePersonnel(Personnel personnel);
	/**
	 * 更新人员信息
	 * @param personnel
	 */
	void updatePersonnel(Personnel personnel);
	/**
	 * 删除人员信息
	 * @param personnelid
	 */
	void deletePersonnel(Integer personnelid);



}
