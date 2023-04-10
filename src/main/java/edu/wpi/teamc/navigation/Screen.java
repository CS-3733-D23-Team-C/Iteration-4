package edu.wpi.teamc.navigation;

public enum Screen {
  ROOT("views/Root.fxml"),
  HOME("views/Home.fxml"),
  SERVICE_REQUEST("views/ServiceRequest.fxml"),
  ADMIN_HOME("views/AdminHome.fxml"),
  GUEST_HOME("views/GuestHome.fxml"),
  ;

  private final String filename;

  Screen(String filename) {
    this.filename = filename;
  }

  public String getFilename() {
    return filename;
  }
}
