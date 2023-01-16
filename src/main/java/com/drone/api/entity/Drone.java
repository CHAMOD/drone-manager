package com.drone.api.entity;

import com.drone.api.enums.Model;
import com.drone.api.enums.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Drone {

  private long id;

  private String serialNo;

  @Enumerated(EnumType.STRING)
  private Model model;

  private int weightLimit;

  private Double batteryCapacity;

  @Enumerated(EnumType.STRING)
  private State state;

  private Set<DroneMedication> droneMedications = new HashSet<>();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getId() {
    return id;
  }

  @JsonIgnore
  @OneToMany(mappedBy = "drone")
  public Set<DroneMedication> getDroneMedications() {
    return droneMedications;
  }

  public void setDroneMedications(Set<DroneMedication> droneMedications) {
    this.droneMedications = droneMedications;
  }

  public void addDroneMedications(DroneMedication droneMedication) {
    this.droneMedications.add(droneMedication);
  }

  @Override
  public String toString() {
    return "Drone{" +
      "id=" + id +
      ", serialNo='" + serialNo + '\'' +
      ", model=" + model +
      ", weightLimit=" + weightLimit +
      ", batteryCapacity=" + batteryCapacity +
      ", state=" + state +
      '}';
  }
}
