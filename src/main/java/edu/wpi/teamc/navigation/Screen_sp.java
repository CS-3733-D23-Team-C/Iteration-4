package edu.wpi.teamc.navigation;

public enum Screen_sp {
    //----------------NON-USING SCREENS----------------//
    ROOT_SP("views/Root_sp.fxml"),
    SERVICE_REQUEST_SP("views/ServiceRequest_sp.fxml"),
    //----------------MAP & SIGNAGE----------------//
    SIGNAGE_SP("views/SignagePage_sp.fxml"),
    ELEVATOR_SIGNAGE_SP("views/ElevatorSignage_sp.fxml"),
    FLOOR_PLAN_SP("views/FloorPlan_sp.fxml"),
    MAP_HISTORY_PAGE_SP("views/MapChangeHistory_sp.fxml"),
    PATHFINDING_PAGE_SP("views/Pathfinding_sp.fxml"),
    EDIT_MAP_SP("views/MapEditing_sp.fxml"),
    //----------------HOME & LOGIN----------------//
    LOGIN_SP("views/LoginPage_sp.fxml"),
    HOME_SP("views/Home_sp.fxml"),
    GUEST_HOME_SP("views/GuestHome_sp.fxml"),
    //----------------SERVICE REQUESTS----------------//
    MEAL_SP("views/Meal_sp.fxml"),
    MEAL_HISTORY_SP("views/MealHistory_sp.fxml"),
    CONFERENCE_SP("views/Conference_sp.fxml"),
    CONFERENCE_HISTORY_SP("views/ConferenceHistory_sp.fxml"),
    FLOWER_SP("views/Flower_sp.fxml"),
    FURNITURE_SP("views/Furniture_sp.fxml"),
    OFFICE_SUPPLY_SP("views/OfficeSupply_sp.fxml"),
    //----------------BONUS----------------//
    CONGRATS_PAGE_SP("views/congratsPage_sp.fxml"),
    HELP_SP("views/Help_sp.fxml");

    private final String filename;

    Screen_sp(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
