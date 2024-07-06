package com.origin.library.domain.converter;

import com.alibaba.fastjson2.JSON;

public abstract class BaseConverter {
  String toJsonString(Object o) {
    if (o != null && !o.equals("")) {
      return JSON.toJSONString(o);
    }
    return null;
  }

  <T> T fromJsonString(String text, Class<T> clazz) {
    if (text != null && !text.equals("")) {
      return JSON.parseObject(text, clazz);
    }
    return null;
  }
}
