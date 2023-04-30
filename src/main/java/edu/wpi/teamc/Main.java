package edu.wpi.teamc;

import java.util.logging.Logger;

public class Main {
  public static final Logger logger =
      Logger.getLogger("main"); // need to copy this and then just use logger. commands

  public static void main(String[] args) {
    //    try (FileInputStream fis = new FileInputStream("logging.properties")) {
    //      LogManager.getLogManager().readConfiguration(fis);
    //
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }

    logger.info("Start application");
    CApp.launch(CApp.class, args);

    //    ImportCSV importCSV = new ImportCSV();
    //    boolean test =
    //        importCSV.importMapCSV(
    //            "src/main/resources/edu/wpi/teamc/Node.csv",
    //            "src/main/resources/edu/wpi/teamc/Edge.csv",
    //            "src/main/resources/edu/wpi/teamc/Move.csv",
    //            "src/main/resources/edu/wpi/teamc/LocationName.csv");

    //
    //    System.out.println(test);
  }
}
