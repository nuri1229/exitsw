package com.semi.nuri.sample.service;

import java.io.InputStream;
import java.net.URL;

public class APIService {

	public static String readURL() throws Exception {

		URL url = new URL("http://e-childschoolinfo.moe.go.kr/api/notice/basicInfo.do?"
				+ "key=0a76b8c490dc4fcfaff0bb1228d22921" + "&sidoCode=11" + "&sggCode=11110");
		
		InputStream is = url.openStream();
		StringBuffer result = new StringBuffer();
		int i;

		while ((i = is.read()) != -1) {
			result.append((char) i);
		}
		System.out.println(result.toString());
		return result.toString();
	}
}
