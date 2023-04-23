package edu.wpi.teamc.dao.displays;

import edu.wpi.teamc.dao.IOrm;

import java.sql.Date;
import java.util.List;

public class SignMove implements IOrm {

    int id ;
    Date date;
    List<SignDirection> directions;

    public SignMove(int id, Date date, List<SignDirection> directions) {
        this.id = id;
        this.date = date;
        this.directions = directions;
    }
    public SignMove() {}
}
