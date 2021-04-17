package com.ankit.clinicals.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ankit.clinicals.model.ClinicalData;

public interface CliniclaDataRepository extends JpaRepository<ClinicalData,Integer> {

	List<ClinicalData> findByPatientIdAndComponentNameOrderByMeasuredDateTime(int patientId, String componentName);

}
