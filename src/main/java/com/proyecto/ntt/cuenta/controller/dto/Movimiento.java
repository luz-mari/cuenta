package com.proyecto.ntt.cuenta.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Movimiento {
    public Integer id;
    @NotNull
    public LocalDate fecha;
    @NotNull
    public double monto;
    public String descripcion;
    @NotNull
    public Integer numero_cuenta;
}
