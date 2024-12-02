package com.proyecto.ntt.cuenta.service.impl;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.controller.dto.Movimiento;
import com.proyecto.ntt.cuenta.repository.MovimientoRepository;
import com.proyecto.ntt.cuenta.repository.dao.MovimientoDao;
import com.proyecto.ntt.cuenta.service.CuentaService;
import com.proyecto.ntt.cuenta.service.TipoCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovimientoServiceImplTest {

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private CuentaService cuentaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test para registrar movimiento
    @Test
    void testRegistrarMovimientoConSaldoSuficiente() {
        // Arrange
        Movimiento movimiento = new Movimiento();
        movimiento.setNumero_cuenta(123);
        movimiento.setMonto(100.0);
        movimiento.setDescripcion("Depósito");
        movimiento.setFecha(LocalDate.now());

        Cuenta cuentaMock = new Cuenta();
        cuentaMock.setNumero_cuenta(123);
        cuentaMock.setSaldo(500.0);
        cuentaMock.setTipo_cuenta(TipoCuenta.Ahorro.ordinal());

        when(cuentaService.obtenerCuenta(123)).thenReturn(cuentaMock);

        MovimientoDao movimientoDaoMock = new MovimientoDao();
        movimientoDaoMock.setId(1);
        movimientoDaoMock.setNumero_cuenta(123);
        movimientoDaoMock.setMonto(100.0);
        movimientoDaoMock.setDescripcion("Depósito");
        movimientoDaoMock.setFecha(LocalDate.now());

        when(movimientoRepository.save(any(MovimientoDao.class))).thenReturn(movimientoDaoMock);

        // Act
        Movimiento resultado = movimientoService.registrar(movimiento);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(123, resultado.getNumero_cuenta());
        assertEquals(100.0, resultado.getMonto());

        verify(cuentaService, times(1)).actualizar(any(Cuenta.class));
        verify(movimientoRepository, times(1)).save(any(MovimientoDao.class));
    }

    // Test para registrar movimiento con saldo insuficiente en cuenta de ahorro
    @Test
    void testRegistrarMovimientoConSaldoInsuficiente() {
        // Arrange
        Movimiento movimiento = new Movimiento();
        movimiento.setNumero_cuenta(123);
        movimiento.setMonto(-600.0); // Intento de retiro
        movimiento.setDescripcion("Retiro");
        movimiento.setFecha(LocalDate.now());

        Cuenta cuentaMock = new Cuenta();
        cuentaMock.setNumero_cuenta(123);
        cuentaMock.setSaldo(500.0);
        cuentaMock.setTipo_cuenta(TipoCuenta.Ahorro.ordinal());

        when(cuentaService.obtenerCuenta(123)).thenReturn(cuentaMock);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> movimientoService.registrar(movimiento));
        assertEquals("No puede realizar esta operacion", exception.getMessage());

        verify(movimientoRepository, never()).save(any(MovimientoDao.class));
    }

    // Test para listar movimientos
    @Test
    void testListMovimiento() {
        // Arrange
        MovimientoDao movimientoDao1 = new MovimientoDao();
        movimientoDao1.setId(1);
        movimientoDao1.setNumero_cuenta(123);
        movimientoDao1.setMonto(100.0);
        movimientoDao1.setDescripcion("Depósito");
        movimientoDao1.setFecha(LocalDate.now());

        MovimientoDao movimientoDao2 = new MovimientoDao();
        movimientoDao2.setId(2);
        movimientoDao2.setNumero_cuenta(123);
        movimientoDao2.setMonto(-50.0);
        movimientoDao2.setDescripcion("Retiro");
        movimientoDao2.setFecha(LocalDate.now());

        when(movimientoRepository.findAll()).thenReturn(List.of(movimientoDao1, movimientoDao2));

        // Act
        List<Movimiento> resultado = movimientoService.listMovimiento();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(123, resultado.get(0).getNumero_cuenta());
        assertEquals(100.0, resultado.get(0).getMonto());
        assertEquals("Retiro", resultado.get(1).getDescripcion());

        verify(movimientoRepository, times(1)).findAll();
    }
}
