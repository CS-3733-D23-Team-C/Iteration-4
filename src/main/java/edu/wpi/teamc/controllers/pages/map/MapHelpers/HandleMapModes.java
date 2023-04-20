package edu.wpi.teamc.controllers.pages.map.MapHelpers;

public enum HandleMapModes {
  SELECT("Select"),
  ADD("Add"),
  MODIFY("Modify"),
  MODIFY_DRAG("Modify_drag"),
  REMOVE("Remove"),
  MOVE("Move"),
  MAKE_EDGES("Make_edges");

  private final String mode;

  HandleMapModes(String mode) {
    this.mode = mode;
  }

  public String getMapMode() {
    return mode;
  }
}
