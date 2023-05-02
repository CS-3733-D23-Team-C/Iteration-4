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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Getter @Setter private static Boolean adminLoginCheck = false;
  @Getter @Setter public static Boolean wpiDB = true;
  @Getter @Setter public static Screen currScreen = Screen.HOME;

  public CApp() throws IOException {}

  @Override
  public void init() {
    log.info("Starting Up");
  }

  public static void timeOut() {
    // 480000
    PauseTransition startPause = new PauseTransition(Duration.millis(480000));
    startPause.setOnFinished(
        (event -> {
          CApp.logoutPopUp();
        }));
    startPause.play();
  }

  static volatile boolean logoutOpen = false;

  public static void logoutPopUp() {
    if (logoutOpen) return;
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
    int[] seconds = new int[1];
    seconds[0] = 10;
    Thread thread =
        new Thread() {
          @Override
          public void run() {
            for (int i = 0; i < 10; i++) {
              try {
                building.setText(seconds[0] + " seconds");
                seconds[0]--;
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            }
          }
        };
    thread.start();
    cancel.setOnAction(
        (event -> {
          stage.close();
          logoutOpen = false;
        }));

    logoutButton.setOnAction(
        (event -> {
          language_choice = 0;
          stage.close();
          logoutOpen = false;
          CApp.setAdminLoginCheck(false);
          CApp.currScreen = Screen.HOME;
          Navigation.clearCache();
          Navigation.navigate(Screen.HOME);
          Navigation.setMenuType(Navigation.MenuType.DISABLED);
        }));

    PauseTransition startPause = new PauseTransition(Duration.millis(10000));
    startPause.setOnFinished(
        (event -> {
          if (!stage.isShowing()) {
            stage.close();
            CApp.timeOut();
            return;
          } else {
            stage.close();
            logoutOpen = false;
            CApp.setAdminLoginCheck(false);
            CApp.currScreen = Screen.SCREENSAVER;
            Navigation.clearCache();
            Navigation.navigate(Screen.SCREENSAVER);
            Navigation.setMenuType(Navigation.MenuType.DISABLED);
          }
        }));
    startPause.play();
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
}
