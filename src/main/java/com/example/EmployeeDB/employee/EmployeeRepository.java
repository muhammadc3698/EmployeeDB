package com.example.EmployeeDB.employee;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //This is a DAO(Data Access Object)
public interface EmployeeRepository extends JpaRepository<Employee, Long> //Specify the type of the repository and the type of ID
{
    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByPhone(long phone);
}