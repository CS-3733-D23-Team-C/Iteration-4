package edu.wpi.teamname.dao.requests;

import lombok.Getter;
import lombok.Setter;

public class ConferenceRoom {
    @Getter @Setter
    private String longName;
    private String shortName;
    private Boolean availability;

    public ConferenceRoom(String longName, String shortName, Boolean availability) {
        this.longName = longName;
        this.shortName = shortName;
        this.availability = availability;
    }
    @Override
    public String toString() {
        return this.longName;
    }
}
