package edu.wpi.teamc;

import static edu.wpi.teamc.languageHelpers.LanguageHolder.language_choice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;


import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.animation.PauseTransition;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CApp extends Application {

  public static List<String> Home_Chinese_list;
  public static List<String> Home_English_list;
  public static List<String> Admin_Home_English_list;
  public static List<String> Admin_Home_Chinese_list;
  public static List<String> Menu_English_list;
  public static List<String> Menu_Chinese_list;
  public static List<String> Meal_English_list;
  public static List<String> Meal_Chinese_list;
  public static List<String> Office_Supply_English_list;
  public static List<String> Office_Supply_Chinese_list;
  public static List<String> Flower_English_list;
  public static List<String> Flower_Chinese_list;
  public static List<String> Furniture_English_list;
  public static List<String> Furniture_Chinese_list;
  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Getter @Setter private static Boolean adminLoginCheck = false;
  @Getter @Setter public static Boolean wpiDB = true;
  @Getter @Setter public static Screen currScreen = Screen.HOME;
  @Getter @Setter public static String currLogin = "";

  public CApp() throws IOException {}

  @Override
  public void init() {
    log.info("Starting Up");
  }

  static boolean timeoutRunning = false;

  public static void timeOut() {
    if (timeoutRunning) return;
    timeoutRunning = true;
    // 480000
    Thread thread =
        new Thread(
            () -> {
              try {
                Thread.sleep(480000);
                Platform.runLater(
                    () -> {
                      CApp.logoutPopUp();
                    });
              } catch (InterruptedException e) {
                timeoutRunning = false;
                e.printStackTrace();
              }
            });
    thread.start();
  }

  static volatile boolean logoutOpen = false;

  public static void logoutPopUp() {
    if (logoutOpen || currScreen.equals(Screen.SCREENSAVER)) return;
    if (currScreen.equals(Screen.HOME)) {
      logoutOpen = false;
      CApp.setAdminLoginCheck(false);
      CApp.currScreen = Screen.SCREENSAVER;
      //            Navigation.clearCache();
      Navigation.navigate(Screen.SCREENSAVER);
      Navigation.setMenuType(Navigation.MenuType.DISABLED);
      return;
    }
    BorderPane borderPane = new BorderPane();

    // Stuff to show on pop up
    VBox vBox = new VBox();
    Text headerText = new Text("Logging out...");
    Text building = new Text("Counting down...");
    MFXButton cancel = new MFXButton("Cancel");
    MFXButton logoutButton = new MFXButton("Logout");
    vBox.getChildren().addAll(headerText, building, cancel, logoutButton);

    // set styles
    headerText.getStyleClass().add("Header");
    building.getStyleClass().add("Text");
    cancel.getStyleClass().add("MFXbutton");
    logoutButton.getStyleClass().add("MFXbutton");
    borderPane.getStyleClass().add("scenePane");

    // set object locations
    int lay_x = 50;
    int lay_y = 40;
    headerText.setLayoutX(lay_x);
    headerText.setLayoutY(lay_y);
    building.setLayoutX(lay_x);
    building.setLayoutY(lay_y + 35);

    cancel.setLayoutX(lay_x);
    cancel.setLayoutY(lay_y + 55);
    logoutButton.setLayoutX(lay_x + 125);
    logoutButton.setLayoutY(lay_y + 55);
    cancel.setMaxWidth(100);
    logoutButton.setMaxWidth(100);

    // Set and show screen
    AnchorPane aPane = new AnchorPane();
    aPane.getChildren().addAll(headerText, building, cancel, logoutButton);
    borderPane.getChildren().add(aPane);
    Scene scene = new Scene(borderPane, 330, 165);
    scene
        .getStylesheets()
        .add(Main.class.getResource("views/pages/map/MapEditorPopUps.css").toString());
    borderPane.relocate(0, 0);
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.setTitle("Log Out");
    stage.setAlwaysOnTop(true);
    logoutOpen = true;
    stage.show();
    Thread thread =
        new Thread() {
          @Override
          public void run() {
            for (int i = 10; i >= 0; i--) {
              try {
                building.setText(i + " seconds");
                Thread.sleep(1000);
                if (i == 0) {
                  building.setText("Logging out...");
                }
                if (Thread.currentThread().isInterrupted()) {
                  break;
                }
              } catch (InterruptedException e) {
              }
            }
            if (Thread.currentThread().isInterrupted()) {
              return;
            }
            Platform.runLater(
                new Runnable() {
                  @Override
                  public void run() {
                    if (!stage.isShowing()) {
                      stage.close();
                      CApp.timeOut();
                      return;
                    } else {
                      stage.close();
                      logoutOpen = false;
                      CApp.setAdminLoginCheck(false);
                      CApp.currScreen = Screen.SCREENSAVER;
                      //            Navigation.clearCache();
                      Navigation.navigate(Screen.SCREENSAVER);
                      Navigation.setMenuType(Navigation.MenuType.DISABLED);
                    }
                  }
                });
          }
        };
    thread.start();
    cancel.setOnAction(
        (event -> {
          thread.interrupt();
          stage.close();
          logoutOpen = false;
        }));

    logoutButton.setOnAction(
        (event -> {

          thread.interrupt();

          language_choice = 0;

          stage.close();
          logoutOpen = false;
          CApp.setAdminLoginCheck(false);
          CApp.currScreen = Screen.HOME;
          //          Navigation.clearCache();
          Navigation.navigate(Screen.HOME);
          Navigation.setMenuType(Navigation.MenuType.DISABLED);
        }));
  }

  //  public void getAllTextForAllPages() {
  //    HomeController.getAllTexts();
  //    System.out.println("All texts loaded");
  //  }

  //    String filePath = "src/main/java/edu/wpi/teamc/languageHelpers/HomeSpanish.json";
  //    String jsonString = new String(Files.readAllBytes(Paths.get(filePath)),
  // StandardCharsets.UTF_8);
  //    JSONArray jsonArray = new JSONArray(jsonString);
  //    public static List<String> list = new ArrayList<>();

  @Override
  public void start(Stage primaryStage) throws Exception {

    Home_Chinese_list = loadJson(Home_Chinese);
    Home_English_list = loadJson(Home_English);
    Admin_Home_English_list = loadJson(Admin_Home_English);
    Admin_Home_Chinese_list = loadJson(Admin_Home_Chinese);
    Menu_English_list = loadJson(Menu_English);
    Menu_Chinese_list = loadJson(Menu_Chinese);
    Meal_English_list = loadJson(Meal_English);
    Meal_Chinese_list = loadJson(Meal_Chinese);
    Office_Supply_English_list = loadJson(Supply_English);
    Office_Supply_Chinese_list = loadJson(Supply_Chinese);
    Flower_English_list = loadJson(Flower_English);
    Flower_Chinese_list = loadJson(Flower_Chinese);
    Furniture_English_list = loadJson(Furniture_English);
    Furniture_Chinese_list = loadJson(Furniture_Chinese);

    /* primaryStage is generally only used if one of your components require the stage to display */
    CApp.primaryStage = primaryStage;
    final FXMLLoader loader = new FXMLLoader(CApp.class.getResource("views/Root.fxml"));
    final BorderPane root = loader.load();

    CApp.rootPane = root;
    // GET Texts
    // getAllTextForAllPages();

    final Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
    Navigation.navigate(Screen.HOME);

    CApp.timeOut();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }

  public List<String> loadJson(String filePath) throws Exception {
    File file = new File(filePath);
    ObjectMapper objectMapper = new ObjectMapper();
    List<String> myList = objectMapper.readValue(file, new TypeReference<List<String>>() {});
    return myList;
  }

  String Home_Chinese = "src/main/java/edu/wpi/teamc/languageHelpers/Home/HomeChinese.json";
  String Home_English = "src/main/java/edu/wpi/teamc/languageHelpers/Home/HomeEnglish.json";
  String Admin_Home_English =
      "src/main/java/edu/wpi/teamc/languageHelpers/AdminHome/AdminHomeEnglish.json";
  String Admin_Home_Chinese =
      "src/main/java/edu/wpi/teamc/languageHelpers/AdminHome/AdminHomeChinese.json";
  String Menu_English = "src/main/java/edu/wpi/teamc/languageHelpers/Menu/MenuEnglish.json";
  String Menu_Chinese = "src/main/java/edu/wpi/teamc/languageHelpers/Menu/MenuChinese.json";
  String Meal_English = "src/main/java/edu/wpi/teamc/languageHelpers/Meal/MealEnglish.json";
  String Meal_Chinese = "src/main/java/edu/wpi/teamc/languageHelpers/Meal/MealChinese.json";
  String Supply_English =
      "src/main/java/edu/wpi/teamc/languageHelpers/Stationary/StationaryEnglish.json";
  String Supply_Chinese =
      "src/main/java/edu/wpi/teamc/languageHelpers/Stationary/StationaryChinese.json";
  String Flower_English = "src/main/java/edu/wpi/teamc/languageHelpers/Flower/FlowerEnglish.json";
  String Flower_Chinese = "src/main/java/edu/wpi/teamc/languageHelpers/Flower/FlowerChinese.json";
  String Furniture_English =
      "src/main/java/edu/wpi/teamc/languageHelpers/Furniture/FurnitureEnglish.json";
  String Furniture_Chinese =
      "src/main/java/edu/wpi/teamc/languageHelpers/Furniture/FurnitureChinese.json";
}
