package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.IUser;
import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

public class GiftBasket extends AbsServiceRequest {
  @Getter @Setter private String giftbasket;
  @Getter @Setter private String eta;

  GiftBasket(
      int requestID, IUser requester, String roomName, String giftbasket, String additionalNotes) {
    super(requestID, requester, roomName, additionalNotes);
    this.giftbasket = giftbasket;
  }

  public GiftBasket(
      IUser requester, String roomName, String giftbasket, String additionalNotes, Date eta) {
    super(requester, roomName, additionalNotes);
    this.giftbasket = giftbasket;
    this.eta = eta.toString();
  }

  public GiftBasket(
      int requestID,
      IUser requester,
      String additionalNotes,
      String giftbasket,
      String eta,
      String roomName) {
    super(requester, roomName, additionalNotes);
    this.giftbasket = giftbasket;
    this.eta = eta.toString();
    this.requestID = requestID;
  }

  public int getID() {
    return super.getRequestID();
  }

  @Override
  public String toString() {
    return this.giftbasket;
  }
}
