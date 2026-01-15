package com.softtek.demo_spring_webflux.domain.port.outbound;

import com.softtek.demo_spring_webflux.domain.model.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepositoryPort {
    Mono<Employee> save(Employee employee);
    Mono<Employee> findById(Long id);
    Flux<Employee> findAll();
    Mono<Void> deleteById(Long id);
    Mono<Employee> update(Employee employee);
}

