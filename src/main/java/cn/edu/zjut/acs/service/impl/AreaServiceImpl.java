package cn.edu.zjut.acs.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*import cn.com.itsea.acs.dao.GatesDao;
import cn.com.itsea.acs.dto.FaceFeatureDTO;
import cn.com.itsea.acs.model.Gate;*/
import cn.edu.zjut.acs.mapper.AreaMapper;
import cn.edu.zjut.acs.mapper.GatesMapper;
import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.model.AREA;
import cn.edu.zjut.acs.model.Gate;
import cn.edu.zjut.acs.model.XT_LOG;
import cn.edu.zjut.acs.service.AreaService;

import com.google.common.collect.Lists;


@Service
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaMapper areaMapper;
	@Resource
	private LogMapper logMapper;
	@Resource 
	private GatesMapper gatesMapper;

	public List<AREA> getAreaList(Map<String, Object> map) {
		return areaMapper.getAreaList(map);
	}

	public AREA getAreaByPK(Integer key) {
		return areaMapper.getAreaByPK(key);
	}

	@Transactional
	public void t_insert(AREA area, XT_LOG log) {
		areaMapper.saveArea(area);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_update(AREA area, XT_LOG log) {
		areaMapper.updateArea(area);
		logMapper.saveLog(log);
	}

	@Transactional
	public void t_delete(AREA area, XT_LOG log) {
		areaMapper.deleteArea(area);
		logMapper.saveLog(log);
	}

	@Transactional
	public List<AREA> t_delete(Integer[] ids, String ipaddress,String session_loginname,ServletContext sct) {
		List<AREA> areaList = Lists.newArrayList();
		for (Integer id : ids) {
			AREA entity = this.areaMapper.getAreaByPK(id);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("areacode", entity.getAreacode());
			List<Gate> gatesList = gatesMapper.getGatesList(map);
			if(gatesList != null && gatesList.size() > 0){
				areaList.add(entity);
			} else {
				// 记录日志
				XT_LOG log = new XT_LOG();
				log.setUsername(session_loginname);
				log.setContent("将防区:" + entity.getAreaname() + "删除了:");
				log.setClientip(ipaddress);
				areaMapper.deleteArea(entity);
				logMapper.saveLog(log);
				/*Map<Integer,List<FaceFeatureDTO>> faceMaps = (Map<Integer,List<FaceFeatureDTO>>)sct.getAttribute("faceFeature");
				faceMaps.remove(entity.getAreacode());
				sct.setAttribute("faceFeature", faceMaps);*/
				//这里是更新内存中的照片数据我就不写了
			}
		}
		return areaList;
	}
}
