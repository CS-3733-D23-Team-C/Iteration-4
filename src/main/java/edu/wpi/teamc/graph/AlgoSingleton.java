package edu.wpi.teamc.graph;

public enum AlgoSingleton {
  INSTANCE;
  String algoType;

  public String getType() {
    return algoType;
  }

  public void setType(String algoType) {
    this.algoType = algoType;
  }
}
