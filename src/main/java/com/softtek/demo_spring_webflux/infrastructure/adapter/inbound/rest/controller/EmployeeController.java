package com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.controller;

import com.softtek.demo_spring_webflux.domain.port.inbound.EmployeeServicePort;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.EmployeeRequest;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.EmployeeResponse;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.mapper.EmployeeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeServicePort employeeServicePort;
    private final EmployeeMapper employeeMapper;

    public EmployeeController(EmployeeServicePort employeeServicePort, EmployeeMapper employeeMapper) {
        this.employeeServicePort = employeeServicePort;
        this.employeeMapper = employeeMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new employee", description = "Creates a new employee in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    public Mono<EmployeeResponse> createEmployee(
            @Valid @RequestBody EmployeeRequest request) {
        return employeeServicePort.createEmployee(employeeMapper.toDomain(request))
                .map(employeeMapper::toResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Retrieves an employee by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    public Mono<EmployeeResponse> getEmployeeById(
            @Parameter(description = "ID of the employee to retrieve", required = true)
            @PathVariable Long id) {
        return employeeServicePort.getEmployeeById(id)
                .map(employeeMapper::toResponse);
    }

    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieves all employees in the system")
    @ApiResponse(responseCode = "200", description = "List of employees retrieved successfully")
    public Flux<EmployeeResponse> getAllEmployees() {
        return employeeServicePort.getAllEmployees()
                .map(employeeMapper::toResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an employee", description = "Updates an existing employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                    content = @Content(schema = @Schema(implementation = EmployeeResponse.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content)
    })
    public Mono<EmployeeResponse> updateEmployee(
            @Parameter(description = "ID of the employee to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
        return employeeServicePort.updateEmployee(id, employeeMapper.toDomain(request))
                .map(employeeMapper::toResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an employee", description = "Deletes an employee from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content)
    })
    public Mono<Void> deleteEmployee(
            @Parameter(description = "ID of the employee to delete", required = true)
            @PathVariable Long id) {
        return employeeServicePort.deleteEmployee(id);
    }
}

