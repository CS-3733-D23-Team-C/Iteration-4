package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IOrm;
import io.github.palexdev.materialfx.effects.ripple.base.IRipple;

public abstract class AbsLogin implements IOrm {
    private String username;
    private String password;
    private String status;


    public AbsLogin(String username, String password, String status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

}