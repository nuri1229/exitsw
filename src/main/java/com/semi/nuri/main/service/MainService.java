package com.semi.nuri.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

public interface MainService {

	List<Map<String, Object>> getSidoServiceList();

	List<Map<String, Object>> getGunGuServiceList(Map<String, Object> paramMap);

}
