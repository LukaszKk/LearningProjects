package org.project.service;

import org.project.bean.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeService extends JpaRepository<Employee, Integer> {

}
