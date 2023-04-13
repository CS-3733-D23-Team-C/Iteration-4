package edu.wpi.teamc.dao.map;

import java.sql.Timestamp;
import lombok.Getter;

@Getter
public class MapHistory {
  private int id;
  private String action;
  private String nodepk;
  private String table;
  private Timestamp timestamp;

  public MapHistory(int id, String action, String nodepk, String table, Timestamp timestamp) {
    this.id = id;
    this.action = action;
    this.nodepk = nodepk;
    this.table = table;
    this.timestamp = timestamp;
  }

  public MapHistory(String action, String nodepk, String table, Timestamp timestamp) {
    this.action = action;
    this.nodepk = nodepk;
    this.table = table;
    this.timestamp = timestamp;
  }
}
