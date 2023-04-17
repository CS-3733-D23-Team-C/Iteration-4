package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IOrm;

public class Login implements IOrm {
    private String username;

    private String hashedPassword;
    private String permissions;

    private String salt;


    public Login(String username, String password, String permissions) {
        this.username = username;
       // this.hashedPassword = DigestUtils.sha256Hex(password);
        this.permissions = permissions;
       // this.salt = RandomStringUtils.randomAlphanumeric(32);
    }

    public boolean checkPassword(String password) {

        //check hashed password against database
        String hashedPassword = password + salt;
        return true;
    }

}