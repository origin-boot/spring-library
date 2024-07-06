package com.origin.library.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bars")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bar {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "a1")
  private int a1;

  @Column(name = "a2")
  private int a2;

  @Column(name = "a3")
  private String a3;

  @Column(name = "create_time")
  private long createTime;
}

