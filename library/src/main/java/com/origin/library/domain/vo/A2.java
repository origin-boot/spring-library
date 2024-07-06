package com.origin.library.domain.vo;

import com.origin.library.domain.converter.EnumConverter;

public enum A2 implements EnumConverter.Valueable<Integer> {
  UNDEFINED(0),
  ONE(100),
  TWO(200),
  THREE(300);

  private final int value;

  private A2(int value) {
    this.value = value;
  }

  public Integer value() {
    return this.value;
  }

  public boolean equals(A2 b) {
    return value() == b.value();
  }
}
