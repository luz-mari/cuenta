package com.proyecto.ntt.cuenta.repository;

import com.proyecto.ntt.cuenta.controller.dto.Cuenta;
import com.proyecto.ntt.cuenta.repository.dao.MovimientoDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MovimientoRepository extends CrudRepository<MovimientoDao, Integer> {
}
