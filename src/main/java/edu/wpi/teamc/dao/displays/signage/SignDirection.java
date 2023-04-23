package edu.wpi.teamc.dao.displays.signage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignDirection {
  DIRECTION direction;
  String name;

  public SignDirection(DIRECTION direction, String name) {
    this.direction = direction;
    this.name = name;
  }

  public SignDirection() {}

  public static SignDirection valueOf(String direction) {
    String[] split = direction.split("_");
    if (split.length == 2) {
      return new SignDirection(DIRECTION.valueOf(split[0]), split[1]);
    } else {
      return null;
    }
  }

  @Override
  public String toString() {
    return direction.toString() + "_" + name;
  }
}
