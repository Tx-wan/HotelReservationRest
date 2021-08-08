package com.twan.framework.convertor;

import com.twan.framework.entity.Gender;

public class GenderConvertor {
	public static Gender Convertor(String str_gender) {
		Gender g = null;
		
		if (str_gender.toLowerCase().equals("male")) {
			g = Gender.MALE;
		} else if (str_gender.toLowerCase().equals("female")) {
			g = Gender.FEMALE;
		} else {
			g = Gender.NONIDENTIFIED;
		}
		
		return g;
	}

	public static Gender ConvertorFromInteger(String str_gender) {
		Gender g = null;
		
		if (str_gender.toLowerCase().equals("0")) {
			g = Gender.MALE;
		} else if (str_gender.toLowerCase().equals("1")) {
			g = Gender.FEMALE;
		} else {
			g = Gender.NONIDENTIFIED;
		}
		
		return g;
	}

}
