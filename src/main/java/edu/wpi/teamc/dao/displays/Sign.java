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

  public Sign(
      String locationname, Date date, String direction, String screenlocation) {
    this.locationname = locationname;
    this.date = date;
    this.direction = direction;
    this.screenlocation = screenlocation;
  }

  public Sign() {}
}
