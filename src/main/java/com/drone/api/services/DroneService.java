package com.drone.api.services;


import com.drone.api.dtos.DroneDto;
import com.drone.api.entity.Drone;
import com.drone.api.enums.State;
import com.drone.api.exceptions.DroneNotFoundException;
import com.drone.api.repositories.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DroneService {

  private static final Logger logger = LoggerFactory.getLogger(DroneService.class);
  @Autowired
  private DroneRepository droneRepository;

  public Drone registerDrone(DroneDto droneDto) {

    Drone drone = Drone.builder()
      .serialNo(droneDto.getSerialNo())
      .model(droneDto.getModel())
      .weightLimit(droneDto.getWeightLimit())
      .batteryCapacity(droneDto.getBatteryCapacity())
      .state(State.I).build();
    return droneRepository.save(drone);
  }

  public Drone updateDroneState(final Long droneId, final State state) {
    final Drone drone = droneRepository.getReferenceById(droneId);
    drone.setState(state);
    droneRepository.save(drone);
    return drone;
  }

  public boolean checkDroneBattery(final Long droneId) {

    final Drone drone = droneRepository.getReferenceById(droneId);
    final Double batteryLevel = drone.getBatteryCapacity();

    if (batteryLevel.compareTo(new Double(25)) < 0) {
      return false;
    }
    return true;

  }

  public Double getDroneBatteryLevel(final Long droneId) {

    Drone drone = null;

    try {
      drone = droneRepository.findById(droneId).orElseThrow(EntityNotFoundException::new);
    } catch (final EntityNotFoundException e) {
      throw new DroneNotFoundException("Invalid Drone ID:" + drone);
    }

    final Double batteryLevel = drone.getBatteryCapacity();
    return batteryLevel;

  }

  public boolean isLoadable(final Long droneId) {
    final Drone drone = droneRepository.getReferenceById(droneId);
    return (drone.getState() == State.I || drone.getState() == State.L);
  }

  public List<Drone> getAvailableDronesForLoading() {

    final List<Drone> droneList = droneRepository.findAll();

    return droneList.stream()
      .filter(c -> c.getState() == State.I || c.getState() == State.L)
      .collect(Collectors.toList());
  }
}
