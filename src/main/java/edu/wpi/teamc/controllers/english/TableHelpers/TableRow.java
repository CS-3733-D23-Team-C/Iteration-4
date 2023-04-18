package edu.wpi.teamc.controllers.english.TableHelpers;

import lombok.Getter;
import lombok.Setter;

public class TableRow {
  @Getter @Setter private int index;
  private int id;
  private String username;
  private String name;
  private String department;
  private String position;

  public TableRow() {
    this.id = 0;
    this.username = "";
    this.name = "";
    this.department = "";
    this.position = "";
  }

  public TableRow(int id, String username, String name, String department, String position) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.department = department;
    this.position = position;
  }

  public TableRow(
      int id, String username, String name, String department, String position, int index) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.department = department;
    this.position = position;
    this.index = index;
  }

  public int getIndex() {
    return index;
  }
}
