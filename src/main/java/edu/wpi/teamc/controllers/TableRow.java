package edu.wpi.teamc.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableRow {
  private String NodeID;
  private String LongName;
  private String Date;
  private int index;
  private String xCoord;
  private String yCoord;
  private String floorNum;
  private String building;
  private String longName;
  private String nodeType;
  private String startNode;
  private String endNode;

  public TableRow() {
    this.NodeID = "";
    this.LongName = "";
    this.Date = "";
  }

  public TableRow(String nodeID, String longName, String date) {
    this.NodeID = nodeID;
    this.LongName = longName;
    this.Date = date;
  }

  //  public TableRow(String nodeID, String longName, String date, int index) {
  //    this.NodeID = nodeID;
  //    this.LongName = longName;
  //    this.Date = date;
  //    this.index = index;
  //  }

  public TableRow(
      String nodeID,
      String xCoord,
      String yCoord,
      String floorNum,
      String building,
      String longName,
      String nodeType,
      int index) {
    this.NodeID = nodeID;
    this.xCoord = xCoord;
    this.yCoord = yCoord;
    this.floorNum = floorNum;
    this.building = building;
    this.longName = longName;
    this.nodeType = nodeType;
    this.index = index;
  }

  public String getNodeID() {
    return NodeID;
  }

  public void setNodeID(String nodeID) {
    this.NodeID = nodeID;
  }

  public String getLongName() {
    return LongName;
  }

  public void setLongName(String longName) {
    this.LongName = longName;
  }

  public String getDate() {
    return Date;
  }

  public void setDate(String date) {
    this.Date = date;
  }

  public int getIndex() {
    return index;
  }
}
