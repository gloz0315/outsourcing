package com.sparta.outsourcing.domain.member.repository.history;

import com.sparta.outsourcing.domain.member.model.entity.History;
import com.sparta.outsourcing.domain.member.repository.history.querydsl.HistoryRepositoryInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryJpaRepository extends JpaRepository<History, Long>,
    HistoryRepositoryInformation {

}
