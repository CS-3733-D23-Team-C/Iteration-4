package edu.wpi.teamc.navigation;

public enum Screen {
  // -------NON USING PAGES-------//
  ROOT("views/english/Root.fxml"),
  SERVICE_REQUEST("views/english/ServiceRequest.fxml"),
  //  PATHFINDING("views/english/PathfindingIt1.fxml"),
  // ELEVATOR_SIGNAGE("views/english/ElevatorSignage.fxml"),

  // -------MAP & SIGNAGE-------//
  SIGNAGE("views/english/ElevatorSignage.fxml"),
  MAP_HISTORY_PAGE("views/english/MapHistory.fxml"),
  PATHFINDING_PAGE("views/english/PathFinding.fxml"),
  GUEST_PATHFINDING_PAGE("views/english/Guest/GuestPathFinding.fxml"),
  MOVETABLE_PAGE("views/english/MoveTable.fxml"),
  EDIT_MAP("views/english/EditMap.fxml"),
  FLOOR_PLAN("views/english/FloorPlan.fxml"),

  // -------LOGIN & HOME-------//
  HOME("views/english/Home.fxml"),
  LOGIN("views/english/LoginPage.fxml"),
  ADMIN_HOME("views/english/AdminHome.fxml"),
  GUEST_HOME("views/english/Guest/GuestHome.fxml"),
  MENU("views/english/Menu.fxml"),
  GUEST_MENU("views/english/Guest/GuestMenu.fxml"),

  // -------SERVICE REQUEST-------//
  MEAL("views/english/MealRequest.fxml"),
  MEAL_HISTORY("views/english/MealHistory.fxml"),

  CONFERENCE("views/english/Conference.fxml"),
  CONFERENCE_HISTORY("views/english/ConferenceHistory.fxml"),
  MOVE_TABLE("views/english/MoveTable.fxml"),
  FLOWER("views/english/FlowerRequest.fxml"),
  FLOWER_HISTORY("views/english/FlowerHistory.fxml"),
  FURNITURE("views/english/FurnitureRequest.fxml"),
  FURNITURE_HISTORY("views/english/FurnitureHistory.fxml"),
  OFFICE_SUPPLY("views/english/OfficeSupplyRequest.fxml"),
  OFFICE_SUPPLY_HISTORY("views/english/OfficeSupplyHistory.fxml"),

  GIFT_BASKET("views/english/GiftBasketRequest.fxml"),
  // -------OTHER-------//
  CONGRATS_PAGE("views/english/Congrats.fxml"),
  HELP("views/english/Help.fxml"),
  EXIT_PAGE("views/english/ExitPage.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
