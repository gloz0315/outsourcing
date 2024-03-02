package com.sparta.outsourcing.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestClass {
  private int number = 0;

  @BeforeEach
  void plus() {
    System.out.println("==매 메서드 실행 시==");
    number++;
  }

  @Test
  void 테스트1() {
    System.out.println(number);
  }

  @Test
  void 테스트2() {
    System.out.println(number);
  }

  @Test
  void 테스트3() {
    System.out.println(number);
  }

  @Test
  void 테스트4() {
    System.out.println(number);
  }

}
