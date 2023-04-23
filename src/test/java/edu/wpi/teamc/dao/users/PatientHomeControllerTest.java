package edu.wpi.teamc.dao.users;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class PatientHomeControllerTest {


  @Test
  public void testDatabaseExistence() {
    PatientUser pat =
        new PatientUser(123, "taya", "01/12/2023", "01/20/2023", "+14086858863", "A2");
    Assertions.assertEquals(true, pat.validPatient("taya", "+14086858863"));
  }
}
