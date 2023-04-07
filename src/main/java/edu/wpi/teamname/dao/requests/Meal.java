package edu.wpi.teamname.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class Meal {
    @Getter
    @Setter
    private String mealName;
    @Getter @Setter private String additionalInfo;

    public Meal(String name, String additionalInfo) {
        this.mealName = name;
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString() {
        return this.mealName;
    }
}
