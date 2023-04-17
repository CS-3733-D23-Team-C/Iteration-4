package edu.wpi.teamc.dao.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Employee {

  int empid;
  String name;
  String department;
  String position;

  public Employee(int empid, String name, String department, String position) {
    this.empid = empid;
    this.name = name;
    this.department = department;
    this.position = position;
  }

  public Employee(String name, String department, String position) {
    this.name = name;
    this.department = department;
    this.position = position;
  }
}
