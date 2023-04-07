package edu.wpi.teamname.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class MealRequest extends ServiceRequest{

    @Getter @Setter
    private Meal meal;
    private String room;

    public MealRequest(Requester requester, Meal meal, String room, String note, STATUS stat) {
        this.requester = requester;
        this.status = stat;
        this.additionalNotes = note;
        this.meal = meal;
        this.room = room;

    }
    @Override
    void executeRequest() {
    }
}
