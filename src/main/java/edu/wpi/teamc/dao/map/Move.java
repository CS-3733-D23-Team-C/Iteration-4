package edu.wpi.teamc.dao.map;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Move {
  private int nodeID;
  private String longName;
  private Date date;

  public Move(int nodeID, String longName, Date date) {
    this.nodeID = nodeID;
    this.longName = longName;
    this.date = date;
  }
}
