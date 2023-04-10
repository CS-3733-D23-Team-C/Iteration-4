package edu.wpi.teamc.dao.requests;

public enum STATUS {
  PENDING,
  IN_PROGRESS,
  COMPLETE;

  public String toString() {
    return this.name();
  }
}
