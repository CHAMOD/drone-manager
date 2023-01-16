package com.drone.api.controllers;

import com.drone.api.dtos.DispatchDto;
import com.drone.api.dtos.LoadDto;
import com.drone.api.entity.Medication;
import com.drone.api.exceptions.InvalidMedicationItemsException;
import com.drone.api.services.DispatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/dispatch")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DispatchController {

  @Autowired
  private DispatchService dispatchService;


  @PostMapping(path = "/load")
  public ResponseEntity<DispatchDto> loadMedications(@RequestBody @Valid LoadDto loadDto) {
    if (loadDto.getMedicationItems().isEmpty()) {
      throw new InvalidMedicationItemsException("Medication item list is empty");
    }
    return new ResponseEntity<>(dispatchService.loadMedications(loadDto), HttpStatus.CREATED);
  }

  @GetMapping("/medication/{droneId}")
  public ResponseEntity<List<Medication>> getMedicationsForDrone(@PathVariable(value = "droneId") final Long droneId) {
    return new ResponseEntity<>(dispatchService.getMedicationsForDrone(droneId), HttpStatus.FOUND);
  }

}
