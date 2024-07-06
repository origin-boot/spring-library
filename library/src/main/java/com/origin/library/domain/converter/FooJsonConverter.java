package com.origin.library.domain.converter;

import com.origin.library.domain.vo.FooJson;

public class FooJsonConverter extends JsonConverter<FooJson> {
  public FooJsonConverter() {
    super.clazz = FooJson.class;
  }
}
