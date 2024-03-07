package com.sparta.outsourcing.domain.menu.repository;

import com.sparta.outsourcing.domain.menu.model.entity.MenuEntity;
import com.sparta.outsourcing.domain.menu.repository.querydsl.MenuRepositoryInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuJpaRepository extends JpaRepository<MenuEntity, Long>,
    MenuRepositoryInformation {

}
