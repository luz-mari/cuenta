package com.proyecto.ntt.cuenta.controller;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.controller.dto.Movimiento;
import com.proyecto.ntt.cuenta.service.MovimientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor

public class MovimientoController {

    private final MovimientoService service;

    @GetMapping
    public List<Movimiento> listMovimiento(){
        return service.listMovimiento();

    }

    @GetMapping("{id}")
    public Movimiento obtenerMovimiento (@PathVariable Integer id){
        return service.obtenerMovimiento(id);
    }


    @PostMapping
    public Movimiento registrar(@RequestBody @Valid Movimiento a){
        return service.registrar(a);
    }

    @PostMapping("/deposito")
    public Movimiento realizarDeposito(@RequestBody @Valid Movimiento a){
        if (a.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        return service.registrar(a);
    }

    @PostMapping("/retiro")
    public Movimiento realizarRetiro(@RequestBody @Valid Movimiento a){
        if (a.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        a.setMonto(-Math.abs(a.getMonto())); // Guardamos como negativo
        return service.registrar(a);
    }



}
