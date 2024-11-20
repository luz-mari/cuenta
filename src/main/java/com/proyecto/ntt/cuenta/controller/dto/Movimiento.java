package com.proyecto.ntt.cuenta.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Movimiento {
    public Integer id;
    public LocalDate fecha;
    public double monto;
    public String descripcion;
    public Integer numero_cuenta;
}
