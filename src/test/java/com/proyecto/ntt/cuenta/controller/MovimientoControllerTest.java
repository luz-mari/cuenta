package com.proyecto.ntt.cuenta.controller;

import java.util.List;
import com.proyecto.ntt.cuenta.controller.dto.Movimiento;
import com.proyecto.ntt.cuenta.service.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoControllerTest {

    @InjectMocks
    private MovimientoController movimientoController;

    @Mock
    private MovimientoService movimientoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para el método listMovimiento
    @Test
    void testListMovimiento() {
        // Arrange
        Movimiento movimiento1 = new Movimiento();
        movimiento1.setId(1);
        movimiento1.setNumero_cuenta(123);
        movimiento1.setMonto(1000.0);
        movimiento1.setDescripcion("Depósito");

        Movimiento movimiento2 = new Movimiento();
        movimiento2.setId(2);
        movimiento2.setNumero_cuenta(123);
        movimiento2.setMonto(-500.0);
        movimiento2.setDescripcion("Retiro");

        when(movimientoService.listMovimiento()).thenReturn(List.of(movimiento1, movimiento2));

        // Act
        var resultado = movimientoController.listMovimiento();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(movimientoService, times(1)).listMovimiento();
    }

    // Test para obtener un movimiento por ID
    @Test
    void testObtenerMovimiento() {
        // Arrange
        Movimiento movimiento = new Movimiento();
        movimiento.setId(1);
        movimiento.setNumero_cuenta(123);
        movimiento.setMonto(1000.0);
        movimiento.setDescripcion("Depósito");

        when(movimientoService.obtenerMovimiento(1)).thenReturn(movimiento);

        // Act
        var resultado = movimientoController.obtenerMovimiento(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Depósito", resultado.getDescripcion());
        verify(movimientoService, times(1)).obtenerMovimiento(1);
    }

    // Test para registrar un movimiento (deposito)
    @Test
    void testRegistrarDeposito() {
        // Arrange
        Movimiento movimiento = new Movimiento();
        movimiento.setNumero_cuenta(123);
        movimiento.setMonto(200.0);
        movimiento.setDescripcion("Depósito");

        Movimiento movimientoRegistrado = new Movimiento();
        movimientoRegistrado.setId(1);
        movimientoRegistrado.setNumero_cuenta(123);
        movimientoRegistrado.setMonto(200.0);
        movimientoRegistrado.setDescripcion("Depósito");

        when(movimientoService.registrar(movimiento)).thenReturn(movimientoRegistrado);

        // Act
        var resultado = movimientoController.realizarDeposito(movimiento);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Depósito", resultado.getDescripcion());
        verify(movimientoService, times(1)).registrar(movimiento);
    }

    // Test para registrar un movimiento (retiro)
    @Test
    void testRegistrarRetiro() {
        // Arrange
        Movimiento movimiento = new Movimiento();
        movimiento.setNumero_cuenta(123);
        movimiento.setMonto(100.0);
        movimiento.setDescripcion("Retiro");

        Movimiento movimientoRegistrado = new Movimiento();
        movimientoRegistrado.setId(1);
        movimientoRegistrado.setNumero_cuenta(123);
        movimientoRegistrado.setMonto(-100.0);
        movimientoRegistrado.setDescripcion("Retiro");

        when(movimientoService.registrar(movimiento)).thenReturn(movimientoRegistrado);

        // Act
        var resultado = movimientoController.realizarRetiro(movimiento);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(-100.0, resultado.getMonto());
        assertEquals("Retiro", resultado.getDescripcion());
        verify(movimientoService, times(1)).registrar(movimiento);
    }

    // Test para manejar IllegalArgumentException (monto <= 0) en el depósito
    @Test
    void testRealizarDepositoMontoInvalido() {
        // Arrange
        Movimiento movimiento = new Movimiento();
        movimiento.setNumero_cuenta(123);
        movimiento.setMonto(-50.0);  // Monto negativo para prueba
        movimiento.setDescripcion("Depósito");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            movimientoController.realizarDeposito(movimiento);
        });

        assertEquals("El monto debe ser mayor a cero.", exception.getMessage());
    }

    // Test para manejar IllegalArgumentException (monto <= 0) en el retiro
    @Test
    void testRealizarRetiroMontoInvalido() {
        // Arrange
        Movimiento movimiento = new Movimiento();
        movimiento.setNumero_cuenta(123);
        movimiento.setMonto(-50.0);  // Monto negativo para prueba
        movimiento.setDescripcion("Retiro");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            movimientoController.realizarRetiro(movimiento);
        });

        assertEquals("El monto debe ser mayor a cero.", exception.getMessage());
    }
}
