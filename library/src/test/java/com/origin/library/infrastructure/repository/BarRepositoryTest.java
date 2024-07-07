package com.origin.library.infrastructure.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.origin.library.domain.Bar;
import com.origin.library.domain.vo.A2;
import com.origin.library.domain.vo.A3;
import com.origin.library.infrastructure.util.TimeUtil;

@SpringBootTest
public class BarRepositoryTest {

  @Autowired
  private BarRepository barRepository;

  @BeforeEach
  public void contextLoads() throws Exception {
    assertNotNull(barRepository);
  }

  @Test
  public void testInsertBar() throws Exception {
    Bar a = new Bar();
    a.setA1(1);
    a.setA2(A2.TWO);
    a.setA3(A3.THREE);
    a.setCreateTime(TimeUtil.getUnixTimestamp());

    Bar a2 = barRepository.save(a);
    assertNotEquals(0, a2.getId());

    Bar a3 = barRepository.findById(a2.getId()).get();
    assertNotNull(a3);
    assertEquals(A2.TWO, a3.getA2());
    assertEquals(A3.THREE, a3.getA3());
  }
}
