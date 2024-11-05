package client;

import java.io.*;
import java.net.*;

public class ActionClient3 {
    private static final String ACTION_CLIENT_ID = "Client3"; // ID for this client

    public static void main(String[] args) throws IOException {
        Socket actionClientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int actionSocketNumber = 4545;
        String actionServerName = "localhost";

        try {
            actionClientSocket = new Socket(actionServerName, actionSocketNumber);
            out = new PrintWriter(actionClientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(actionClientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost ");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + actionSocketNumber);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

        System.out.println("Initialised " + ACTION_CLIENT_ID + " client and IO connections");
        System.out.println("Commands: add <value>, subtract <value>, transfer <to> <value>, view, exit");

        while (true) {
            fromUser = stdIn.readLine();
            if (fromUser != null) {
                System.out.println(ACTION_CLIENT_ID + " sending " + fromUser + " to ActionServer");
                out.println(ACTION_CLIENT_ID + ": " + fromUser);
            }
            fromServer = in.readLine();
            System.out.println(ACTION_CLIENT_ID + " received " + fromServer + " from ActionServer");

            if (fromUser.equalsIgnoreCase("exit")) {
                break;
            }
        }

        out.close();
        in.close();
        stdIn.close();
        actionClientSocket.close();
    }
}
