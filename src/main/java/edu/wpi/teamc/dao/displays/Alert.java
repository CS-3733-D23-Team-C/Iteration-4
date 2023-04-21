package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.IOrm;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

public class Alert implements IOrm {
  @Getter private int id;
  @Getter @Setter String title;
  @Getter @Setter String description;
  @Getter @Setter String type;
  @Getter @Setter Timestamp startdate;
  @Getter @Setter Timestamp enddate;

  public Alert() {
  }
  Alert(
      int id,
      String title,
      String description,
      String type,
      Timestamp startdate,
      Timestamp enddate) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.type = type;
    this.startdate = startdate;
    this.enddate = enddate;
  }

  public Alert(
      String title, String description, String type, Timestamp startdate, Timestamp enddate) {
    this.title = title;
    this.description = description;
    this.type = type;
    this.startdate = startdate;
    this.enddate = enddate;
  }
}
