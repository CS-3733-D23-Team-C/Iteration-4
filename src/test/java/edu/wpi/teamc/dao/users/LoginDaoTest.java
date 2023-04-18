package edu.wpi.teamc.dao.users;

import static edu.wpi.teamc.dao.users.PERMISSIONS.ADMIN;

import edu.wpi.teamc.dao.HospitalSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginDaoTest {

  @Test
  public void testAddUser1() {
    Login login = new Login("Aaron", "password", ADMIN);
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testAddUser2() {
    LoginDao loginDao = new LoginDao();
    Login login = new Login("Randy", "password", ADMIN);
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testDeleteUser1() {
    Login login = new Login("randy", "password", ADMIN);
    Assertions.assertEquals(login, HospitalSystem.deleteRow(login));
  }
}
