package com.sparta.outsourcing.domain.menu.model.entity;

import com.sparta.outsourcing.domain.common.entity.Timestamped;
import com.sparta.outsourcing.domain.menu.controller.dto.MenuUpdateRequestDto;
import com.sparta.outsourcing.domain.menu.model.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "menu")
@SQLDelete(sql = "update menu set deleted_date = NOW() where id = ?")
@SQLRestriction(value = "deleted_date is NULL")
public class MenuEntity extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long restaurantId;

  @Column(nullable = false, length = 20)
  private String name;

  @Column(nullable = false, length = 20)
  private String category;

  @Column(nullable = false)
  private int price;

  @Column(nullable = false)
  private String description;

  public static MenuEntity of(Long restaurantId, String name, String category, int price,
      String description) {

    return MenuEntity.builder()
        .restaurantId(restaurantId)
        .name(name)
        .category(category)
        .price(price)
        .description(description)
        .build();
  }

  public Menu toModel() {

    return Menu.builder()
        .id(id)
        .restaurantId(restaurantId)
        .name(name)
        .category(category)
        .price(price)
        .description(description)
        .build();
  }

  public void updateMenu(MenuUpdateRequestDto dto) {
    name = (dto.getName().isEmpty()) ? name : dto.getName();
    price = (dto.getPrice() == null) ? price : dto.getPrice();
    category = (dto.getCategory().isEmpty()) ? category : dto.getCategory();
    description = (dto.getDescription().isEmpty()) ? description : dto.getDescription();
  }
}
