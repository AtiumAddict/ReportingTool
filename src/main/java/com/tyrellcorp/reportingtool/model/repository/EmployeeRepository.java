package com.tyrellcorp.reportingtool.model.repository;

import com.tyrellcorp.reportingtool.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Employee> findByUsername(String username);
}
