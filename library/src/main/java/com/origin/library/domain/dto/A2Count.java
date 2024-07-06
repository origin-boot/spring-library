package com.origin.library.domain.dto;

import com.origin.library.domain.vo.A2;
import com.origin.library.domain.converter.A2Converter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import lombok.Data;

@Data
public class A2Count {
  @Column(name = "a2")
  @Convert(converter = A2Converter.class)
  private A2 a2;

  @Column(name = "count")
  private long count;
}
