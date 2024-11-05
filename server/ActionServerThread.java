package server;

import java.net.*;
import java.io.*;

public class ActionServerThread extends Thread {
  private Socket actionSocket = null;
  private SharedActionState mySharedActionStateObject;
  private String myActionServerThreadName;

  public ActionServerThread(Socket actionSocket, String ActionServerThreadName, SharedActionState SharedObject) {
    this.actionSocket = actionSocket;
    mySharedActionStateObject = SharedObject;
    myActionServerThreadName = ActionServerThreadName;
  }

  public void run() {
    try {
      System.out.println(myActionServerThreadName + " initializing.");
      PrintWriter out = new PrintWriter(actionSocket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(actionSocket.getInputStream()));
      String inputLine;

      while ((inputLine = in.readLine()) != null) {
        System.out.println(myActionServerThreadName + " received: " + inputLine); // Log received message
        String outputLine;

        // Get a lock and process the input
        try {
          mySharedActionStateObject.acquireLock();
          outputLine = mySharedActionStateObject.processInput(inputLine);
          out.println(outputLine);
          System.out.println(myActionServerThreadName + " sending: " + outputLine); // Log sent response
          mySharedActionStateObject.releaseLock();
        } catch (InterruptedException e) {
          System.err.println("Failed to get lock when reading: " + e);
        }
      }

      out.close();
      in.close();
      actionSocket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
