package edu.wpi.teamc.navigation;

import edu.wpi.teamc.CApp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    // System.out.println("changed");

    updateMenu();
  }

  private static void updateMenu() {
    Thread thread =
        new Thread(
            () -> {
              try {
                final FXMLLoader menuBarLoader =
                    new FXMLLoader(CApp.class.getResource("views/components/Menu.fxml"));
                final FXMLLoader guestMenuBarLoader =
                    new FXMLLoader(CApp.class.getResource("views/pages/guest/GuestMenu.fxml"));
                final FXMLLoader adminMenuBarLoader =
                    new FXMLLoader(CApp.class.getResource("views/components/Menu.fxml"));
                Platform.runLater(
                    () -> {
                      try {
                        switch (menuType) {
                          case ADMIN -> CApp.getRootPane().setLeft(adminMenuBarLoader.load());
                          case GUEST -> CApp.getRootPane().setLeft(guestMenuBarLoader.load());
                          case DISABLED -> CApp.getRootPane()
                              .getChildren()
                              .remove(CApp.getRootPane().getLeft());
                        }
                      } catch (IOException e) {
                        throw new RuntimeException(e);
                      }
                    });

              } catch (NullPointerException e) {
                e.printStackTrace();
              }
            });

    thread.start();
  }

  public static void navigate(final Screen screen) {
    Thread thread =
        new Thread(
            () -> {
              try {
                final String filename = screen.getFilename();
                final var resource = CApp.class.getResource(filename);
                final FXMLLoader loader = new FXMLLoader(resource);
                final FXMLLoader menuBarLoader =
                    new FXMLLoader(CApp.class.getResource("views/components/Menu.fxml"));
                final FXMLLoader guestMenuBarLoader =
                    new FXMLLoader(CApp.class.getResource("views/pages/guest/GuestMenu.fxml"));

                Platform.runLater(
                    () -> {
                      try {
                        CApp.getRootPane().setCenter(loader.load());
                      } catch (IOException e) {
                        throw new RuntimeException(e);
                      }
                    });
              } catch (NullPointerException e) {
                e.printStackTrace();
              }
              updateMenu();
            });
    thread.start();
  }

  //  caching pages, not sure if we should include this,
  //  but it doesn't really work with how we have the switching between database setup rn

  private static final Map<String, Node> pageCache = new HashMap<>();

  //  private static final Map<Screen, FXMLLoader> loaderMap = new HashMap<Screen, FXMLLoader>();
  //  private void setCache(){
  //    loaderMap.put(Screen.ROOT, new
  // FXMLLoader(getClass().getResource(Screen.ROOT.getFilename())));
  //    loaderMap.put(
  //            Screen.SIGN_UP, new
  // FXMLLoader(getClass().getResource(Screen.SIGN_UP.getFilename())));
  //    loaderMap.put(
  //            Screen.SIGNAGE, new
  // FXMLLoader(getClass().getResource(Screen.SIGNAGE.getFilename())));
  //    loaderMap.put(
  //            Screen.GUEST_SIGNAGE,
  //            new FXMLLoader(getClass().getResource(Screen.GUEST_SIGNAGE.getFilename())));
  //    loaderMap.put(
  //            Screen.SIGNAGE_EDIT,
  //            new FXMLLoader(getClass().getResource(Screen.SIGNAGE_EDIT.getFilename())));
  //    loaderMap.put(
  //            Screen.MOVE_TABLE, new
  // FXMLLoader(getClass().getResource(Screen.MOVE_TABLE.getFilename())));
  //    loaderMap.put(
  //            Screen.MAP_HISTORY_PAGE,
  //            new FXMLLoader(getClass().getResource(Screen.MAP_HISTORY_PAGE.getFilename())));
  //    loaderMap.put(
  //            Screen.PATHFINDING_PAGE,
  //            new FXMLLoader(getClass().getResource(Screen.PATHFINDING_PAGE.getFilename())));
  //    loaderMap.put(
  //            Screen.GUEST_PATHFINDING_PAGE,
  //            new
  // FXMLLoader(getClass().getResource(Screen.GUEST_PATHFINDING_PAGE.getFilename())));
  //    loaderMap.put(
  //            Screen.EDIT_MAP, new
  // FXMLLoader(getClass().getResource(Screen.EDIT_MAP.getFilename())));
  //    loaderMap.put(
  //            Screen.FLOOR_PLAN, new
  // FXMLLoader(getClass().getResource(Screen.FLOOR_PLAN.getFilename())));
  //    loaderMap.put(Screen.HOME, new
  // FXMLLoader(getClass().getResource(Screen.HOME.getFilename())));
  //        loaderMap.put(
  //            Screen.ADMIN_HOME, new
  //     FXMLLoader(getClass().getResource(Screen.ADMIN_HOME.getFilename())));
  //    loaderMap.put(
  //        Screen.GUEST_HOME, new
  // FXMLLoader(getClass().getResource(Screen.GUEST_HOME.getFilename())));
  //        loaderMap.put(
  //            Screen.PATIENT_HOME,
  //            new FXMLLoader(getClass().getResource(Screen.PATIENT_HOME.getFilename())));
  //        loaderMap.put(Screen.MENU, new
  //     FXMLLoader(getClass().getResource(Screen.MENU.getFilename())));
  //        loaderMap.put(
  //            Screen.GUEST_MENU, new
  //     FXMLLoader(getClass().getResource(Screen.GUEST_MENU.getFilename())));
  //        loaderMap.put(Screen.ABOUT, new
  //     FXMLLoader(getClass().getResource(Screen.ABOUT.getFilename())));
  //        loaderMap.put(Screen.MEAL, new
  //     FXMLLoader(getClass().getResource(Screen.MEAL.getFilename())));
  //        loaderMap.put(
  //            Screen.CONFERENCE, new
  //     FXMLLoader(getClass().getResource(Screen.CONFERENCE.getFilename())));
  //        loaderMap.put(
  //            Screen.FLOWER, new FXMLLoader(getClass().getResource(Screen.FLOWER.getFilename())));
  //        loaderMap.put(
  //            Screen.FURNITURE, new
  //     FXMLLoader(getClass().getResource(Screen.FURNITURE.getFilename())));
  //        loaderMap.put(
  //            Screen.OFFICE_SUPPLY,
  //            new FXMLLoader(getClass().getResource(Screen.OFFICE_SUPPLY.getFilename())));
  //        loaderMap.put(
  //            Screen.GIFT_BASKET,
  //            new FXMLLoader(getClass().getResource(Screen.GIFT_BASKET.getFilename())));
  //        loaderMap.put(
  //            Screen.REQUEST_HISTORY,
  //            new FXMLLoader(getClass().getResource(Screen.REQUEST_HISTORY.getFilename())));
  //        loaderMap.put(
  //            Screen.CONGRATS_PAGE,
  //            new FXMLLoader(getClass().getResource(Screen.CONGRATS_PAGE.getFilename())));
  //        loaderMap.put(
  //            Screen.CONGRATS_SIGNUP_PAGE,
  //            new FXMLLoader(getClass().getResource(Screen.CONGRATS_SIGNUP_PAGE.getFilename())));
  //        loaderMap.put(Screen.HELP, new
  //     FXMLLoader(getClass().getResource(Screen.HELP.getFilename())));
  //        loaderMap.put(
  //            Screen.GUEST_HELP, new
  //     FXMLLoader(getClass().getResource(Screen.GUEST_HELP.getFilename())));
  //        loaderMap.put(
  //            Screen.EXIT_PAGE, new
  //     FXMLLoader(getClass().getResource(Screen.EXIT_PAGE.getFilename())));
  //        loaderMap.put(
  //            Screen.ALERT_REQUEST,
  //            new FXMLLoader(getClass().getResource(Screen.ALERT_REQUEST.getFilename())));
  //        loaderMap.put(
  //            Screen.SIGNUP_PAGE,
  //            new FXMLLoader(getClass().getResource(Screen.SIGNUP_PAGE.getFilename())));
  //        loaderMap.put(
  //            Screen.LOGIN_TABLE,
  //            new FXMLLoader(getClass().getResource(Screen.LOGIN_TABLE.getFilename())));
  //        loaderMap.put(
  //            Screen.EMPLOYEETABLE_PAGE,
  //            new FXMLLoader(getClass().getResource(Screen.EMPLOYEETABLE_PAGE.getFilename())));
  //  }

  //  public static void clearCache() {
  //    pageCache.clear();
  //  }

  //  public static void navigate(final Screen screen) {
  //    final String filename = screen.getFilename();
  //
  //    try {
  //      // Check if the page view is already cached
  //      Node pageView = pageCache.get(filename);
  //
  //      // If the page view is not cached, load it and add it to the cache
  //      if (pageView == null) {
  //        final var resource = CApp.class.getResource(filename);
  //        final FXMLLoader loader = new FXMLLoader(resource);
  //        pageView = loader.load();
  //        pageCache.put(filename, pageView);
  //      }
  //
  //      // Set the page view as the center of the root pane
  //      CApp.getRootPane().setCenter(pageView);
  //      updateMenu();
  //
  //    } catch (IOException | NullPointerException e) {
  //      e.printStackTrace();
  //    }
  //  }
}
