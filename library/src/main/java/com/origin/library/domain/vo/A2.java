package com.origin.library.domain.vo;

import com.origin.library.domain.converter.EnumConverter;

public enum A2 implements EnumConverter.Valueable<Integer> {
  UNDEFINED(0),
  ONE(1),
  TWO(2),
  THREE(3);

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
