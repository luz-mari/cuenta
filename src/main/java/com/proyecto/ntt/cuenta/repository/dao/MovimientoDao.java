package com.proyecto.ntt.cuenta.repository.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Document(collection="movimiento")
public class MovimientoDao {
    @Id
    private Integer id= UUID.randomUUID().hashCode();
    private Integer numero_cuenta;
    private String descripcion;
    private double monto;
    private LocalDate fecha;

}
