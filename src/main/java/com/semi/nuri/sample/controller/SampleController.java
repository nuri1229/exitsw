package com.semi.nuri.sample.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import com.semi.nuri.sample.service.SampleService;


@Controller
public class SampleController {

	private static final Logger logger = Logger.getLogger(SampleController.class);
	
	@Value("${api.key}")
	private String apiKey;
	
	@Resource
	SampleService sampleService;
	
	@RequestMapping(value = "/sample/index.do", method = RequestMethod.GET)
	public String testController() {
		return "index";
	}

	@RequestMapping(value = "/api/sample/json.do", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> jsonSample(){
		
		Map<String, Object> responseMap=new HashMap<String, Object>();
		List<Map<String,Object>> list=sampleService.sampleService();
		responseMap.put("data", list);
		logger.debug(responseMap);
		
		return responseMap;
	}
	
	@RequestMapping(value = "/api/sample/apiSample.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> apiSample(@RequestBody Map<String, Object> paramMap) throws UnsupportedEncodingException, IOException, ParseException{
		
		
		System.out.println(apiKey);
		String apiUrl=paramMap.get("url").toString()+"&key="+apiKey;
		logger.debug("sample api Url ======>" + apiUrl);
		Map<String, Object> responseMap=new HashMap<String, Object>();

		URL url = new URL(apiUrl);
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("CONTENT-TYPE","json/application"); 
	    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
	    String inputLine;
	    String buffer = "";
	    while ((inputLine = in.readLine()) != null){
	     	buffer += inputLine.trim();
	    }
	    in.close();
	    
	    JSONParser json=new JSONParser();
	    
		responseMap.put("data", json.parse(buffer.toString()));
		logger.debug(responseMap);
		
		return responseMap;
	}
}
