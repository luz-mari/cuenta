package com.proyecto.ntt.cuenta.repository.dao;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Data
@Document(collection="cuentas")
public class CuentaDao {
    @Id
    private Integer numero_cuenta= UUID.randomUUID().hashCode();
    @NotNull
    private Integer tipo_cuenta;
    @NotNull
    private double saldo = 0.0;
    @NotNull
    private Integer id_cliente;
}
