package com.bajajfinserv.health.repository;

import com.bajajfinserv.health.entity.SqlProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SqlProblemRepository extends JpaRepository<SqlProblem, Long> {
    Optional<SqlProblem> findByRegNo(String regNo);
}
