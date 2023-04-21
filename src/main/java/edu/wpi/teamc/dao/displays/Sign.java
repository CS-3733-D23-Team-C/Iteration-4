package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;

import java.sql.Date;

@Getter
public class Sign implements IOrm {
  String locationname;
  Date date;
  String direction;
  String screenlocation;
  String location;

  public Sign(
      String locationname, Date date, String direction, String screenlocation, String location) {
    this.locationname = locationname;
    this.date = date;
    this.direction = direction;
    this.screenlocation = screenlocation;
    this.location = location;
  }

  public Sign() {}
}
