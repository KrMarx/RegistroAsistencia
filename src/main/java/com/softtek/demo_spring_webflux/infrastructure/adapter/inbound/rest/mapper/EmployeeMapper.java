package com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.mapper;

import com.softtek.demo_spring_webflux.domain.model.Employee;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.EmployeeRequest;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.EmployeeResponse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toDomain(EmployeeRequest request) {
        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setEmail(request.getEmail());
        employee.setPhoneNumber(request.getPhoneNumber());
        employee.setPosition(request.getPosition());
        return employee;
    }

    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getPhoneNumber(),
                employee.getPosition(),
                employee.getCreatedAt(),
                employee.getUpdatedAt()
        );
    }
}

