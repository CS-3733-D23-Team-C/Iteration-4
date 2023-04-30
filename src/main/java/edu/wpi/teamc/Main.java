package edu.wpi.teamc;

import static java.lang.System.exit;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
  public static final Logger logger =
      Logger.getLogger("main"); // need to copy this and then just use logger. commands

  public static void main(String[] args) {
    //    try (FileInputStream fis = new FileInputStream("logging.properties")) {
    //      LogManager.getLogManager().readConfiguration(fis);
    //
    //    } catch (IOException e) {
    //      e.printStackTrace();
    //    }

    logger.info("Start application");
    CApp.launch(CApp.class, args);

    Scanner scan = new Scanner(System.in);
    while (true) {
      String command = scan.nextLine();
      if (command.equals("exit")) {
        exit(0);
        break;
      }
      if (command.equals("help")) {
        System.out.println("Commands: ");
        System.out.println("exit - exit the program");
        System.out.println("help - display this help message");
        System.out.println("test screensaver - test screensaver");
      }
      if (command.equals("test screensaver")) {
        try {
          CApp.timerPopUp();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }
}
