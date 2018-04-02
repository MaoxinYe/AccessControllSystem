package cn.edu.zjut.acs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zjut.acs.mapper.LogMapper;
import cn.edu.zjut.acs.mapper.WorktimeMapper;
import cn.edu.zjut.acs.model.Worktime;
import cn.edu.zjut.acs.service.WorktimeService;



@Service
public class WorktimeServiceImpl implements WorktimeService {

	@Resource
	private WorktimeMapper worktimeMapper;                                            ;
	@Resource
	private LogMapper logMapper;
	
	public List<Worktime> getWorktimeList(Map<String, Object> map) {
		return worktimeMapper.getWorktimeList(map);
	}

	public Worktime getWorktimeByPK(Integer key) {
		return worktimeMapper.getWorktimeByPK(key);
	}

	public void saveWorktime(Worktime worktime) {
		worktimeMapper.saveWorktime(worktime);
	}

	public void updateWorktime(Worktime worktime) {
		worktimeMapper.updateWorktime(worktime);
	}

	public void deleteWorktime(Worktime worktime) {
		worktimeMapper.deleteWorktime(worktime);
	}

	@Transactional
	public void t_delete(Integer[] ids) {
		for (Integer id : ids) {
			Worktime entity = this.worktimeMapper.getWorktimeByPK(id);
			worktimeMapper.deleteWorktime(entity);
		}
	}

	@Transactional
	public void t_update(List<Worktime> worktimeList, Worktime worktime) {
		for (Worktime yw_worktime : worktimeList) {
			yw_worktime.setStatus(0);
			worktimeMapper.updateWorktime(yw_worktime);
		}
		if(worktime != null){
			worktime.setStatus(1);
			worktimeMapper.updateWorktime(worktime);
		}
	}


}
