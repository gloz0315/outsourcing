package com.sparta.outsourcing.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Restaurants {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long resturant_id;
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
  private LocalDate created_date;
  @Column @NotNull
  @LastModifiedDate
  private LocalDate updated_date;
//  @Column
//  private LocalDate deleted_date;
  @Builder
  public Restaurants(String name,String category,String address,String number){
    this.name = name;
    this.category = category;
    this.address = address;
    this.number = number;
    this.created_date = LocalDate.now();
    this.updated_date = LocalDate.now();
  }

  public Restaurants() {

  }
}
