package com.drone.api.enums;

public enum Model {

  LW("Lightweight"),
  MW("Middleweight"),
  CW("Cruiserweight"),
  HW("Heavyweight");
  private String value;

  Model(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.getValue();
  }

  public static Model getEnum(String value) {
    if(value == null)
      throw new IllegalArgumentException();
    for(Model v : values())
      if(value.equalsIgnoreCase(v.getValue())) return v;
    throw new IllegalArgumentException();
  }
}
