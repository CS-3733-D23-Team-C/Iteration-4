package edu.wpi.teamc.controllers.Robot;

//import edu.wpi.teamc.robotStuff.jSerialComm;
//import edu.wpi.teamc.robotStuff.jSerialComm-2.9.3.jar.com.fazecast.jSerialComm.SerialPort;
//import edu.wpi.teamc.robotStuff.com.fazecast.jSerialComm.*;
//import robotStuff.com.fazecast.jSerialComm.*;
import com.fazecast.jSerialComm.*;




public class RobotController {
    SerialPort[] ports = SerialPort.getCommPorts();
      if (ports.length != 0) {
        System.out.println(ports[0]);
        ports[0].setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
        ports[0].setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 16); // Blocking write

        if (ports[0].openPort(16)) {
            int numMessages = currentPath.size();
            byte[] bytes = {
                    (byte) (numMessages / 1000 % 10),
                    (byte) (numMessages / 100 % 10),
                    (byte) (numMessages / 10 % 10),
                    (byte) (numMessages % 10),
                    10
            };
            ports[0].writeBytes(bytes, bytes.length);
        }

        for (Node node : currentPath) {
            if (ports[0].isOpen() || ports[0].openPort(16)) {
                //            System.out.println("Port opened successfully");
                int x = node.getXCoord();
                int y = node.getYCoord();
                byte[] bytes = {
                        (byte) (x / 1000 % 10),
                        (byte) (x / 100 % 10),
                        (byte) (x / 10 % 10),
                        (byte) (x % 10),
                        10
                };
                ports[0].writeBytes(bytes, bytes.length);
                byte[] bytes2 = {
                        (byte) (y / 1000 % 10),
                        (byte) (y / 100 % 10),
                        (byte) (y / 10 % 10),
                        (byte) (y % 10),
                        10
                };
                ports[0].writeBytes(bytes2, bytes2.length);
                System.out.println(bytes.toString() + "sent");
                System.out.println(bytes2.toString() + "sent");
            } else {
                System.out.println("Failed to open port");
                System.out.println(ports[0].getLastErrorCode());
            }
        }

        ports[0].closePort();
        //        System.out.println("Port closed");
    }

    bool checkSerial1(void) {

        if(Serial.available()) {
            if(!numRecieved) {
                if (iterator == 4) {
                    char c = Serial.read();
                    numToExpect = vals[0]*1000 + vals[1]*100 + vals[2]*10 + vals[3];
                    // Serial.println(numToExpect);
                    numRecieved = true;
                    iterator = 0;
                }
                else {
                    vals[iterator] = Serial.read();
                    // Serial.println(vals[iterator]);
                    iterator ++;
                }
            }
            else {
                if (iterator == 4) {
                    char c = Serial.read();
                    if (swap){
                        xCoords[iteratorX] = vals[0]*1000 + vals[1]*100 + vals[2]*10 + vals[3];
                        // Serial.println(xCoords[iteratorX]);
                        iteratorX ++;
                        iterator = 0;
                        swap = false;
                    }
                    else{
                        yCoords[iteratorY] = vals[0]*1000 + vals[1]*100 + vals[2]*10 + vals[3];
                        // Serial.println(yCoords[iteratorY]);
                        iteratorY ++;
                        iterator = 0;
                        swap = true;
                    }
                }
                else {
                    vals[iterator] = Serial.read();
                    // Serial.println(vals[iterator]);
                    iterator ++;
                }
            }
            if (iteratorY == numToExpect) {
                // Serial.println("all data recieved");
                return true;
            }
        }
        return false;
    }

    void handleMessages() {
        destinationX = xCoords[0];
        destinationY = yCoords[0];
        chassis.targetPose(destinationX, destinationY);
        chassis.overridePose(destinationX, destinationY, 0);
        iterator = 1;
        beginDriveToPoint();
    }
}
