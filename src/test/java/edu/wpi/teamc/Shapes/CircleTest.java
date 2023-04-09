package edu.wpi.teamc.Shapes;

class CircleTest {

  @org.junit.jupiter.api.Test
  void getPermimeter() {
    Circle c = new Circle(5);
    assertEquals(31.41592653589793, c.getPermimeter());
  }

  @org.junit.jupiter.api.Test
  void getArea() {
    Circle c = new Circle(5);
    assertEquals(78.53981633974483, c.getArea());
  }
}
