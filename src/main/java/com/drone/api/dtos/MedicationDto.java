package com.drone.api.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class MedicationDto {


  @NotBlank(message = "serial no can't be blank")
  @Pattern(regexp = "^[\\w_-]+$", message = "Invalid name: only allow letters, numbers, - and _")
  private String name;

  private Double weight;

  @Pattern(regexp = "^[A-Z_-]*$", message = "Invalid code: only allow uppercase letters, underscore and numbers")
  private String code;

  private String imageUrl;

}
