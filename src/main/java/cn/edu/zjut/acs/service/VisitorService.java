package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.model.Visitor;




public interface VisitorService {
	
	/**
     * 根据Map
     * @param Visitor
     * @return Visitor
     */
	List<Visitor> getVisitorList (Map<String, Object> map);

	/**
     * 存储访客信息以及照片
     * @param Visitor
     * 
     */
	Integer saveVisitor(Visitor visitor);

	
	/**
     * 根据主键查询
     * @param key
     * @return Visitor
     */
	Visitor getVisitorByPK(Integer key);

	/**
     * 修改访客信息
     * @param Visitor
     * 
     */
	void updateVisitor(Visitor visitor);
	
	/**
     * 批量修改访客信息
     * @param List<Visitor>
     * 
     */
	void t_update(List<Visitor> visitors);
	/**
     * 批量修改访客信息
     * @param Integer[] id
     * 
     */
	void t_update(Integer[] ids);


	/**
	 * 根据map查询访客信息
	 * @param map
	 * @return Map<String, Object>
	 */
	Map<String, Object> getVisitorList(int nowPage,Map<String, Object> map);

	
	/**
     * 删除访客信息
     * @param Visitor
     * 
     */
	void deleteVisitor(Visitor visitor);
	
	/**
     * 批量删除访客信息
     * @param Integer[]
     * 
     */
	void t_delete(Integer[] ids);


	
}
