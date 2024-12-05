/**
 * The ActionServer class is a multi-threaded server that listens for client connections 
 * on port 4545. It handles client requests concurrently, using a shared state to manage 
 * three accounts with an initial balance of 1000 units each. Each client connection is 
 * handled by a separate thread (ActionServerThread).
*/

package server;

import java.net.*;
import java.io.*;

public class ActionServer {
  public static void main(String[] args) throws IOException {
    ServerSocket actionServerSocket = null;
    boolean listening = true;
    int actionServerNumber = 4545;

    // Initialize shared state for three accounts with 1000 units each
    SharedActionState sharedState = new SharedActionState(new double[] { 1000, 1000, 1000 }); // accounts A, B, C

    try {
      actionServerSocket = new ServerSocket(actionServerNumber);
    } catch (IOException e) {
      System.err.println("Could not start server on port " + actionServerNumber);
      System.exit(-1);
    }
    System.out.println("ActionServer started");

    while (listening) {
      new ActionServerThread(actionServerSocket.accept(), "ActionServerThread1", sharedState).start();
      new ActionServerThread(actionServerSocket.accept(), "ActionServerThread2", sharedState).start();
      new ActionServerThread(actionServerSocket.accept(), "ActionServerThread3", sharedState).start();
    }

    actionServerSocket.close();
  }
}
