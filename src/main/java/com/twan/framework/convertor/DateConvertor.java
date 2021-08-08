package com.twan.framework.convertor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertor {
	
	public DateConvertor() {

	}
	
	public String Convert(Object date) {
		String str_date = "";
		
		str_date = new SimpleDateFormat("yyyy-MM-dd").format(date).toString();
		
		return str_date;
	}
}
