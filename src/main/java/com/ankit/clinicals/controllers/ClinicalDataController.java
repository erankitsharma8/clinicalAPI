package com.ankit.clinicals.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ankit.clinicals.dto.ClinicalDataRequest;
import com.ankit.clinicals.model.ClinicalData;
import com.ankit.clinicals.model.Patient;
import com.ankit.clinicals.repos.CliniclaDataRepository;
import com.ankit.clinicals.repos.PatientRepository;
import com.ankit.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ClinicalDataController {
	
	private CliniclaDataRepository clinicalDataRepos;
	private PatientRepository patientRepos;
	
	ClinicalDataController(CliniclaDataRepository clinicalDataRepos, PatientRepository patientRepos){
		this.clinicalDataRepos=clinicalDataRepos;
		this.patientRepos=patientRepos;
	}

	@RequestMapping(value="/clinicals",method=RequestMethod.POST)
	public ClinicalData saveClinicalData(@RequestBody ClinicalDataRequest clinicalDataRequest) {
		Patient patient = patientRepos.findById(clinicalDataRequest.getPatientId()).get();
		ClinicalData clinicalData = new ClinicalData();
		clinicalData.setComponentName(clinicalDataRequest.getComponentName());
		clinicalData.setComponentValue(clinicalDataRequest.getComponentValue());
		clinicalData.setPatient(patient);
		return clinicalDataRepos.save(clinicalData);
		
	}
	
	@RequestMapping(value = "/clinicals/{patientId}/{componentName}", method = RequestMethod.GET)
	public List<ClinicalData> getClinicalData(@PathVariable("patientId") int patientId,
			@PathVariable("componentName") String componentName) {
		if(componentName.equals("bmi")) {
			componentName="hw";
		}
		List<ClinicalData> clinicalData = clinicalDataRepos.findByPatientIdAndComponentNameOrderByMeasuredDateTime(patientId, componentName);
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(clinicalData);
		for(ClinicalData eachEntry:duplicateClinicalData) {
			BMICalculator.calculateBMI(clinicalData, eachEntry);
		}
		
		return clinicalData;
	}
}
