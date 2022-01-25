package org.example.test.database.repositories;


import org.example.test.database.entities.ApplicantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantTypeRepository extends JpaRepository<ApplicantType, Short> {
}
