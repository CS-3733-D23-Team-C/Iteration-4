package edu.wpi.teamc.controllers.english.MapHelpers;

public enum HandleMapModes {

    SELECT("Select"),
    ADD("Add"),
    MODIFY("Modify"),
    REMOVE("Remove");

    private final String mode;

    HandleMapModes(String mode) {
        this.mode = mode;
    }

    public String getMapMode() {
        return mode;
    }

}
