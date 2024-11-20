package com.proyecto.ntt.cuenta.service;


import com.proyecto.ntt.cuenta.controller.dto.Movimiento;

import java.util.List;

public interface MovimientoService {
    List<Movimiento> listMovimiento();
    Movimiento obtenerMovimiento (Integer id);
    Movimiento registrar (Movimiento a);

}
