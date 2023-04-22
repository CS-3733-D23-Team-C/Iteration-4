package edu.wpi.teamc.dao.requests;

public interface IRequest {
  int getRequestID();

  STATUS getStatus();

  String getAssignedto();

  void setAssignedto(String s);

  void setStatus(STATUS s);
}
