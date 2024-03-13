package com.sparta.outsourcing.querydsltest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing.domain.member.model.Member;
import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.member.repository.member.MemberJpaRepository;
import com.sparta.outsourcing.domain.menu.model.entity.MenuEntity;
import com.sparta.outsourcing.domain.menu.model.entity.QMenuEntity;
import com.sparta.outsourcing.domain.menu.repository.MenuJpaRepository;
import com.sparta.outsourcing.domain.restaurant.entity.QRestaurants;
import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;
import com.sparta.outsourcing.domain.restaurant.repository.RestaurantsRepository;
import com.sparta.outsourcing.global.config.QueryDSLConfig;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(QueryDSLConfig.class)
public class QueryDSLTest {

  @Autowired
  private JPAQueryFactory jpaQueryFactory;

  @Autowired
  private MemberJpaRepository memberJpaRepository;

  @Autowired
  private RestaurantsRepository restaurantsRepository;

  @Autowired
  private MenuJpaRepository menuJpaRepository;

  private static MemberEntity memberEntity;
  private static Restaurants restaurants;
  private static List<MenuEntity> menuEntityList;

  @BeforeAll
  static void setup() {
    memberEntity = MemberEntity.of("test1234@naver.com", "테스트계정", "Sk@123456", "테스트 주소",
        "010-1111-1111");
    restaurants = RestaurantInit.init();
    menuEntityList = List.of(
        MenuEntity.of(1L, "테스트 음식1", "양식", 5000, "테스트 설명1"),
        MenuEntity.of(1L, "테스트 음식2", "양식", 6000, "테스트 설명2"),
        MenuEntity.of(1L, "테스트 음식3", "중식", 5500, "테스트 설명3"),
        MenuEntity.of(1L, "볶은 짜장면", "중식", 6500, "테스트 설명4"),
        MenuEntity.of(1L, "볶은 짬뽕", "중식", 7500, "테스트 설명5"),
        MenuEntity.of(1L, "볶은 탕수육", "중식", 8500, "테스트 설명6"));
  }

  @BeforeEach
  void set() {
    if (!menuJpaRepository.existsById(1L)) {
      memberJpaRepository.save(memberEntity);
    }

    if (!restaurantsRepository.existsById(1L)) {
      restaurantsRepository.save(restaurants);
    }

    if (!menuJpaRepository.existsById(1L)) {
      menuJpaRepository.saveAll(menuEntityList);
    }
  }

  @Nested
  @Order(1)
  @DisplayName("탐색 테스트")
  class CheckTest {

    @Test
    @DisplayName("유저 탐색")
    void 유저탐색() {
      Member member = memberJpaRepository.findByEmail("test1234@naver.com").orElseThrow().toModel();

      Assertions.assertNotNull(member);
      Assertions.assertEquals(member.getEmail(), memberEntity.getEmail());
    }

    @Test
    @DisplayName("가게 탐색")
    void 가게탐색() {
      Restaurants restaurants1 = restaurantsRepository.findById(1L).orElseThrow();

      Assertions.assertNotNull(restaurants1);
      Assertions.assertEquals(restaurants1.getName(), restaurants.getName());
    }

    @Test
    @DisplayName("음식 탐색")
    void 음식탐색() {
      List<MenuEntity> menuEntities = menuJpaRepository.findAll();

      Assertions.assertEquals(menuEntities.size(), 6);
    }
  }

  @Nested
  @Order(2)
  @DisplayName("QueryDSL 테스트")
  class QueryDSLCheckTest {

    @Test
    @DisplayName("Repository에 접근 하지 않고 가져오는 QueryDSL 테스트")
    void 가게_음식_테스트() {
      QMenuEntity menu = QMenuEntity.menuEntity;
      QRestaurants findRestaurant = QRestaurants.restaurants;

      Restaurants restaurants1 = jpaQueryFactory
          .selectFrom(findRestaurant)
          .where(findRestaurant.restaurantId.eq(1L))
          .fetchFirst();

      List<MenuEntity> menuEntities = jpaQueryFactory
          .selectFrom(menu)
          .where(menu.restaurantId.eq(restaurants1.getRestaurantId()))
          .fetch();

      Assertions.assertNotNull(restaurants1);

      System.out.println("=====1====");
      menuEntities.forEach(
          menuEntity -> {
            System.out.println("가게의 아이디값: " + menuEntity.getRestaurantId());
            System.out.println("음식의 이름: " + menuEntity.getName());
            System.out.println();
          });

      Assertions.assertEquals(menuEntities.size(), 6);
    }

    @Test
    @DisplayName("음식 종류 -> 볶은 포함 7000 이상 메뉴 찾기 (볶은 짜장면은 포함x)")
    void test2() {
      QMenuEntity menu = QMenuEntity.menuEntity;

      List<MenuEntity> result = jpaQueryFactory.selectFrom(menu)
          .where(menu.name.contains("볶은")
              .and(menu.price.gt(7000)))
          .orderBy(menu.price.desc())
          .fetch();

      Assertions.assertEquals(result.size(), 2);
      Assertions.assertFalse(result.stream().anyMatch(
          m -> m.getName().equals("볶은 짜장면")));
    }
  }
}
