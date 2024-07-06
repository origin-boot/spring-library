package com.origin.library.domain.converter;

import com.origin.library.domain.vo.UserNames;

public class UserNamesConverter extends JsonConverter<UserNames> {
  UserNamesConverter() {
    super.clazz = UserNames.class;
  }
}