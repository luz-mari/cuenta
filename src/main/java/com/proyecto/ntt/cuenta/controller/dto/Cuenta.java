package com.proyecto.ntt.cuenta.controller.dto;

import lombok.Data;

@Data

public class Cuenta {
    private Integer numero_cuenta;
    private Integer tipo_cuenta;
    private double saldo;
    private Integer id_cliente;

}

