package edu.wpi.teamc.controllers.pages.map.MapHelpers;

import edu.wpi.teamc.dao.map.LocationName;
import edu.wpi.teamc.dao.map.NODE_STATUS;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapModeSaver {
  public int eventX;
  public int eventY;
  public int nodeID;
  public String shortName;
  public String longName;
  public LocationName locationName;
  public NODE_STATUS nodeStatus;
  public Boolean draggingNodeCreated = false;

  public MapModeSaver() {}

  public MapModeSaver(int eventX, int eventY) {
    this.eventX = eventX;
    this.eventY = eventY;
  }

  public MapModeSaver(int eventX, int eventY, int nodeID) {
    this.eventX = eventX;
    this.eventY = eventY;
    this.nodeID = nodeID;
  }

  public int getEventX() {
    return eventX;
  }

  public int getEventY() {
    return eventY;
  }

  public int getNodeID() {
    return nodeID;
  }

  public void setNodeID(int iD) {
    this.nodeID = iD;
  }

  public void setEventCoords(int x, int y) {
    this.eventX = x;
    this.eventY = y;
  }
}
