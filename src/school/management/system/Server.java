package school.management.system;

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Server {
    private static HashMap<String, String> faq;

    public static void main(String[] args) {
        startServer();
    }

    public static void startServer() {
        faq = new HashMap<>();
        // Add frequent question-response pairs
        faq.put("hello", "Hi there! How can I assist you?");
        faq.put("how are you", "I'm just a server, but I'm functioning perfectly!");
        faq.put("bye", "Goodbye! Have a great day!");
        faq.put("who build that website and why?", "Mehedi Hasan and ABS");

        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String question;
                while ((question = in.readLine()) != null) {
                    question = question.toLowerCase();
                    String reply = faq.getOrDefault(question, "Sorry, I don't understand that.");
                    out.println(reply);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
