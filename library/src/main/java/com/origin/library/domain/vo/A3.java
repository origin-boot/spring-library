package com.origin.library.domain.vo;

import com.origin.library.domain.converter.EnumConverter;

public enum A3 implements EnumConverter.Valueable<String> {
  UNDEFINED(""),
  ONE("One1"),
  TWO("Two2"),
  THREE("Three3");

  private final String value;

  private A3(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }

  public boolean equals(A3 b) {
    return b != null && value().equals(b.value());
  }
}
