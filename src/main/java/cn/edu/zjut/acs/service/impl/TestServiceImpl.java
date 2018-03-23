package cn.edu.zjut.acs.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.zjut.acs.mapper.TestMapper;
import cn.edu.zjut.acs.model.Test;
import cn.edu.zjut.acs.service.TestService;

@Service
public class TestServiceImpl implements TestService{

	@Resource
	private TestMapper testMapper;
	@Override
	public List<Test> getTestList() {
		// TODO Auto-generated method stub
		return testMapper.getTestList();
	}
	
}
