package com.drone.api.enums;

public enum State {
  I("IDLE"),
  L("LOADING"),
  DG("DELIVERING"),
  D("DELIVERED"),
  R("RETURNING");
  private String value;

  State(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return this.getValue();
  }

  public static State getEnum(String value) {
    if (value == null)
      throw new IllegalArgumentException();
    for (State v : values())
      if (value.equalsIgnoreCase(v.getValue())) return v;
    throw new IllegalArgumentException();
  }
}
