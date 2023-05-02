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
import com.system.simplemed.service.PatientService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients(){

		List<PatientDto> patientList = patientService.getAllPatients()
			.stream()
			.map(patient -> modelMapper.map(patient, PatientDto.class))
			.collect(Collectors.toList());

		return new ResponseEntity<List<PatientDto>>(patientList, HttpStatus.OK);
    }

	@GetMapping("/{id}")
	public ResponseEntity<PatientDto> getPatientById(@PathVariable long id) {
		
		Patient patient = patientService.getPatientById(id);

		PatientDto patientDto = modelMapper.map(patient, PatientDto.class);

		return new ResponseEntity<PatientDto>(patientDto, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto) {
		
		Patient patientRequest = modelMapper.map(patientDto, Patient.class);

		Patient patient = patientService.createPatient(patientRequest);

		PatientDto patientResponse = modelMapper.map(patient, PatientDto.class);

		return new ResponseEntity<PatientDto>(patientResponse, HttpStatus.CREATED);
	}

    @PutMapping("/{id}")
	public ResponseEntity<PatientDto> updatePatient(@PathVariable long id, @RequestBody Patient patientDetails){
		
		Patient patient = patientService.updatePatient(id, patientDetails);

		PatientDto patientDto = modelMapper.map(patient, PatientDto.class);

		return new ResponseEntity<PatientDto>(patientDto, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deletePatient(@PathVariable long id){

		patientService.deletePatient(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return new ResponseEntity<Map<String, Boolean>>(response, HttpStatus.ACCEPTED);
	}
}
