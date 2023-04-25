package edu.wpi.teamc.dao.users;

import static edu.wpi.teamc.dao.users.PERMISSIONS.ADMIN;
import static edu.wpi.teamc.dao.users.PERMISSIONS.STAFF;

import edu.wpi.teamc.dao.HospitalSystem;
import edu.wpi.teamc.dao.users.login.Login;
import edu.wpi.teamc.dao.users.login.LoginDao;
import java.sql.SQLException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginDaoTest {

  @Test
  public void testAddUser1() {
    Login login = new Login("jeff", "jeff", STAFF);
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testAddUser2() {
    LoginDao loginDao = new LoginDao();
    Login login = new Login("mark", "mark", STAFF);
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testDeleteUser1() {
    Login login = new Login("randy", "password", ADMIN);
    Assertions.assertEquals(login, HospitalSystem.deleteRow(login));
  }

  @Test
  public void testdefaultUser2() {
    Login login = new Login("admin", "admin", ADMIN);
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testdefaultUser3() {
    Login login = new Login("staff", "staff", STAFF);
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testDeleteUser4() {
    Login login = new Login("a", "a", ADMIN);
    Assertions.assertEquals(login, HospitalSystem.addRow(login));
  }

  @Test
  public void testdefaultUser5() {
    Login login = new Login("otp", "otp", STAFF);
    // login.generateOTP();
  }

  @Test
  public void testPassword4() throws SQLException {
    LoginDao loginDao = new LoginDao();
    Login testAccount = loginDao.fetchObject("otp");
    //    Assertions.assertEquals("WIZJMPNIB2JRQMOO", testAccount.getOtp());
    // Assertions.assertEquals(true, testAccount.checkOTP("058870"));
  }
}
