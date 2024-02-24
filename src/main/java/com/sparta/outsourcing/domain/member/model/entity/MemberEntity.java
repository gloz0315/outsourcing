package com.sparta.outsourcing.domain.member.model.entity;

import com.sparta.outsourcing.domain.common.entity.Timestamped;
import com.sparta.outsourcing.domain.member.model.Member;
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

@Entity
@Getter
@Builder
@Table(name = "member")
@SQLDelete(sql = "update member set deleted_date = NOW() where id = ?")
@SQLRestriction(value = "deleted_date is NULL")
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 40)
  private String email;

  @Column(nullable = false, length = 20)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false, length = 13)
  private String number;

  public static MemberEntity of(String email, String nickname, String password, String address, String number) {

    return MemberEntity.builder()
        .email(email)
        .nickname(nickname)
        .password(password)
        .address(address)
        .number(number)
        .build();
  }

  public Member toModel() {

    return Member.builder()
        .id(id)
        .email(email)
        .nickname(nickname)
        .password(password)
        .address(address)
        .number(number)
        .build();
  }
}
