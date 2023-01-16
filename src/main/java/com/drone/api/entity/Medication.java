package com.drone.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
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
public class Medication {

  private long id;

  private String name;

  private Double weight;

  private String code;

  private String imageUrl;

  private Set<DroneMedication> droneMedications = new HashSet<>();

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getId() {
    return id;
  }

  @JsonIgnore
  @OneToMany(mappedBy = "medication")
  public Set<DroneMedication> getDroneMedications() {
    return droneMedications;
  }


  public void setDroneMedications(Set<DroneMedication> droneMedications) {
    this.droneMedications = droneMedications;
  }

  public void addDroneMedications(DroneMedication droneMedication) {
    this.droneMedications.add(droneMedication);
  }
}
