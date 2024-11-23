package com.proyecto.ntt.cuenta.repository;

import com.proyecto.ntt.cuenta.repository.dao.CuentaDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CuentaRepository extends MongoRepository<CuentaDao, Integer> {
}

