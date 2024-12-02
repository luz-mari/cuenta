package com.proyecto.ntt.cuenta.service.impl;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.repository.CuentaRepository;
import com.proyecto.ntt.cuenta.repository.dao.CuentaDao;
import com.proyecto.ntt.cuenta.service.dto.Cliente;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuentaServiceImplTest {

    @Mock
    private CuentaRepository repository;

    @Mock
    private WebClient webClient;

    @InjectMocks
    private CuentaServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListadeCuenta() {
        // Mock de datos
        CuentaDao cuentaDao = new CuentaDao();
        cuentaDao.setNumero_cuenta(1);
        cuentaDao.setTipo_cuenta(0);
        cuentaDao.setSaldo(1000.0);
        cuentaDao.setId_cliente(1);

        when(repository.findAll()).thenReturn(List.of(cuentaDao));

        // Prueba
        List<Cuenta> cuentas = service.listadeCuenta();

        // Validaciones
        assertNotNull(cuentas);
        assertEquals(1, cuentas.size());
        assertEquals(1, cuentas.get(0).getNumero_cuenta());
    }

    @Test
    void testObtenerCuenta() {
        // Mock de datos
        CuentaDao cuentaDao = new CuentaDao();
        cuentaDao.setNumero_cuenta(1);
        cuentaDao.setTipo_cuenta(0);
        cuentaDao.setSaldo(1000.0);
        cuentaDao.setId_cliente(1);

        when(repository.findById(1)).thenReturn(Optional.of(cuentaDao));

        // Prueba
        Cuenta cuenta = service.obtenerCuenta(1);

        // Validaciones
        assertNotNull(cuenta);
        assertEquals(1, cuenta.getNumero_cuenta());
        assertEquals(1000.0, cuenta.getSaldo());
    }

    /*@Test
    void testRegistrarCuentaClienteNoExistente() {
        // Mock de cliente no existente
        when(webClient.get()).thenReturn(mock(WebClient.RequestHeadersUriSpec.class));
        when(webClient.get()
                .uri("/clientes/{id}", 99)
                .retrieve()
                .bodyToMono(Cliente.class))
                .thenReturn(Mono.empty());

        Cuenta cuenta = new Cuenta();
        cuenta.setId_cliente(99);
        cuenta.setTipo_cuenta(0);
        cuenta.setSaldo(500.0);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            service.registrar(cuenta).block();
        });

        assertEquals("El cliente no existe", exception.getMessage());
    }*/

    @Test
    void testEliminar() {
        // Mock de datos
        CuentaDao cuentaDao = new CuentaDao();
        cuentaDao.setNumero_cuenta(1);

        when(repository.findById(1)).thenReturn(Optional.of(cuentaDao));
        doNothing().when(repository).delete(cuentaDao);

        // Prueba
        service.eliminar(1);

        // Verificaciones
        verify(repository, times(1)).delete(cuentaDao);
    }

    @Test
    void testActualizar() {
        // Mock de datos
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero_cuenta(1);
        cuenta.setTipo_cuenta(1);
        cuenta.setSaldo(2000.0);
        cuenta.setId_cliente(2);

        CuentaDao cuentaDao = new CuentaDao();
        cuentaDao.setNumero_cuenta(1);
        cuentaDao.setTipo_cuenta(1);
        cuentaDao.setSaldo(2000.0);
        cuentaDao.setId_cliente(2);

        when(repository.save(any(CuentaDao.class))).thenReturn(cuentaDao);

        // Prueba
        Cuenta resultado = service.actualizar(cuenta);

        // Validaciones
        assertNotNull(resultado);
        assertEquals(1, resultado.getNumero_cuenta());
        assertEquals(2000.0, resultado.getSaldo());
    }
}
