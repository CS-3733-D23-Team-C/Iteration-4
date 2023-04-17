package edu.wpi.teamc.dao.users;

import edu.wpi.teamc.dao.IOrm;
import io.github.palexdev.materialfx.effects.ripple.base.IRipple;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter

public abstract class AbsLogin implements IOrm {
    private String username;

    private String password;
    private String permissions;


    public AbsLogin(String username, String password, String permissions) {
        this.username = username;
        this.password = password;
        this.permissions = permissions;
    }

}