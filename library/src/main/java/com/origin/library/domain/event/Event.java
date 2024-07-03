package com.origin.library.domain.event;

import java.util.UUID;

import lombok.Data;

@Data
abstract class Event {
  protected String id;

  Event() {
    this.id = UUID.randomUUID().toString();
  }
}
