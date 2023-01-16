package com.drone.api.controllers;

import com.drone.api.dtos.MedicationDto;
import com.drone.api.entity.Medication;
import com.drone.api.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/medication")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MedicationController {

  @Autowired
  MedicationService medicationService;


  @PostMapping(path = "/register")
  public ResponseEntity<Medication> registerMedication(@RequestBody @Valid MedicationDto medicationDto) {
    return new ResponseEntity<>(medicationService.registerMedication(medicationDto), HttpStatus.CREATED);
  }

}
