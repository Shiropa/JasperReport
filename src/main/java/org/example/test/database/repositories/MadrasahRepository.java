package org.example.test.database.repositories;


import org.example.test.database.entities.Madrasah;
import org.example.test.database.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MadrasahRepository extends JpaRepository<Madrasah, Short>, JpaSpecificationExecutor<Madrasah> {

    List<Madrasah> findByOrderByIdAsc();
}
