package com.twan.framework.convertor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanConvertor implements AttributeConverter<Boolean, Character>{

	@Override
	public Character convertToDatabaseColumn(Boolean attribute) {
		// TODO Auto-generated method stub
		if (attribute != null) {
			if (attribute) {
				return 'Y';
			} else {
				return 'N';
			}
		}
		
		return null;
	}

	@Override
	public Boolean convertToEntityAttribute(Character dbData) {
		// TODO Auto-generated method stub
		if (dbData != null) {
			return dbData.equals('Y');
		}
		return null;
	}

}
