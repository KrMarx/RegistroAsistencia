package com.softtek.demo_spring_webflux.application.service;

import com.softtek.demo_spring_webflux.domain.model.Employee;
import com.softtek.demo_spring_webflux.domain.port.inbound.EmployeeServicePort;
import com.softtek.demo_spring_webflux.domain.port.outbound.EmployeeRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class EmployeeService implements EmployeeServicePort {

    private final EmployeeRepositoryPort employeeRepositoryPort;

    public EmployeeService(EmployeeRepositoryPort employeeRepositoryPort) {
        this.employeeRepositoryPort = employeeRepositoryPort;
    }

    @Override
    public Mono<Employee> createEmployee(Employee employee) {
        employee.setCreatedAt(LocalDateTime.now());
        employee.setUpdatedAt(LocalDateTime.now());
        return employeeRepositoryPort.save(employee);
    }

    @Override
    public Mono<Employee> getEmployeeById(Long id) {
        return employeeRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found with id: " + id)));
    }

    @Override
    public Flux<Employee> getAllEmployees() {
        return employeeRepositoryPort.findAll();
    }

    @Override
    public Mono<Employee> updateEmployee(Long id, Employee employee) {
        return employeeRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found with id: " + id)))
                .flatMap(existingEmployee -> {
                    existingEmployee.setFirstName(employee.getFirstName());
                    existingEmployee.setLastName(employee.getLastName());
                    existingEmployee.setEmail(employee.getEmail());
                    existingEmployee.setPhoneNumber(employee.getPhoneNumber());
                    existingEmployee.setPosition(employee.getPosition());
                    existingEmployee.setUpdatedAt(LocalDateTime.now());
                    return employeeRepositoryPort.update(existingEmployee);
                });
    }

    @Override
    public Mono<Void> deleteEmployee(Long id) {
        return employeeRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Employee not found with id: " + id)))
                .flatMap(employee -> employeeRepositoryPort.deleteById(id));
    }
}

