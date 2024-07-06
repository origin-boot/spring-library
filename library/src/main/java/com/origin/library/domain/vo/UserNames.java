package com.origin.library.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNames {
  @JSONField
  private String firstName;

  @JSONField
  private String lastName;
}
