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
import org.springframework.web.bind.annotation.RestController;

import com.system.simplemed.model.Speciality;
import com.system.simplemed.service.SpecialityService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/specialities")
public class SpecialityController {

        @Autowired
        private SpecialityService specialityService;

        @GetMapping
        public ResponseEntity<List<Speciality>> getAllSpecialities(){
            List<Speciality> specialityList = specialityService.getAllSpecialities();
            return new ResponseEntity<List<Speciality>>(specialityList, HttpStatus.OK);
        } 

        @PostMapping
        public ResponseEntity<Speciality> createSpeciality(@RequestBody Speciality specialityRequest){
            Speciality speciality = specialityService.createSpeciality(specialityRequest);
            return new ResponseEntity<Speciality>(speciality, HttpStatus.CREATED);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Speciality> updateSpeciality(@PathVariable Long id, @RequestBody Speciality specialityRequest){

            specialityService.updateSpeciality(id, specialityRequest);

            return new ResponseEntity<Speciality>(specialityRequest, HttpStatus.ACCEPTED);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Map<String, Boolean>> deleteSpeciality(@PathVariable Long id){

            specialityService.deleteSpeciality(id);

            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);

            return new ResponseEntity<Map<String,Boolean>>(response, HttpStatus.ACCEPTED);
        }
}
