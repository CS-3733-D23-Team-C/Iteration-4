package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IDao;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PatientDaoTest {
  // also need to test for the phone number being valid at some point
  // see if I can use Twilio to format the phone number bc it chels for invlaid phone numbre anyway
  // and goes through with the try ctach

  @Test
  // testing fetch patient correctly
  public void activePhoneTest() {
    Instant inst = LocalDateTime.of(2023, 4, 24, 0, 0, 0).toInstant(ZoneOffset.of("-0400"));

    PatientUserDao pdao = new PatientUserDao();
    PatientUser p2 =
        new PatientUser(
            123, "taya", Timestamp.from(inst), Timestamp.from(inst), "+14086858863", "A2", true);
    PatientUser pTest = pdao.fetchPatient("taya", "+14086858863");
    Assertions.assertEquals(pTest, p2);
  }

  @Test
  // testing when the active phone is null; need to see
  public void nullPhoneTest() {
    PatientUserDao pdao = new PatientUserDao();
    List<String> phones = new ArrayList<>();
    phones.add("+14086858863");
    Assertions.assertEquals(pdao.listActivePhone(), phones);
  }

  @Test
  public void testUpdate() {
    PatientUserDao pdao = new PatientUserDao();
    IDao<PatientUser, Integer> dao = new PatientUserDao();
    PatientUser pat1 = new PatientUser("bob");
    PatientUser pat2 = new PatientUser("bob");

    dao.updateRow(pat1, pat2);
  }

  @Test
  // testing when the active phone is false
  public void falsePhoneTest() {}

  @Test
  // patient with this name and number does not exist
  public void nullPatient() {}

  @Test
  // patient with wrong name but correct phone number
  public void wrongNamePatient() {}

  @Test
  // patient with this name but wrong phone number
  public void wrongPhonePatient() {}

  @Test
  // correct patient
  public void correctPatient() {}

  @Test
  // invalid phone number in the database
  public void nullPhone() {}
}
