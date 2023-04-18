package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.HospitalSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginDaoTest {

  @Test
  public void testAddUser1() {
    Login login = new Login("Aaron", "password", "Admin");
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testAddUser2() {
    LoginDao loginDao = new LoginDao();
    Login login = new Login("Randy", "password", "Admin");
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testDeleteUser1() {
    Login login = new Login("randy", "password", "Admin");
    Assertions.assertEquals(login, HospitalSystem.deleteRow(login));
  }
}
