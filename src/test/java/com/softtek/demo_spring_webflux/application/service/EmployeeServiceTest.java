package com.softtek.demo_spring_webflux.application.service;

import com.softtek.demo_spring_webflux.domain.model.Employee;
import com.softtek.demo_spring_webflux.domain.port.outbound.EmployeeRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service - Tests")
class EmployeeServiceTest {

    // ========================================
    // CONFIGURACIÓN DE MOCKS
    // ========================================

    @Mock
    private EmployeeRepositoryPort employeeRepositoryPort;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        // Crear un empleado de prueba que usaremos en varios tests
        testEmployee = new Employee();
        testEmployee.setId(1L);
        testEmployee.setFirstName("Carlos");
        testEmployee.setLastName("García");
        testEmployee.setEmail("carlos@company.com");
        testEmployee.setPhoneNumber("+51987654321");
        testEmployee.setPosition("Developer");
    }

    // ========================================
    // TEST 1: Obtener todos los empleados
    // ========================================

    @Test
    @DisplayName("Debe obtener todos los empleados correctamente")
    void getAllEmployees_RetornarEmpleados() {
        // ============ ARRANGE ============
        // Crear otro empleado para tener múltiples
        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setFirstName("Ana");
        employee2.setLastName("Martínez");
        employee2.setEmail("ana@company.com");
        employee2.setPhoneNumber("+51987654322");
        employee2.setPosition("QA Engineer");

        // Configurar el MOCK: cuando se llame a findAll(), devolver estos 2 empleados
        when(employeeRepositoryPort.findAll())
                .thenReturn(Flux.just(testEmployee, employee2));

        // ============ ACT ============
        Flux<Employee> result = employeeService.getAllEmployees();

        // ============ ASSERT ============
        // StepVerifier es para testear código reactivo (Mono/Flux)
        StepVerifier.create(result)
                .expectNext(testEmployee)   // Esperamos el primer empleado
                .expectNext(employee2)      // Esperamos el segundo empleado
                .verifyComplete();          // Verificamos que el Flux completó

        // Verificar que SÍ se llamó al repository
        verify(employeeRepositoryPort, times(1)).findAll();
    }

    // ========================================
    // TEST 2: Obtener empleado por ID (éxito)
    // ========================================

    @Test
    @DisplayName("Debe obtener un empleado por ID cuando existe")
    void getEmployeeById_RetornarEmpleado() {
        // ============ ARRANGE ============
        Long employeeId = 1L;

        // Configurar el MOCK: cuando se llame con ID=1, devolver testEmployee
        when(employeeRepositoryPort.findById(employeeId))
                .thenReturn(Mono.just(testEmployee));

        // ============ ACT ============
        Mono<Employee> result = employeeService.getEmployeeById(employeeId);

        // ============ ASSERT ============
        StepVerifier.create(result)
                .expectNext(testEmployee)
                .verifyComplete();

        verify(employeeRepositoryPort, times(1)).findById(employeeId);
    }

    // ========================================
    // TEST 3: Obtener empleado por ID (no existe) - ❌ ESTE VA A FALLAR
    // ========================================

    @Test
    @DisplayName("Debe lanzar excepción cuando el empleado no existe")
    void getEmployeeById_debeLanzarExcepcion() {
        // ============ ARRANGE ============
        Long employeeId = 9L;

        // Configurar el MOCK: cuando se llame con ID=999, devolver vacío
        when(employeeRepositoryPort.findById(employeeId))
                .thenReturn(Mono.empty());

        // ============ ACT ============
        Mono<Employee> result = employeeService.getEmployeeById(employeeId);

        // ============ ASSERT ============
        // ❌ ESTE TEST VA A FALLAR si el service NO maneja el caso de "no encontrado"
        StepVerifier.create(result)
                .expectError(RuntimeException.class)  // Esperamos una excepción
                .verify();

        verify(employeeRepositoryPort, times(1)).findById(employeeId);
    }

    // ========================================
    // TEST 4: Crear empleado
    // ========================================

    @Test
    @DisplayName("Debe crear un empleado correctamente")
    void createEmployee_GuardarYRetornar() {
        // ============ ARRANGE ============
        Employee newEmployee = new Employee();
        newEmployee.setFirstName("Pedro");
        newEmployee.setLastName("López");
        newEmployee.setEmail("pedro@company.com");
        newEmployee.setPhoneNumber("+51987654323");
        newEmployee.setPosition("Manager");

        // Simular que el repository guarda y devuelve el empleado con ID asignado
        Employee savedEmployee = new Employee();
        savedEmployee.setId(3L);
        savedEmployee.setFirstName("Pedro");
        savedEmployee.setLastName("López");
        savedEmployee.setEmail("pedro@company.com");
        savedEmployee.setPhoneNumber("+51987654323");
        savedEmployee.setPosition("Manager");

        when(employeeRepositoryPort.save(any(Employee.class)))
                .thenReturn(Mono.just(savedEmployee));

        // ============ ACT ============
        Mono<Employee> result = employeeService.createEmployee(newEmployee);

        // ============ ASSERT ============
        StepVerifier.create(result)
                .expectNextMatches(emp ->
                        emp.getId() != null &&
                                emp.getFirstName().equals("Pedro") &&
                                emp.getEmail().equals("pedro@company.com")
                )
                .verifyComplete();

        verify(employeeRepositoryPort, times(1)).save(any(Employee.class));
    }

    // ========================================
    // TEST 5: Eliminar empleado
    // ========================================

    @Test
    @DisplayName("Debe eliminar un empleado correctamente")
    void deleteEmployee_EliminarCorrectamente() {
        // ============ ARRANGE ============
        Long employeeId = 19L;

        // El repository devuelve el empleado al buscarlo
        when(employeeRepositoryPort.findById(employeeId))
                .thenReturn(Mono.just(testEmployee));

        // El repository confirma la eliminación
        when(employeeRepositoryPort.deleteById(employeeId))
                .thenReturn(Mono.empty());

        // ============ ACT ============
        Mono<Void> result = employeeService.deleteEmployee(employeeId);

        // ============ ASSERT ============
        StepVerifier.create(result)
                .verifyComplete();  // Solo verificamos que completa sin error

        verify(employeeRepositoryPort, times(1)).findById(employeeId);
        verify(employeeRepositoryPort, times(1)).deleteById(employeeId);
    }
}