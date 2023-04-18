package edu.wpi.teamc.controllers.english.MapHelpers;

public enum HandleMapModes {
  SELECT("Select"),
  ADD("Add"),
  MODIFY("Modify"),
  MODIFY_DRAG("Modify_drag"),
  REMOVE("Remove"),
  MOVE("Move");

  private final String mode;

  HandleMapModes(String mode) {
    this.mode = mode;
  }

  public String getMapMode() {
    return mode;
  }
}
