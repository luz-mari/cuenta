package com.proyecto.ntt.cuenta.controller;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping ("/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService service;

    @GetMapping
    public List<Cuenta> listadeCuenta(){
        return service.listadeCuenta();
    }

    @GetMapping("{numeroCuenta}")
    public Cuenta obtener_Cuenta (@PathVariable Integer numeroCuenta){
        return service.obtenerCuenta(numeroCuenta);
    }


    @PostMapping
    public Mono<Cuenta> registrar(@RequestBody @Valid Cuenta a){
        return service.registrar(a);
    }

    @PutMapping
    public Cuenta actualizar (@RequestBody Cuenta b){
        return service.actualizar(b);
    }

    @DeleteMapping("{Numero_cuenta}")
    public void eliminar (@PathVariable Integer Numero_cuenta){
        service.eliminar(Numero_cuenta);

    }







}
