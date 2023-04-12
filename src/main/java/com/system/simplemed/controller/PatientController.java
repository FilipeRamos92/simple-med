package com.system.simplemed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.simplemed.dto.PatientDto;
import com.system.simplemed.model.Patient;
import com.system.simplemed.repository.PatientRepository;
import com.system.simplemed.service.PatientService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

	@Autowired
	private ModelMapper modelMapper;
	
    @Autowired
    private PatientRepository patientRepository;

	private PatientService patientService;

	public PatientController(PatientService patientService) {
		super();
		this.patientService = patientService;
	}


    @GetMapping
    public List<PatientDto> getAllPatients(){

		return patientService.getAllPatients()
			.stream()
			.map(patient -> modelMapper.map(patient, PatientDto.class))
			.collect(Collectors.toList());
    }

	@GetMapping("/{id}")
	public ResponseEntity<PatientDto> getPatientById(@PathVariable(name = "id") long id) {
		
		Patient patient = patientService.getPatientById(id);

		PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);
		return ResponseEntity.ok().body(patientResponse);
	}

	@PostMapping
	public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
		
		Patient patientRequest = modelMapper.map(patientDto, Patient.class);

		Patient patient = patientService.createPatient(patientRequest);

		PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);

		return new ResponseEntity<PatientDto>(patientResponse, HttpStatus.CREATED);
	}

    @PutMapping("/{id}")
	public ResponseEntity<PatientDto> updatePatient(@PathVariable Long id, @RequestBody Patient patientDetails){
		
		Patient patient = patientService.updatePatient(id, patientDetails);

		PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);
		return new ResponseEntity<>(patientResponse, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePatient(@PathVariable(name = "id") Long id){

		patientService.deletePatient(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
	}
}
