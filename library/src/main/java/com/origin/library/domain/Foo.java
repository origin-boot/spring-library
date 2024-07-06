package com.origin.library.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import com.origin.library.domain.converter.FooJsonConverter;
import com.origin.library.domain.converter.A2Converter;
import com.origin.library.domain.vo.A2;
import com.origin.library.domain.vo.FooJson;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "foos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "a1")
  private int a1;

  @Column(name = "a2")
  @Convert(converter = A2Converter.class)
  private A2 a2;

  @Column(name = "a3")
  private String a3;

  @Column(name = "json")
  @Convert(converter = FooJsonConverter.class)
  private FooJson json;

  @Column(name = "create_time")
  private long createTime;
}

