package edu.wpi.teamc.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  SIGNAGE("views/SignagePage.fxml"),
  ELEVATOR_SIGNAGE("views/ElevatorSignage.fxml"),
  HOME("views/Home.fxml"),
  ADMIN_HOME("views/AdminHome.fxml"),

  FLOOR_PLAN("views/FloorPlan.fxml"),

  LOGIN("views/LoginPage.fxml"),
  GUEST_HOME("views/GuestHome.fxml"),
  MEAL("views/Meal.fxml"),
  MEAL_HISTORY("views/MealHistory.fxml"),
  CONFERENCE("views/Conference.fxml"),
  CONFERENCE_HISTORY("views/ConferenceHistory.fxml"),

  FLOWER("views/Flower.fxml"),
  FURNITURE("views/Furniture.fxml"),
  OFFICE_SUPPLY("views/OfficeSupply.fxml"),
  CONGRATS_PAGE("views/congratsPage.fxml"),
  PATHFINDING_PAGE("views/Pathfinding.fxml"),
  HELP("views/Help.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
