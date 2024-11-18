package com.proyecto.ntt.cuenta.service;



import com.proyecto.ntt.cuenta.controller.dto.Cuenta;

import java.util.List;

public interface CuentaService {
    List<Cuenta> listadeCuenta();
    Cuenta obtenerCuenta (Integer numeroCuenta);
    Cuenta registrar (Cuenta a);
    Cuenta actualizar (Cuenta a);
    void eliminar (Integer Numero_cuenta);

}