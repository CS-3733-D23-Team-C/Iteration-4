package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.IOrm;

public class Kiosk implements IOrm {

  String id;
  String kioskname;
  int signid;

  public Kiosk(String id, String kioskname, int signid) {
    this.id = id;
    this.signid = signid;
  }

  public Kiosk() {}
}
