package com.drone.api.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class LoadDto {

  @NotNull(message = "Invalid droneId: droneId can't be null")
  private Long droneId;

  @NotNull(message = "Invalid medicationItems: medicationItems can't be null")
  private Set<Long> medicationItems;
}
