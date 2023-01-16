package com.drone.api.dtos;

import com.drone.api.entity.Drone;
import com.drone.api.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
@Builder
public class DispatchDto {

  private Drone drone;

  private Set<Medication> medications;

}
