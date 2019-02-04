package com.semi.nuri.main.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.semi.nuri.main.dao.MainDao;

@Service
public class MainServiceImpl implements MainService {

	@Resource
	private MainDao dao;
	
	@Override
	public List<Map<String, Object>> getSidoServiceList() {
		return dao.getSidoServiceList();
	}

	@Override
	public List<Map<String, Object>> getGunGuServiceList(Map<String, Object> paramMap) {
		return dao.getGunGuServiceList(paramMap);
	}

}
