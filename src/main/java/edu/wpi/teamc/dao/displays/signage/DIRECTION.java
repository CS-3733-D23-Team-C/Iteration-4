package edu.wpi.teamc.dao.displays.signage;

import java.util.List;

public enum DIRECTION {
  UP,
  DOWN,
  LEFT,
  RIGHT,
  HERE,
  NONE;

  public static List<DIRECTION> getDirections() {
    return List.of(UP, DOWN, LEFT, RIGHT, HERE, NONE);
  }

  public static List<String> getDirectionStrings() {
    return List.of("UP", "DOWN", "LEFT", "RIGHT", "HERE", "NONE");
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
