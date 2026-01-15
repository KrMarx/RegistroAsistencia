package com.softtek.demo_spring_webflux.infrastructure.adapter.outbound.persistence;

import com.softtek.demo_spring_webflux.domain.model.Employee;
import com.softtek.demo_spring_webflux.domain.port.outbound.EmployeeRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeRepositoryAdapter implements EmployeeRepositoryPort {

    private final EmployeeR2dbcRepository employeeR2dbcRepository;

    public EmployeeRepositoryAdapter(EmployeeR2dbcRepository employeeR2dbcRepository) {
        this.employeeR2dbcRepository = employeeR2dbcRepository;
    }

    @Override
    public Mono<Employee> save(Employee employee) {
        return employeeR2dbcRepository.save(employee);
    }

    @Override
    public Mono<Employee> findById(Long id) {
        return employeeR2dbcRepository.findById(id);
    }

    @Override
    public Flux<Employee> findAll() {
        return employeeR2dbcRepository.findAll();
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return employeeR2dbcRepository.deleteById(id);
    }

    @Override
    public Mono<Employee> update(Employee employee) {
        return employeeR2dbcRepository.save(employee);
    }
}

