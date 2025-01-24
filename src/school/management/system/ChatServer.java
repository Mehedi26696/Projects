package school.management.system;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static final int PORT = 12345;
    private static final Map<String, PrintWriter> userWriters = Collections.synchronizedMap(new HashMap<>());
    private static final String CHAT_LOG_FILE = "chat_log.txt";

    public static void main(String[] args) {
        System.out.println("Chat server is running...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("Error in server: " + e.getMessage());
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private String username;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Assign a username
                out.println("Enter your username:");
                username = in.readLine();
                if (username == null || username.isEmpty()) {
                    socket.close();
                    return;
                }

                synchronized (userWriters) {
                    if (userWriters.containsKey(username)) {
                        out.println("Username already taken. Disconnecting...");
                        socket.close();
                        return;
                    }
                    userWriters.put(username, out);
                }

                System.out.println(username + " has joined the chat.");
                broadcast(username + " has joined the chat.", null);

                // Handle incoming messages
                String message;
                while ((message = in.readLine()) != null) {
                    handleClientMessage(message);
                }
            } catch (IOException e) {
                System.out.println("Error handling client: " + e.getMessage());
            } finally {
                cleanup();
            }
        }

        private void handleClientMessage(String message) {
            message = message.trim();
            
            System.out.println(message);
  
            if (message.contains("@")) {
                int spaceIndex = message.indexOf(" ");
                if (spaceIndex > 1) {
                    String targetUser = message.substring(1, spaceIndex).trim();
                    String privateMessage = message.substring(spaceIndex + 1).trim();
                    sendPrivateMessage(username, targetUser, privateMessage);
                } else {
                    out.println("Invalid private message format. Use @username message");
                }
            } else {
                broadcast(username + ": " + message, username);
            }
        }

        private void sendPrivateMessage(String fromUser, String toUser, String message) {
            synchronized (userWriters) {
                PrintWriter targetWriter = userWriters.get(toUser);
                if (targetWriter != null) {
                    targetWriter.println("(Private) " + fromUser + ": " + message);
                    out.println("(Private to " + toUser + ") " + message);
                    logMessage("(Private) " + fromUser + " to " + toUser + ": " + message);
                } else {
                    out.println("User " + toUser + " not found or not connected.");
                }
            }
        }

        private void broadcast(String message, String excludeUser) {
            synchronized (userWriters) {
                for (Map.Entry<String, PrintWriter> entry : userWriters.entrySet()) {
                    if (!entry.getKey().equals(excludeUser)) {
                        entry.getValue().println(message);
                    }
                }
            }
        }

        private void logMessage(String message) {
            try (FileWriter fw = new FileWriter(CHAT_LOG_FILE, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(message + "\n");
            } catch (IOException e) {
                System.out.println("Error writing to log file: " + e.getMessage());
            }
        }

        private void cleanup() {
            if (username != null) {
                System.out.println(username + " has left the chat.");
                broadcast(username + " has left the chat.", null);
                synchronized (userWriters) {
                    userWriters.remove(username);
                }
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}







