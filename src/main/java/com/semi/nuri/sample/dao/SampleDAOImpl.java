package com.semi.nuri.sample.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SampleDAOImpl implements SampleDAO{

	@Inject
	private SqlSessionTemplate sqlSession;
	
	private static final String NAME_SPACE = "com.semi.nuri.mapper.sample";
	
	@Override
	public List<Map<String,Object>> test() {
		
		List<Map<String,Object>> list=sqlSession.selectList(NAME_SPACE+".selectTest");

		return list;
	}
	
	
}
