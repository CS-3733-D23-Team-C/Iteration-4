package edu.wpi.teamc.navigation;

public enum Screen {
  // -------NON USING PAGES-------//
  ROOT("views/Root.fxml"),
  //  PATHFINDING("views/english/PathfindingIt1.fxml"),
  // ELEVATOR_SIGNAGE("views/english/ElevatorSignage.fxml"),

  // -------MAP & SIGNAGE-------//
  SIGNAGE("views/pages/map/ElevatorSignage.fxml"),
  GUEST_SIGNAGE("views/pages/guest/GuestElevatorSignage.fxml"),
  MOVE_TABLE("views/pages/map/MoveTable.fxml"),
  MAP_HISTORY_PAGE("views/pages/map/MapHistory.fxml"),
  PATHFINDING_PAGE("views/pages/map/PathFinding.fxml"),
  GUEST_PATHFINDING_PAGE("views/pages/guest/GuestPathFinding.fxml"),
  EDIT_MAP("views/pages/map/EditMap.fxml"),
  FLOOR_PLAN("views/pages/map/FloorPlan.fxml"),

  // -------LOGIN & HOME-------//
  HOME("views/pages/Home.fxml"),
  ADMIN_HOME("views/pages/admin/AdminHome.fxml"),
  GUEST_HOME("views/pages/guest/GuestHome.fxml"),
  MENU("views/components/Menu.fxml"),
  GUEST_MENU("views/pages/guest/GuestMenu.fxml"),

  // -------SERVICE REQUEST-------//
  MEAL("views/pages/requests/MealRequest.fxml"),
  CONFERENCE("views/pages/requests/Conference.fxml"),
  FLOWER("views/pages/requests/FlowerRequest.fxml"),
  FURNITURE("views/pages/requests/FurnitureRequest.fxml"),
  OFFICE_SUPPLY("views/pages/requests/OfficeSupplyRequest.fxml"),
  GIFT_BASKET("views/pages/requests/GiftBasketRequest.fxml"),

  REQUEST_HISTORY("views/pages/requests/RequestHistory.fxml"),

  // -------OTHER-------//
  CONGRATS_PAGE("views/pages/Congrats.fxml"),
  HELP("views/pages/Help.fxml"),

  GUEST_HELP("views/pages/guest/GuestHelp.fxml"),

  EXIT_PAGE("views/pages/ExitPage.fxml"),
  EMPLOYEETABLE_PAGE("views/pages/EmployeeTable.fxml");

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
