package com.proyecto.ntt.cuenta.repository.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table (name="movimiento")

public class MovimientoDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer numero_cuenta;
    private String descripcion;
    private double monto;
    private LocalDate fecha;

}
