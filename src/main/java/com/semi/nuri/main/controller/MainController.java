package com.semi.nuri.main.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semi.nuri.main.service.MainService;
import com.semi.nuri.sample.controller.SampleController;

@Controller
public class MainController {

	private static final Logger logger = Logger.getLogger(MainController.class);

	@Resource
	MainService mainService;

	@Value("${api.key}")
	private String apiKey;

	@RequestMapping(value = "/api/main/main.do")
	public @ResponseBody Map<String, Object> mainInit() {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = mainService.getSidoServiceList();
		responseMap.put("data", list);

		logger.debug(responseMap);

		return responseMap;
	}

	@RequestMapping(value = "/api/main/gunGu.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> gunGuInit(@RequestBody Map<String, Object> paramMap) {
		logger.debug(paramMap);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = mainService.getGunGuServiceList(paramMap);
		responseMap.put("data", list);

		logger.debug(responseMap);

		return responseMap;
	}

	@RequestMapping(value = "/api/main/SidogunGu.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> sidoGunGuInit(@RequestBody Map<String, Object> paramMap)
			throws Exception {
		logger.debug(paramMap);
		Map<String, Object> responseMap = new HashMap<String, Object>();

		String apiUrl = paramMap.get("url").toString() + "key=" + apiKey + "&sidoCode="+paramMap.get("sido")+ "&sggCode="+paramMap.get("gungu");
		//String apiUrl = paramMap.get("url").toString() + "&key=" + apiKey;
		System.out.println("indexOf:: "+ apiUrl.indexOf("\r\n"));
		
		System.out.println("apirul::" + apiUrl);

		URL url = new URL(apiUrl);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("CONTENT-TYPE", "json/application");
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
		String inputLine;
		String buffer = "";
		while ((inputLine = in.readLine()) != null) {
			buffer += inputLine.trim();
		}
		in.close();

		JSONParser json = new JSONParser();

		responseMap.put("data", json.parse(buffer.toString()));
		logger.debug(responseMap);

		return responseMap;
	}
}
