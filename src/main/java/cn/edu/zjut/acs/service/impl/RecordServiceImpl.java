package cn.edu.zjut.acs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zjut.acs.dto.RecordDTO;
import cn.edu.zjut.acs.mapper.RecordMapper;
import cn.edu.zjut.acs.model.Record;
import cn.edu.zjut.acs.service.RecordService;
import cn.edu.zjut.acs.support.PageCommon;



@Service
public class RecordServiceImpl implements RecordService {
	
	@Resource
	private RecordMapper recordMapper;

	public Record getRecordByPK(Integer id) {
		return recordMapper.getRecordByPK(id);
	}

	public Record getRecordWithinTenMinutes(Map<String, Object> map) {
		return recordMapper.getRecordWithinTenMinutes(map);
	}

	public void saveRecord(Record record) {
		recordMapper.saveRecord(record);
	}

	public Map<String, Object> getRecordList(Map<String, Object> map,int nowPage) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		pageCommon.setTotalRows(recordMapper.getTotalNumber(map));
		map.put("pageCommon", pageCommon);
		List<RecordDTO> list = recordMapper.getRecordList(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", list);
		return pageMap;
	}
	public List<RecordDTO> getRecordList(Map<String, Object> map) {
		return recordMapper.getRecordList(map);
	}

	public void updateRecord(Record record) {
		recordMapper.updateRecord(record);
	}

	public  Map<String, Object> getNotOutRecordList(int nowPage,Map<String, Object> map) {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		PageCommon pageCommon = new PageCommon();
		pageCommon.setCurrentPage(nowPage);
		pageCommon.setPageSize(20);
		pageCommon.setTotalRows(recordMapper.getNotOutTotalNumber(map));
		map.put("pageCommon", pageCommon);
		List<RecordDTO> list = recordMapper.getNotOutRecordList(map);
		pageMap.put("page", pageCommon);
		pageMap.put("info", list);
		return pageMap;
	}


}
