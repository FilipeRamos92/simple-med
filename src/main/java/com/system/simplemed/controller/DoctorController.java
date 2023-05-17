package com.system.simplemed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.simplemed.model.Doctor;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.service.DoctorService;
import com.system.simplemed.service.SpecialityService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

	@Autowired
	private SpecialityService specialityService;
	
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
		
		List<Doctor> doctorList = doctorService.getAllDoctors();

        return new ResponseEntity<List<Doctor>>(doctorList, HttpStatus.OK);
    }

	@GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctor(@PathVariable long id){
		
		Doctor doctor = doctorService.getDoctorById(id);

		return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
	}

	// @GetMapping
	// public ResponseEntity<Doctor> getDoctorBySpeciality(@RequestParam String name) {
	// 	return new ResponseEntity<Doctor>(null, null, 0);
	// }

	@PostMapping("/speciality/{specialityId}")
	public ResponseEntity<Doctor> createDoctor(@PathVariable long specialityId, @RequestBody Doctor doctor) {
		
		Speciality speciality = specialityService.getSpecialityById(specialityId);
		
		doctor.setSpeciality(speciality);

		doctorService.createDoctor(doctor);

		return new ResponseEntity<Doctor>(doctor, HttpStatus.CREATED);
	}

    @PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctorInfo(@PathVariable long id, @RequestBody Doctor doctorDetails){

		Doctor updatedDoctor = doctorService.updateDoctorInfo(id, doctorDetails);
		
		return new ResponseEntity<Doctor>(updatedDoctor, HttpStatus.ACCEPTED);
	}

	@PutMapping("/{doctorId}/updateToSpeciality/{specialityId}")
	public ResponseEntity<Doctor> updateDoctorSpeciality(@PathVariable long doctorId, @PathVariable long specialityId) {

		Doctor updatedDoctor = doctorService.updateDoctorSpeciality(doctorId, specialityId);

		return new ResponseEntity<Doctor>(updatedDoctor, HttpStatus.ACCEPTED);
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDoctor(@PathVariable long id){
		
		doctorService.deleteDoctor(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.ACCEPTED);
	}
}
