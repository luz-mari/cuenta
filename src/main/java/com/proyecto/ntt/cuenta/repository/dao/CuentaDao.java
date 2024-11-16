package com.proyecto.ntt.cuenta.repository.dao;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="cuentas")

public class CuentaDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer numero_cuenta;
    private Integer tipo_cuenta;
    private double saldo;
    private Integer id_cliente;
}
