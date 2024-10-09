package Server;

import java.io.*;
// import java.net.*;

public class ServerImpl {
    public static void start_server() throws IOException {}

    // Starting server
    public static void main(String[] args) { 
        try {
            start_server(); 
        } catch (IOException e) {
            System.err.println("Unable to start server: " + e);
        }
    }
}
