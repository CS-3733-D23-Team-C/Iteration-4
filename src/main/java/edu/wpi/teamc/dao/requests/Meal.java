package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class Meal {
  @Getter @Setter private String mealName;
  @Getter @Setter private String drinkName;
  @Getter @Setter private String additionalInfo;

  public Meal(String name, String drinkName, String additionalInfo) {
    this.mealName = name;
    this.drinkName = drinkName;
    this.additionalInfo = additionalInfo;
  }

  @Override
  public String toString() {
    return this.mealName;
  }
}
