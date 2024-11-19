package com.mihir.practicesb.services;

import com.mihir.practicesb.dto.EmployeeDTO;
import com.mihir.practicesb.entity.Employee;
import com.mihir.practicesb.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Add a new employee
    public EmployeeDTO addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setLevel(employeeDTO.getLevel());
        employee.setIsActive(employeeDTO.getIsActive());

        Employee savedEmployee = employeeRepository.save(employee);
        return new EmployeeDTO(savedEmployee.getId(), savedEmployee.getName(), savedEmployee.getLevel(), savedEmployee.getIsActive());
    }

    // Get an employee by ID
    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(emp -> new EmployeeDTO(emp.getId(), emp.getName(), emp.getLevel(), emp.getIsActive()))
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
    }

    // Get all employees
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(emp -> new EmployeeDTO(emp.getId(), emp.getName(), emp.getLevel(), emp.getIsActive()))
                .collect(Collectors.toList());
    }

    // Delete an employee by ID
    public String deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return "Employee with ID: " + id + " deleted.";
        } else {
            throw new RuntimeException("Employee not found with ID: " + id);
        }
    }
}
