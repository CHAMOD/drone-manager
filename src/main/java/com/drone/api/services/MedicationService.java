package com.drone.api.services;


import com.drone.api.dtos.MedicationDto;
import com.drone.api.entity.Medication;
import com.drone.api.repositories.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

  private static final Logger logger = LoggerFactory.getLogger(MedicationService.class);
  @Autowired
  private MedicationRepository medicationRepository;

  public Medication registerMedication(MedicationDto medicationDto) {

    Medication medication = Medication.builder()
      .name(medicationDto.getName())
      .weight(medicationDto.getWeight())
      .code(medicationDto.getCode())
      .imageUrl(medicationDto.getImageUrl()).build();


    return medicationRepository.save(medication);
  }

  public List<Medication> getAllMedication() {
    return medicationRepository.findAll();
  }

  public List<Medication> getAllMedicationByMedicationIds(final List<Long> medicationIds) {
    return medicationRepository.findAllById(medicationIds);
  }

  public Medication getMedicationById(final Long medicationId) {
    return medicationRepository.getReferenceById(medicationId);
  }

}
