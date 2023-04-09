package edu.wpi.teamc.Shapes;

class RectangleTest {

  @org.junit.jupiter.api.Test
  void getPermimeter() {
    Rectangle r = new Rectangle(5, 10);
    assertEquals(30, r.getPermimeter());
  }

  @org.junit.jupiter.api.Test
  void getArea() {
    Rectangle r = new Rectangle(5, 10);
    assertEquals(50, r.getArea());
  }
}
