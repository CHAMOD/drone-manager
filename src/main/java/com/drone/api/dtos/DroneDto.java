package com.drone.api.dtos;

import com.drone.api.enums.Model;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
public class DroneDto {

  @NotBlank(message = "serial no can't be blank")
  @Size(max = 100, message = "Invalid serial No: max length is 100")
  private String serialNo;

  private Model model;

  @Max(100)
  private int weightLimit;

  private Double batteryCapacity;

}
