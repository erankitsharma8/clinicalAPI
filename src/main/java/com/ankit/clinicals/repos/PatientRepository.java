package com.ankit.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankit.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient,Integer> {

}
