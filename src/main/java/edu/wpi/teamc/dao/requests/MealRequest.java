package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class MealRequest extends AbsServiceRequest {

  @Getter @Setter private Meal meal;
  @Getter @Setter private String eta;

  public MealRequest(int requestID, Requester requester, String roomName, String note, Meal meal) {
    super(requestID, requester, roomName, note);
    this.meal = meal;
  }

  @Override
  void executeRequest() {}
}
