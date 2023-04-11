package edu.wpi.teamc.navigation;

public enum Screen {
  //----------------NON-USING SCREENS----------------//
  ROOT("views/Root.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  //----------------MAP & SIGNAGE----------------//
  SIGNAGE("views/SignagePage.fxml"),
  ELEVATOR_SIGNAGE("views/ElevatorSignage.fxml"),
  FLOOR_PLAN("views/FloorPlan.fxml"),
  MAP_HISTORY_PAGE("views/MapChangeHistory.fxml"),
  PATHFINDING_PAGE("views/Pathfinding.fxml"),
  EDIT_MAP("views/MapEditing.fxml"),
  //----------------HOME & LOGIN----------------//
  LOGIN("views/LoginPage.fxml"),
  HOME("views/Home.fxml"),
  GUEST_HOME("views/GuestHome.fxml"),
  //----------------SERVICE REQUESTS----------------//
  MEAL("views/Meal.fxml"),
  MEAL_HISTORY("views/MealHistory.fxml"),
  CONFERENCE("views/Conference.fxml"),
  CONFERENCE_HISTORY("views/ConferenceHistory.fxml"),
  FLOWER("views/Flower.fxml"),
  FURNITURE("views/Furniture.fxml"),
  OFFICE_SUPPLY("views/OfficeSupply.fxml"),
  //----------------BONUS----------------//
  CONGRATS_PAGE("views/congratsPage.fxml"),
  HELP("views/Help.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
