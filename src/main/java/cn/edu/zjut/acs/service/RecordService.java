package cn.edu.zjut.acs.service;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.dto.RecordDTO;
import cn.edu.zjut.acs.model.Record;


public interface RecordService {
	/**
     * 根据主键查询模块信息
     * @param id
     * @return Record
     */
	Record getRecordByPK (Integer id);

	/**
     * 根据map查询10分钟内是否有出入记录
     * @param map
     * @return Record
     */
	Record getRecordWithinTenMinutes(Map<String, Object> map);

	/**
     * 插入出入记录
     * @param Record
     */
	void saveRecord(Record record);
	
	/**
     * 修改出入记录
     * @param Record
     */
	void updateRecord(Record record);

	/**
	 * 根据map查询出入记录
	 * 带分页
	 * @param map
	 * @return Map<String, Object>
	 */
	Map<String, Object> getRecordList(Map<String, Object> map,int nowPage);
	List<RecordDTO> getRecordList(Map<String, Object> map);
	 Map<String, Object> getNotOutRecordList(int nowPage,Map<String, Object> map);
	
}
