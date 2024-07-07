package com.origin.library.infrastructure.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.origin.library.domain.Foo;
import com.origin.library.domain.Page;
import com.origin.library.domain.dto.A2Count;
import com.origin.library.domain.vo.A2;
import com.origin.library.domain.vo.FooJson;
import com.origin.library.infrastructure.util.TimeUtil;

@SpringBootTest
public class FooRepositoryTest {

  @Autowired
  private FooRepository fooRepository;

  @BeforeEach
  public void contextLoads() throws Exception {
    assertNotNull(fooRepository);
  }

  @Test
  public void testInsertFoo() throws Exception {
    Foo a = new Foo();
    a.setA1(1);
    a.setA2(A2.ONE);
    a.setA3("a3");
    a.setCreateTime(TimeUtil.getUnixTimestamp());

    FooJson json = new FooJson();
    json.setFieldA(1);
    json.setFieldB("B");
    a.setJson(json);

    Foo a2 = fooRepository.save(a);
    assertNotEquals(0, a2.getId());

    Foo a3 = fooRepository.findById(a2.getId()).get();
    assertNotNull(a3);
    assertEquals(A2.ONE, a3.getA2());
    assertNotNull(a3.getJson());
    assertEquals(1, a3.getJson().getFieldA());
    assertEquals("B", a3.getJson().getFieldB());
  }

  @Test
  public void testExamples() {
    Page<Foo> e1 = fooRepository.searchExample1("", 0, 10);
    assertNotNull(e1.getRecords());

    Iterable<Foo> e2 = fooRepository.searchExample2("");
    assertNotNull(e2);

    boolean e3 = fooRepository.editExample3();
    assertNotNull(e3);

    long e4 = fooRepository.removeExample4();
    assertNotNull(e4);

    List<A2Count> e5 = fooRepository.countExample5();
    assertNotNull(e5);
    System.out.println("countExample5: " + e5);
  }
}
