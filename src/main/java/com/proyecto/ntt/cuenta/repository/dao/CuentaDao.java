package com.proyecto.ntt.cuenta.repository.dao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name="cuentas")

public class CuentaDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer numero_cuenta;
    @NotNull
    private Integer tipo_cuenta;
    @NotNull
    private double saldo = 0.0;
    @NotNull
    private Integer id_cliente;
}
