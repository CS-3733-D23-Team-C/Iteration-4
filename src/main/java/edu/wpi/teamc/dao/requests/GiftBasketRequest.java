package edu.wpi.teamc.dao.requests;

import edu.wpi.teamc.dao.users.IUser;
import lombok.Getter;
import lombok.Setter;

public class GiftBasketRequest extends AbsServiceRequest {
  @Getter @Setter private GiftBasket gift;
  @Getter @Setter private String eta;

  public GiftBasketRequest(
      int requestID, IUser requester, String roomName, String note, GiftBasket gift) {
    super(requestID, requester, roomName, note);
    this.gift = gift;
  }

  public GiftBasketRequest(IUser requester, String roomName, String note, GiftBasket gift) {
    super(requester, roomName, note);
    this.gift = gift;
  }

  public GiftBasketRequest(
      IUser requester, String roomName, String note, GiftBasket giftl, String eta) {
    super(requester, roomName, note);
    this.gift = gift;
    this.eta = eta;
  }

  public GiftBasketRequest() {}
}
