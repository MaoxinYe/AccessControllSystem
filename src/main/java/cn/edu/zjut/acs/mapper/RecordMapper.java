package cn.edu.zjut.acs.mapper;

import java.util.List;
import java.util.Map;

import cn.edu.zjut.acs.dto.RecordDTO;
import cn.edu.zjut.acs.model.Record;




public interface RecordMapper {
	
	Record getRecordByPK (Integer id);
	
	Record getRecordWithinTenMinutes(Map<String, Object> map);

	void saveRecord(Record record);

	Integer getTotalNumber(Map<String, Object> map);

	List<RecordDTO> getRecordList(Map<String, Object> map);

	void updateRecord(Record record);
	
	List<RecordDTO> getNotOutRecordList(Map<String, Object> map);

	int getNotOutTotalNumber(Map<String, Object> map);

}
