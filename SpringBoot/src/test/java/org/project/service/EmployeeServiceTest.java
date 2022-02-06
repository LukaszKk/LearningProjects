package org.project.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.project.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(classes = org.project.Main.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testInsert() {
        Employee employee = new Employee("asd", "asdasdfg@asdas.com");
        employeeService.save(employee);
        Assertions.assertEquals(1, employeeService.findAll().size());
    }

    @Test
    public void testThatFindAllRetrievesEmptyEmployeesList() {
        Assertions.assertEquals(0, employeeService.findAll().size());
    }
}
