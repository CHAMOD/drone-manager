package com.drone.api.services;

import com.drone.api.dtos.DispatchDto;
import com.drone.api.dtos.LoadDto;
import com.drone.api.entity.Drone;
import com.drone.api.entity.DroneMedication;
import com.drone.api.entity.Medication;
import com.drone.api.enums.State;
import com.drone.api.exceptions.DroneNotFoundException;
import com.drone.api.repositories.DroneRepository;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DispatchService {

  private static final Logger logger = LoggerFactory.getLogger(DispatchService.class);

  @Autowired
  private DroneRepository droneRepository;

  @Autowired
  private DroneService droneService;

  @Autowired
  private MedicationService medicationService;

  @PersistenceContext
  private EntityManager entityManager;


  public DispatchDto loadMedications(final LoadDto loadDto) {

    Drone drone = null;

    try {
      drone = droneRepository.findById(loadDto.getDroneId()).orElseThrow(EntityNotFoundException::new);
    } catch (final EntityNotFoundException e) {
      throw new DroneNotFoundException("Invalid Drone ID:" + loadDto.getDroneId());
    }

    if (!droneService.isLoadable(loadDto.getDroneId())) {
      ParameterizedMessage parameterizedMessage = new ParameterizedMessage("cannot load medications for drone {}",
                                                                           loadDto.getDroneId());
      throw new RuntimeException(parameterizedMessage.getFormattedMessage());
    }


    if (!droneService.checkDroneBattery(loadDto.getDroneId())) {
      ParameterizedMessage parameterizedMessage = new ParameterizedMessage("Battery level for the drone {} is not enough",
                                                                           loadDto.getDroneId());
      throw new RuntimeException(parameterizedMessage.getFormattedMessage());
    }


    final List<Medication> medicationList = medicationService.getAllMedicationByMedicationIds(loadDto.getMedicationItems().stream().collect(
      Collectors.toList()));

    Session session = entityManager.unwrap(Session.class);
    Transaction tx = session.beginTransaction();

    List<DroneMedication> currentDroneMedications = session.createQuery(
        "from DroneMedication dm WHERE dm.drone=:drone AND dm.activated=:activated")
      .setParameter("drone", drone)
      .setParameter("activated", true)
      .list();

    tx.commit();

    Double totalWeight = Double.valueOf(0);

    for (final DroneMedication droneMedication : currentDroneMedications) {

      totalWeight = Double.sum(totalWeight, droneMedication.getMedication().getWeight());

    }

    for (final Medication m : medicationList) {
      totalWeight = Double.sum(totalWeight, m.getWeight());
      if (totalWeight.compareTo(Double.valueOf(drone.getWeightLimit())) > 0) {
        ParameterizedMessage parameterizedMessage = new ParameterizedMessage("Weight limit is exceeded for drone {}",
                                                                             loadDto.getDroneId());
        throw new RuntimeException(parameterizedMessage.getFormattedMessage());
      }

    }

    DroneMedication droneMedication = new DroneMedication();
    droneMedication.setDrone(drone);
    droneMedication.setMedication(medicationList.get(0));
    droneMedication.setActivated(true);
    session.save(droneMedication);
    session.close();

    if (drone.getState() == State.I) {
      droneService.updateDroneState(drone.getId(), State.L);
    }

    return new DispatchDto(drone, medicationList.stream().collect(Collectors.toSet()));
  }


  public List<Medication> getMedicationsForDrone(final Long droneId) {

    Drone drone = null;

    try {
      drone = droneRepository.findById(droneId).orElseThrow(EntityNotFoundException::new);
    } catch (final EntityNotFoundException e) {
      throw new DroneNotFoundException("Invalid Drone ID:" + droneId);
    }

    Session session = entityManager.unwrap(Session.class);
    Transaction tx = session.beginTransaction();

    List<DroneMedication> droneMedications = session.createQuery(
        "from DroneMedication dm WHERE dm.drone=:drone AND dm.activated=:activated")
      .setParameter("drone", drone)
      .setParameter("activated", true)
      .list();

    tx.commit();

    List<Medication> medicationList =
      droneMedications.stream()
        .map(DroneMedication::getMedication)
        .collect(Collectors.toList());

    return medicationList;

  }
}
