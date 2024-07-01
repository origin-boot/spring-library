package com.origin.library.infrastructure.test;

import lombok.Data;

@Data
public class Exceptional<T> {
  T value;
  Exception exception;
}
