package client;

import java.io.*;
import java.net.*;

public class ActionClient3 {
    public static void main(String[] args) throws IOException {
        // Set up the socket, output, and input streams
        Socket actionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int actionSocketNumber = 4545;
        String actionServerName = "localhost";
        String actionClientID = "ActionClient3";

        try {
            // Initialize the socket and IO streams
            actionClientSocket = new Socket(actionServerName, actionSocketNumber);
            out = new PrintWriter(actionClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(actionClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + actionServerName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to port: " + actionSocketNumber);
            System.exit(1);
        }

        // Set up input stream for user input
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialized " + actionClientID + " client and IO connections");

        // Client sends first, waits for server's response
        while (true) {
            // Read user input
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                // Send the user input to the server
                System.out.println(actionClientID + " sending " + fromUser + " to ActionServer");
                out.println(fromUser);
            }

            // Read and display the server's response
            fromServer = in.readLine();
            if (fromServer != null) {
                System.out.println(actionClientID + " received " + fromServer + " from ActionServer");
            }
        }

        // Clean up resources (commented out as loop runs indefinitely)
        // out.close();
        // in.close();
        // stdIn.close();
        // actionClientSocket.close();
    }
}
