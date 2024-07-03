package com.origin.library.infrastructure.redis;

interface RedisCacher {
  void init();

  String getKey();

  long getTimeout();
}
