package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.IUser;
import lombok.Getter;
import lombok.Setter;

public class MealRequest extends AbsServiceRequest {

  @Getter @Setter private Meal meal;
  @Getter @Setter private String eta;

  MealRequest(int requestID, IUser requester, String roomName, String note, Meal meal) {
    super(requestID, requester, roomName, note);
    this.meal = meal;
  }

  public MealRequest(IUser requester, String roomName, String note, Meal meal) {
    super(requester, roomName, note);
    this.meal = meal;
  }
}
