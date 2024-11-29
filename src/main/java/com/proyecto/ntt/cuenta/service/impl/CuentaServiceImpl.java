package com.proyecto.ntt.cuenta.service.impl;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.repository.CuentaRepository;
import com.proyecto.ntt.cuenta.repository.dao.CuentaDao;
import com.proyecto.ntt.cuenta.service.CuentaService;
import com.proyecto.ntt.cuenta.service.dto.Cliente;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class CuentaServiceImpl implements CuentaService {
    public CuentaServiceImpl(CuentaRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    private final CuentaRepository repository;
    private final WebClient webClient;

    @Override
    public List<Cuenta> listadeCuenta() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(cuentaDaos -> {
                    Cuenta cuenta = new Cuenta();
                    cuenta.setNumero_cuenta(cuentaDaos.getNumero_cuenta());
                    cuenta.setTipo_cuenta(cuentaDaos.getTipo_cuenta());
                    cuenta.setSaldo(cuentaDaos.getSaldo());
                    cuenta.setId_cliente(cuentaDaos.getId_cliente());
                    return cuenta;
                }).toList();
    }

    @Override
    public Cuenta obtenerCuenta(Integer numeroCuenta) {
        var cuentaEncontrada = repository.findById(numeroCuenta);
        if (cuentaEncontrada.isPresent()) {
            var cuenta = new Cuenta();
            var cuentaDao = cuentaEncontrada.get();
            cuenta.setNumero_cuenta(cuentaDao.getNumero_cuenta());
            cuenta.setTipo_cuenta(cuentaDao.getTipo_cuenta());
            cuenta.setId_cliente(cuentaDao.getId_cliente());
            cuenta.setSaldo(cuentaDao.getSaldo());
            return cuenta;
        } else {
            return null;
        }
    }

    @Override
    public Mono<Cuenta> registrar(Cuenta a) {
        var idCliente = a.getId_cliente();
        return obtenerClientePorId(idCliente).map(cliente -> {
            if(cliente==null){
                log.error("Cliente no encontrado");
                throw new RuntimeException("El cliente no existe");
            }
            if(a.getTipo_cuenta() != 0 && a.getTipo_cuenta() != 1) {
                throw new RuntimeException("El tipo de cuenta no es valido");
            }
            var cuentaDao = new CuentaDao();
            cuentaDao.setTipo_cuenta(a.getTipo_cuenta());
            cuentaDao.setSaldo(a.getSaldo());
            cuentaDao.setId_cliente(a.getId_cliente());
            var cuentaRegistrada = repository.save(cuentaDao);
            Cuenta cuenta = new Cuenta();
            cuenta.setNumero_cuenta(cuentaRegistrada.getNumero_cuenta());
            cuenta.setTipo_cuenta(cuentaRegistrada.getTipo_cuenta());
            cuenta.setSaldo(cuentaRegistrada.getSaldo());
            cuenta.setId_cliente(cuentaRegistrada.getId_cliente());
            return cuenta;
        });
    }

    @Override
    public Cuenta actualizar(Cuenta a) {
        var cuentaDao = new CuentaDao();
        cuentaDao.setNumero_cuenta(a.getNumero_cuenta());
        cuentaDao.setTipo_cuenta(a.getTipo_cuenta());
        cuentaDao.setSaldo(a.getSaldo());
        cuentaDao.setId_cliente(a.getId_cliente());
        var cuentaRegistrada = repository.save(cuentaDao);
        Cuenta cuenta = new Cuenta();
        cuenta.setNumero_cuenta(cuentaRegistrada.getNumero_cuenta());
        cuenta.setTipo_cuenta(cuentaRegistrada.getTipo_cuenta());
        cuenta.setSaldo(cuentaRegistrada.getSaldo());
        cuenta.setId_cliente(cuentaRegistrada.getId_cliente());
        return cuenta;
    }
    @Override
    public void eliminar(Integer Numero_cuenta) {
        var cuentaEncontrada = repository.findById(Numero_cuenta);
        if (cuentaEncontrada.isPresent()){
            repository.delete(cuentaEncontrada.get());
        }
    }

    public Mono<Cliente> obtenerClientePorId(Integer id) {
        return webClient.get() // Usamos GET para obtener los datos
                .uri("/clientes/{id}", id) // El id se pasa como parte de la URL
                .retrieve() // Recupera la respuesta
                .onStatus(
                        status -> status.is4xxClientError(), // Manejo de errores 4xx
                        response -> Mono.error(new RuntimeException("Error del cliente: " + response.statusCode()))
                )
                .onStatus(
                        status -> status.is5xxServerError(), // Manejo de errores 5xx
                        response -> Mono.error(new RuntimeException("Error del servidor: " + response.statusCode()))
                )
                .bodyToMono(Cliente.class)
                // Mapea la respuesta a la clase Cliente
                .switchIfEmpty(Mono.error(new RuntimeException("El cuerpo esta vacio")));
    }
}
