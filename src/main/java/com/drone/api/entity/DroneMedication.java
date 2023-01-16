package com.drone.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DroneMedication {

  private long id;

  private Drone drone;

  private Medication medication;

  private boolean activated;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "DRONE_ID")
  public Drone getDrone() {
    return drone;
  }

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "MEDICATION_ID")
  public Medication getMedication() {
    return medication;
  }

  @Column
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getId() {
    return id;
  }

  public boolean isActivated() {
    return activated;
  }
}
