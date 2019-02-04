package com.semi.nuri.main.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MainDaoImpl implements MainDao {

	@Inject
	private SqlSessionTemplate sqlSession;

	private static final String NAME_SPACE = "com.semi.nuri.mapper.main";
	
	@Override
	public List<Map<String, Object>> getSidoServiceList() {
		return sqlSession.selectList(NAME_SPACE+".getSidoServiceList");
	}

	@Override
	public List<Map<String, Object>> getGunGuServiceList(Map<String, Object> paramMap) {
		return sqlSession.selectList(NAME_SPACE+".getGunGuServiceList", paramMap);
	}
	
	
}
