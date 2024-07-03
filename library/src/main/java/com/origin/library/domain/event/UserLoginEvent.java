package com.origin.library.domain.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserLoginEvent extends Event {
  private long userId;

  public UserLoginEvent(long userId) {
    super();
    this.userId = userId;
  }
}
