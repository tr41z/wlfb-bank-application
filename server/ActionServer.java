package server;

import java.net.*;
import java.io.*;

public class ActionServer {
  public static void main(String[] args) throws IOException {
    ServerSocket actionServerSocket = null;
    boolean listening = true;
    String actionServerName = "ActionServer";
    int actionServerNumber = 4545;
    double sharedVariable = 100;

    // Create the shared object in the global scope
    SharedActionState ourSharedActionStateObject = new SharedActionState(sharedVariable);

    // Create the server socket
    try {
      actionServerSocket = new ServerSocket(actionServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start " + actionServerName + " on the specified port.");
      System.exit(-1);
    }

    System.out.println(actionServerName + " started");

    // Handle client connections - only four clients at a time
    while (listening) {
      // Start four threads, one for each client connection
      new ActionServerThread(actionServerSocket.accept(), "ActionServerThread1", ourSharedActionStateObject).start();
      new ActionServerThread(actionServerSocket.accept(), "ActionServerThread2", ourSharedActionStateObject).start();
      new ActionServerThread(actionServerSocket.accept(), "ActionServerThread3", ourSharedActionStateObject).start();
      new ActionServerThread(actionServerSocket.accept(), "ActionServerThread4", ourSharedActionStateObject).start();

      System.out.println("New " + actionServerName + " thread started.");
    }

    // Close the server socket after use
    actionServerSocket.close();
  }
}
