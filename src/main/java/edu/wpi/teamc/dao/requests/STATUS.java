package edu.wpi.teamc.dao.requests;

public enum STATUS {
  PENDING("Pending"),
  IN_PROGRESS("In Progress"),
  COMPLETE("Complete"),
  CANCELLED("Cancelled");

  private String status;

  STATUS(String status) {
    this.status = status;
  }

  //  public String getStatus(String status) {
  //    return
  //  }
  public String getStatus() {
    return status;
  }
}
