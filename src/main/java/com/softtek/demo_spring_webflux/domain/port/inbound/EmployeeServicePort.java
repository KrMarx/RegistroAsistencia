package com.softtek.demo_spring_webflux.domain.port.inbound;

import com.softtek.demo_spring_webflux.domain.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeServicePort {
    Mono<Employee> createEmployee(Employee employee);
    Mono<Employee> getEmployeeById(Long id);
    Flux<Employee> getAllEmployees();
    Mono<Employee> updateEmployee(Long id, Employee employee);
    Mono<Void> deleteEmployee(Long id);
}

