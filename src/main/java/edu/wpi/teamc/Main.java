package edu.wpi.teamc;

import edu.wpi.teamc.dao.ImportCSV;
import java.sql.SQLException;

public class Main {

  public static void main(String[] args) throws SQLException {
    //    CApp.launch(CApp.class, args);

    ImportCSV importCSV = new ImportCSV();
    importCSV.importAllDisplaysCSV(
        "src/main/resources/edu/wpi/teamc/Alert.csv",
        "src/main/resources/edu/wpi/teamc/Signage.csv");
    //    ImportCSV importCSV = new ImportCSV();
    //    boolean test =
    //        importCSV.importAllCSV(
    //            "src/main/resources/edu/wpi/teamc/Node.csv",
    //            "src/main/resources/edu/wpi/teamc/Edge.csv",
    //            "src/main/resources/edu/wpi/teamc/Move.csv",
    //            "src/main/resources/edu/wpi/teamc/LocationName.csv");
    //
    //    System.out.println(test);
    System.out.println("Hello World!");
  }
}
