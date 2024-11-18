package com.proyecto.ntt.cuenta.service.impl;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.repository.CuentaRepository;
import com.proyecto.ntt.cuenta.repository.dao.CuentaDao;
import com.proyecto.ntt.cuenta.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service

public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository repository;

    @Override
    public List<Cuenta> listadeCuenta() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(cuentaDaos -> {Cuenta cuenta = new Cuenta();
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
    public Cuenta registrar(Cuenta a) {
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
}
