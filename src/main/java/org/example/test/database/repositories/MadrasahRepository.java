package org.example.test.database.repositories;


import org.example.test.database.entities.Madrasah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MadrasahRepository extends JpaRepository<Madrasah, Short> {
}
