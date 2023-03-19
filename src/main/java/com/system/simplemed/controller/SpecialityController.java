package com.system.simplemed.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.simplemed.exception.ResourceNotFoundException;
import com.system.simplemed.model.Speciality;
import com.system.simplemed.repository.SpecialityRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/")
public class SpecialityController {

        @Autowired
        private SpecialityRepository specialityRepository;

        @GetMapping("specialities")
        public List<Speciality> getAllSpecialities(){
            return specialityRepository.findAll();
        } 

        @PostMapping("/specialities")
        public Speciality createSpeciality(@RequestBody Speciality speciality){
            return specialityRepository.save(speciality);
        }

        @DeleteMapping("/specialities/{id}")
        public ResponseEntity<Map<String, Boolean>> deleteSpeciality(@PathVariable Long id){
            Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speciality not exist with id: " + id));

            specialityRepository.delete(speciality);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }
}
