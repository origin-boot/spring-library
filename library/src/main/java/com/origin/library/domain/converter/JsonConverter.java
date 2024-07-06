package com.origin.library.domain.converter;

import com.alibaba.fastjson2.JSON;

import jakarta.persistence.AttributeConverter;

public class JsonConverter<T> extends BaseConverter implements AttributeConverter<T, String> {
  protected Class<T> clazz;

  @Override
  public String convertToDatabaseColumn(T attribute) {
    if (attribute != null && !attribute.equals("")) {
      return JSON.toJSONString(attribute);
    }
    return null;
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    if (clazz == null) {
      throw new RuntimeException("super.clazz must be set in the constructor");
    }
    return JSON.parseObject(dbData, clazz);
  }

}
