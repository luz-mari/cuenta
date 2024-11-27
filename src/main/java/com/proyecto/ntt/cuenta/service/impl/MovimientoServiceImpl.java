package com.proyecto.ntt.cuenta.service.impl;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.controller.dto.Movimiento;
import com.proyecto.ntt.cuenta.repository.MovimientoRepository;
import com.proyecto.ntt.cuenta.repository.dao.CuentaDao;
import com.proyecto.ntt.cuenta.repository.dao.MovimientoDao;
import com.proyecto.ntt.cuenta.service.CuentaService;
import com.proyecto.ntt.cuenta.service.MovimientoService;
import com.proyecto.ntt.cuenta.service.TipoCuenta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service

public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoRepository repository;
    private final CuentaService service;

    @Override
    public List<Movimiento> listMovimiento() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(movimientoDao -> {
                    Movimiento movimiento = new Movimiento();
                    movimiento.setId(movimientoDao.getId());
                    movimiento.setDescripcion(movimientoDao.getDescripcion());
                    movimiento.setFecha(movimientoDao.getFecha());
                    movimiento.setMonto(movimientoDao.getMonto());
                    movimiento.setNumero_cuenta(movimientoDao.getNumero_cuenta());
                    return movimiento;
                }).toList();
    }

    @Override
    public Movimiento obtenerMovimiento(Integer id) {
        var movimientoEncontrado = repository.findById(id);
        if (movimientoEncontrado.isPresent()) {
            var movimiento = new Movimiento();
            var movimientoDao = movimientoEncontrado.get();
            movimiento.setId(movimientoDao.getId());
            movimiento.setNumero_cuenta(movimientoDao.getNumero_cuenta());
            movimiento.setDescripcion(movimientoDao.getDescripcion());
            movimiento.setFecha(movimientoDao.getFecha());
            movimiento.setMonto(movimiento.getMonto());
            return movimiento;
        } else {
            return null;
        }
    }

    @Override
    public Movimiento registrar(Movimiento a) {
        var cuenta = service.obtenerCuenta(a.getNumero_cuenta());
        var tipoCuenta = cuenta.getTipo_cuenta();
        var saldo = cuenta.getSaldo();
        System.out.println("Saldo:"+saldo);
        System.out.println("Cuenta:"+tipoCuenta);
        System.out.println("Ord:"+TipoCuenta.Ahorro.ordinal());
        if ( tipoCuenta == TipoCuenta.Ahorro.ordinal()){
            if (a.getMonto()<0 ){
                if ((a.getMonto()+saldo)<0){
                    throw new RuntimeException("No puede realizar esta operacion");
                }
            }
        }
        if ( tipoCuenta == TipoCuenta.Corriente.ordinal()){
            if (a.getMonto()<0 ){
                if ((a.getMonto()+saldo)<-500){
                    throw new RuntimeException("No puede realizar esta operacion");
                }else{
                }
            }
        }
        saldo = a.getMonto()+saldo;
        var movimientoDao = new MovimientoDao();
        movimientoDao.setDescripcion(a.getDescripcion());
        movimientoDao.setNumero_cuenta(a.getNumero_cuenta());
        movimientoDao.setMonto(a.getMonto());
        movimientoDao.setFecha(a.getFecha());
        var movimientoRegistrado = repository.save(movimientoDao);
        System.out.println("Saldo actuals:"+saldo);
        cuenta.setSaldo(saldo);
        service.actualizar(cuenta);
        Movimiento movimiento = new Movimiento();
        movimiento.setId(movimientoRegistrado.getId());
        movimiento.setNumero_cuenta(movimientoRegistrado.getNumero_cuenta());
        movimiento.setDescripcion(movimientoRegistrado.getDescripcion());
        movimiento.setFecha(movimientoRegistrado.getFecha());
        movimiento.setMonto(movimientoRegistrado.getMonto());
        return movimiento;
    }
}
