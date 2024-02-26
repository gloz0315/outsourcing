package com.sparta.outsourcing.domain.review.model.entity;

import com.sparta.outsourcing.domain.member.model.entity.MemberEntity;
import com.sparta.outsourcing.domain.restaurant.entity.Restaurants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
@Where(clause = "deleted_date IS NULL")
@NoArgsConstructor
@Table(name = "review")
public class Review {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @Column
  private String contents;
  @NotNull
  @Column
  @Min(0)
  @Max(5)
  private int score;
  @Column
  @NotNull
  @CreatedDate
  private LocalDateTime createdDate;
  @Column
  @NotNull
  private LocalDateTime updatedDate;
  @Column
  @LastModifiedDate
  private LocalDateTime deletedDate;


  @Column(nullable = false)
  @NotNull
  private Long memberEntityId;

  @Column(nullable = false)
  @NotNull
  private Long restaurantId;

  @Builder
  public Review(String contents, int score, Long memberEntityId, Long restaurantId,
      LocalDateTime createdDate, LocalDateTime updatedDate) {
    this.contents = contents;
    this.score = score;
    this.memberEntityId = memberEntityId;
    this.restaurantId = restaurantId;
    this.createdDate = createdDate;
    this.updatedDate = updatedDate;
  }

}
