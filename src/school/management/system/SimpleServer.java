
package school.management.system;

import java.net.*;
import java.io.*;

public class SimpleServer {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(7000)) {
            System.out.println("Server started on port 7000...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");
                socket.close();
            }
        }
    }
}
