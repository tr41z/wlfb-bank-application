package server;

import java.net.*;
import java.io.*;

public class ActionServerThread extends Thread {
    private Socket actionSocket = null;
    private SharedActionState mySharedActionStateObject;
    private String myActionServerThreadName;

    // Constructor to set up the thread
    public ActionServerThread(Socket actionSocket, String actionServerThreadName, SharedActionState sharedObject) {
      // Initialize socket, shared state, and thread name
      this.actionSocket = actionSocket;
      mySharedActionStateObject = sharedObject;
      myActionServerThreadName = actionServerThreadName;
    }

    // The run method that is executed when the thread starts
    public void run() {
      try {
        System.out.println(myActionServerThreadName + " initializing.");

        // Set up output and input streams for communication
        PrintWriter out = new PrintWriter(actionSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(actionSocket.getInputStream()));
        String inputLine, outputLine;

        // Loop to process input from the client
        while ((inputLine = in.readLine()) != null) {
          // Try to acquire a lock before processing
          try {
            mySharedActionStateObject.acquireLock(); // Acquire lock
            outputLine = mySharedActionStateObject.processInput(myActionServerThreadName, inputLine); // Process input
            out.println(outputLine); // Send output back to the client
            mySharedActionStateObject.releaseLock(); // Release lock after processing
          } catch (InterruptedException e) {
            System.err.println("Failed to get lock when reading: " + e);
          }
        }

        // Close the output and input streams and socket after communication ends
        out.close();
        in.close();
        actionSocket.close();

      } catch (IOException e) {
        e.printStackTrace();
      }
  }
}
