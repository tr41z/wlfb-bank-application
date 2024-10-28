package server;

import java.net.*;
import java.io.*;

public class ActionServer {
  public static void main(String[] args) throws IOException {
    ServerSocket actionServerSocket = null;
    boolean listening = true;
    String actionServerName = "ActionServer";
    int actionServerNumber = 4545;

    double accountBalance = 1000.0;

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
      new ActionServerThread(actionServerSocket.accept(), "CLIENT1", new SharedActionState(accountBalance)).start();
      new ActionServerThread(actionServerSocket.accept(), "CLIENT2", new SharedActionState(accountBalance)).start();
      new ActionServerThread(actionServerSocket.accept(), "CLIENT3", new SharedActionState(accountBalance)).start();

      System.out.println("New " + actionServerName + " thread started.");
    }

    // Close the server socket after use
    actionServerSocket.close();
  }
}
