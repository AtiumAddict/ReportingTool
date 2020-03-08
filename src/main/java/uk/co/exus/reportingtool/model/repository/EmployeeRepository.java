package uk.co.exus.reportingtool.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uk.co.exus.reportingtool.model.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Employee> findByUsername(String username);
}
