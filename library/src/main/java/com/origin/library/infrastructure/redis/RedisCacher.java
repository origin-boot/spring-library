package com.origin.library.infrastructure.redis;

interface RedisCacher {
  String getKey();

  long getTimeout();
}
