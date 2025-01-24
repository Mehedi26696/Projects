package school.management.system;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame;
    private JTextField textField;
    private JTextArea chatArea;

    public ChatClient() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Chat Client");
        textField = new JTextField();
        chatArea = new JTextArea(20, 50);

        chatArea.setEditable(false);
        textField.addActionListener(e -> {
            String message = textField.getText();
            if (!message.isEmpty()) {
                out.println(message);
                textField.setText("");
            }
        });

        frame.getContentPane().add(new JScrollPane(chatArea), BorderLayout.CENTER);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        connectToServer();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Listening for messages from the server
            new Thread(this::listenForMessages).start();
        } catch (IOException e) {
            System.out.println("Error connecting to server: " + e.getMessage());
            chatArea.append("Unable to connect to server.\n");
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                chatArea.append(message + "\n");
            }
        } catch (IOException e) {
            chatArea.append("Connection lost.\n");
            System.out.println("Error reading from server: " + e.getMessage());
        } finally {
            cleanup();
        }
    }

    private void cleanup() {
        try {
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.out.println("Error closing client socket: " + e.getMessage());
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatClient::new);
    }
}
