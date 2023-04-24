package edu.wpi.teamc.dao.displays.signage;

import java.util.List;

public enum DIRECTION {
  NORTH,
  SOUTH,
  EAST,
  WEST,
  HERE,
  NONE;

  public static List<DIRECTION> getDirections() {
    return List.of(NORTH, SOUTH, EAST, WEST, HERE, NONE);
  }

  public static List<String> getDirectionStrings() {
    return List.of("NORTH", "SOUTH", "EAST", "WEST", "HERE", "NONE");
  }

  @Override
  public String toString() {
    if (this == NONE) {
      return null;
    } else {
      return super.toString();
    }
  }
}
