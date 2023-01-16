package com.drone.api.controllers;

import com.drone.api.dtos.DroneDto;
import com.drone.api.entity.Drone;
import com.drone.api.services.DroneService;
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
@RequestMapping("/v1/drone")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DroneController {

  @Autowired
  DroneService droneService;


  @PostMapping(path = "/register")
  public ResponseEntity<Drone> registerDrone(@RequestBody @Valid DroneDto droneDto) {
    return new ResponseEntity<>(droneService.registerDrone(droneDto), HttpStatus.CREATED);
  }

  @GetMapping("/batteryLevel/{droneId}")
  public ResponseEntity<Double> getDroneBatteryLevel(@PathVariable(value = "droneId") final Long droneId) {
    return new ResponseEntity<>(droneService.getDroneBatteryLevel(droneId), HttpStatus.FOUND);
  }

  @GetMapping("/available")
  public ResponseEntity<List<Drone>> getAvailableDronesForLoading() {
    return new ResponseEntity<>(droneService.getAvailableDronesForLoading(), HttpStatus.FOUND);
  }

}
