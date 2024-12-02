package com.proyecto.ntt.cuenta.controller;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.service.CuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaControllerTest {

    @Mock
    private CuentaService cuentaService;

    @InjectMocks
    private CuentaController cuentaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListadeCuenta() {
        // Arrange
        Cuenta cuenta1 = new Cuenta();
        cuenta1.setNumero_cuenta(1);
        cuenta1.setTipo_cuenta(0);
        cuenta1.setSaldo(1000);
        cuenta1.setId_cliente(1);

        Cuenta cuenta2 = new Cuenta();
        cuenta2.setNumero_cuenta(2);
        cuenta2.setTipo_cuenta(1);
        cuenta2.setSaldo(2000);
        cuenta2.setId_cliente(2);

        List<Cuenta> cuentas = Arrays.asList(cuenta1, cuenta2);
        when(cuentaService.listadeCuenta()).thenReturn(cuentas);

        // Act
        List<Cuenta> result = cuentaController.listadeCuenta();

        // Assert
        assertEquals(2, result.size());
        verify(cuentaService, times(1)).listadeCuenta();
    }

    @Test
    void testObtenerCuenta() {
        // Arrange
        Integer numeroCuenta = 1;
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero_cuenta(numeroCuenta);
        cuenta.setTipo_cuenta(0);
        cuenta.setSaldo(1000);
        cuenta.setId_cliente(1);

        when(cuentaService.obtenerCuenta(numeroCuenta)).thenReturn(cuenta);

        // Act
        Cuenta result = cuentaController.obtener_Cuenta(numeroCuenta);

        // Assert
        assertNotNull(result);
        assertEquals(numeroCuenta, result.getNumero_cuenta());
        verify(cuentaService, times(1)).obtenerCuenta(numeroCuenta);
    }

    @Test
    void testRegistrar() {
        // Arrange
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero_cuenta(1);
        cuenta.setTipo_cuenta(0);
        cuenta.setSaldo(1000);
        cuenta.setId_cliente(1);

        when(cuentaService.registrar(any(Cuenta.class))).thenReturn(Mono.just(cuenta));

        // Act
        Mono<Cuenta> result = cuentaController.registrar(cuenta);

        // Assert
        Cuenta resultCuenta = result.block();
        assertNotNull(resultCuenta);
        assertEquals(1, resultCuenta.getNumero_cuenta());
        verify(cuentaService, times(1)).registrar(cuenta);
    }

    @Test
    void testActualizar() {
        // Arrange
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero_cuenta(1);
        cuenta.setTipo_cuenta(0);
        cuenta.setSaldo(1000);
        cuenta.setId_cliente(1);

        when(cuentaService.actualizar(any(Cuenta.class))).thenReturn(cuenta);

        // Act
        Cuenta result = cuentaController.actualizar(cuenta);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getNumero_cuenta());
        verify(cuentaService, times(1)).actualizar(cuenta);
    }

    @Test
    void testEliminar() {
        // Arrange
        Integer numeroCuenta = 1;
        doNothing().when(cuentaService).eliminar(numeroCuenta);

        // Act
        cuentaController.eliminar(numeroCuenta);

        // Assert
        verify(cuentaService, times(1)).eliminar(numeroCuenta);
    }
}
