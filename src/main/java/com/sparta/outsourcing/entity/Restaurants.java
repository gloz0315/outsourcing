package com.sparta.outsourcing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Table(name = "restaurants")
@SQLDelete(sql = "update restaurant set deleted_date = NOW() where restaurant_id= ?")
@NamedQuery(
    name = "Restaurants.findActiveRestaurants",
    query = "SELECT r FROM Restaurants r WHERE r.deletedDate IS NULL"
)
public class Restaurants {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long restaurantId;
  @Column(length = 50, unique = true) @NotNull
  private String name;
  @Column(length = 20) @NotNull
  private String category;
  @Column @NotNull
  private String address;
  @Column(length = 13) @NotNull
  private String number;
  @Column @NotNull
  @CreatedDate
  private LocalDateTime createdDate;
  @Column @NotNull
  @LastModifiedDate
  private LocalDateTime updatedDate;

  private LocalDateTime deletedDate;
  @Builder
  public Restaurants(String name,String category,String address,String number){
    this.name = name;
    this.category = category;
    this.address = address;
    this.number = number;
    this.createdDate = LocalDateTime.now();
    this.updatedDate = LocalDateTime.now();
    this.deletedDate = LocalDateTime.now();
  }

  public Restaurants() {

  }
}
