package com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.mapper;

import com.softtek.demo_spring_webflux.domain.model.Employee;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.EmployeeRequest;
import com.softtek.demo_spring_webflux.infrastructure.adapter.inbound.rest.dto.EmployeeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeMapperTest {

        private EmployeeMapper employeeMapper;
        @BeforeEach //metodo que se ejecuta antes de cada test
        void setup (){
            employeeMapper = new EmployeeMapper();
        }


    @Test
    void toDomain_DatosValidos_ConvertirCorrectamente() {
        // ============ (Preparar) ============
        // Crear un EmployeeRequest con datos de prueba
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Carlos");
        request.setLastName("García");
        request.setEmail("carlos@company.com");
        request.setPhoneNumber("934572431");
        request.setPosition("Developer");

        // ============ (Actuar) ============
        // Ejecutar el metodo que queremos probar
        Employee employee = employeeMapper.toDomain(request);

        // ============ ASSERT (Verificar) ============
        // Verificar que la conversión fue correcta
        assertNotNull(employee);  // ¿El resultado NO es null?
        assertEquals("Carlos", employee.getFirstName());  // ¿El nombre es correcto?
        assertEquals("García", employee.getLastName());
        assertEquals("carlos@company.com", employee.getEmail());
        assertEquals("934572431", employee.getPhoneNumber());
        assertEquals("Developer", employee.getPosition());
    }

    @Test
    void toDomain_PhoneNumberNull_ConvertirSinError() {
        // ============ ARRANGE ============
        EmployeeRequest request = new EmployeeRequest();
        request.setFirstName("Pedro");
        request.setLastName("López");
        request.setEmail("pedro@company.com");
        request.setPhoneNumber(null);  // ← Campo opcional null
        request.setPosition("Manager");

        // ============ ACT ============
        Employee employee = employeeMapper.toDomain(request);

        // ============ ASSERT ============
        assertNotNull(employee);
        assertEquals("Pedro", employee.getFirstName());
        assertNull(employee.getPhoneNumber());  // ← Verificar que es null
    }
}