package com.proyecto.ntt.cuenta.controller;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.service.CuentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Cuenta registrar(@RequestBody Cuenta a){
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
