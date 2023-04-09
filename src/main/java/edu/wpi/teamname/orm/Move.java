package edu.wpi.teamname.orm;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Move {
  private String nodeID;
  private String longName;
  private Date date;

  public Move(String nodeID, String longName, Date date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }
}
