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



}
