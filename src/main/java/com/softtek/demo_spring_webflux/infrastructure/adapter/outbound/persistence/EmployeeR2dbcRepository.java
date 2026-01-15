package com.softtek.demo_spring_webflux.infrastructure.adapter.outbound.persistence;

import com.softtek.demo_spring_webflux.domain.model.Employee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeR2dbcRepository extends R2dbcRepository<Employee, Long> {
}

