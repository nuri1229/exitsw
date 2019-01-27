package com.semi.nuri.sample.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.semi.nuri.sample.dao.SampleDAO;

@Service
public class SampleServiceImpl implements SampleService{
	
	@Resource
	private SampleDAO dao;
	
	@Override
	public List<Map<String,Object>> sampleService() {
		
		return dao.test();
	}
	

}
