package edu.wpi.teamname.Shapes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FizzBuzzTest {

  @Test
  void answerForThree() {
    int answer = 3;
    FizzBuzz FizzBuzzThree = new FizzBuzz(answer);
    assertEquals("fizz", FizzBuzzThree.answer(answer));
  }

  @Test
  void answerForSix() {
    int answer = 6;
    FizzBuzz FizzBuzzSix = new FizzBuzz(answer);
    assertEquals("fizz", FizzBuzzSix.answer(answer));
  }

  @Test
  void answerForFive() {
    int answer = 5;
    FizzBuzz FizzBuzzFive = new FizzBuzz(answer);
    assertEquals("buzz", FizzBuzzFive.answer(answer));
  }

  @Test
  void answerForTen() {
    int answer = 10;
    FizzBuzz FizzBuzzTen = new FizzBuzz(answer);
    assertEquals("buzz", FizzBuzzTen.answer(answer));
  }

  @Test
  void answerForFifteen() {
    int answer = 15;
    FizzBuzz FizzBuzzFifteen = new FizzBuzz(answer);
    assertEquals("fizzbuzz", FizzBuzzFifteen.answer(answer));
  }

  @Test
  void answerForThirteen() {
    int answer = 13;
    FizzBuzz FizzBuzzThirteen = new FizzBuzz(answer);
    assertEquals("13", FizzBuzzThirteen.answer(answer));
  }

  @Test
  void answerForThirteenWhiz() {
    int answer = 13;
    FizzBuzzWhiz FizzBuzzWhizThirteen = new FizzBuzzWhiz(answer);
    assertEquals("prime", FizzBuzzWhizThirteen.answer(answer));
  }
}
