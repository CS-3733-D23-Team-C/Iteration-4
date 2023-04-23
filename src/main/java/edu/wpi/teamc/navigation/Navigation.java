package edu.wpi.teamc.navigation;

import edu.wpi.teamc.CApp;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import lombok.Getter;
import lombok.NonNull;

public class Navigation {
  public enum MenuType {
    ADMIN,
    GUEST,
    DISABLED;
  }

  @NonNull @Getter private static MenuType menuType = MenuType.DISABLED;

  public static void setMenuType(MenuType menuType) {
    Navigation.menuType = menuType;

    System.out.println("changed");

    updateMenu();
  }

  private static void updateMenu() {
    try {
      final FXMLLoader menuBarLoader =
          new FXMLLoader(CApp.class.getResource("views/components/Menu.fxml"));
      final FXMLLoader guestMenuBarLoader =
          new FXMLLoader(CApp.class.getResource("views/pages/guest/GuestMenu.fxml"));
      final FXMLLoader adminMenuBarLoader =
          new FXMLLoader(CApp.class.getResource("views/components/Menu.fxml"));

      switch (menuType) {
        case ADMIN -> CApp.getRootPane().setLeft(adminMenuBarLoader.load());
        case GUEST -> CApp.getRootPane().setLeft(guestMenuBarLoader.load());
        case DISABLED -> CApp.getRootPane().getChildren().remove(CApp.getRootPane().getLeft());
      }
    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }

  public static void navigate(final Screen screen) {
    final String filename = screen.getFilename();

    try {
      final var resource = CApp.class.getResource(filename);
      final FXMLLoader loader = new FXMLLoader(resource);
      final FXMLLoader menuBarLoader =
          new FXMLLoader(CApp.class.getResource("views/components/Menu.fxml"));
      final FXMLLoader guestMenuBarLoader =
          new FXMLLoader(CApp.class.getResource("views/pages/guest/GuestMenu.fxml"));

      CApp.getRootPane().setCenter(loader.load());
      updateMenu();

    } catch (IOException | NullPointerException e) {
      e.printStackTrace();
    }
  }
}
