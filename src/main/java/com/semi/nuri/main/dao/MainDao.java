package com.semi.nuri.main.dao;

import java.util.List;
import java.util.Map;

public interface MainDao {

	List<Map<String, Object>> getSidoServiceList();

	List<Map<String, Object>> getGunGuServiceList(Map<String, Object> paramMap);
}
