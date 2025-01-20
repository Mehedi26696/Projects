package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame {
    private JComboBox<String> questionDropdown;
    private JTextArea chatArea;
    private JButton sendButton;
    private JButton refreshlButton;
    private PrintWriter out;

    public Client() {
        setTitle("Dhaka High School Chatbot: ");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Set background color for the entire frame to gray
        getContentPane().setBackground(new Color(0xB0BEC5)); // Gray background

        // Chat area with white text and gray background
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Raleway", Font.PLAIN, 14));
        chatArea.setBackground(new Color(0x424242)); // Dark gray background for chat area
        chatArea.setForeground(Color.WHITE); // White text color for the chat area
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Predefined questions
        String[] questions = {
            "hello",
            "how are you",
            "bye",
            "what is your name",
            "tell me a joke",
            "who build that website and why?"
        };

        // Dropdown for selecting questions, with increased width
        questionDropdown = new JComboBox<>(questions);
        questionDropdown.setFont(new Font("Raleway", Font.PLAIN, 14));
        questionDropdown.setPreferredSize(new Dimension(250, 30)); // Increased width

        // Send button with styling
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Raleway", Font.BOLD, 14));
        sendButton.setBackground(new Color(0x4CAF50)); // Green color
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createLineBorder(new Color(0x388E3C), 2));
        sendButton.setPreferredSize(new Dimension(120, 40)); // Increase button width
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Cancel button with styling
        refreshlButton = new JButton("Refresh");
        refreshlButton.setFont(new Font("Raleway", Font.BOLD, 14));
        refreshlButton.setBackground(new Color(0x4CAF50)); // green color
        refreshlButton.setForeground(Color.WHITE);
        refreshlButton.setFocusPainted(false);
        refreshlButton.setBorder(BorderFactory.createLineBorder(new Color(0xE64A19), 2));
        refreshlButton.setPreferredSize(new Dimension(120, 40)); // Same width as send button
        refreshlButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button hover effects
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(new Color(0x388E3C)); // Darker green
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(new Color(0x4CAF50)); // Original green
            }
        });

        refreshlButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshlButton.setBackground(new Color(0xE64A19)); // Darker orange
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshlButton.setBackground(new Color(0xFF5722)); // Original orange
            }
        });

        // Send button action
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedQuestion = (String) questionDropdown.getSelectedItem();
                chatArea.append("\uD83D\uDC64 You: " + selectedQuestion + "\n"); // Add user icon before the message
                out.println(selectedQuestion);
                questionDropdown.setSelectedIndex(0); // Reset dropdown
            }
        });

        // Refresh button action
        refreshlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetInterface(); // Call a method to reset the interface
            }
        });

        // Layout setup
        setLayout(new BorderLayout(10, 10)); // Add spacing between components
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridBagLayout());
        bottomPanel.setBackground(new Color(0x282828)); // Match frame background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Spacing between components

        gbc.gridx = 0;
        gbc.gridy = 0;
        bottomPanel.add(questionDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(0x282828));
        buttonPanel.add(sendButton);
        buttonPanel.add(refreshlButton);
        bottomPanel.add(buttonPanel, gbc);

        add(bottomPanel, BorderLayout.SOUTH);

        try {
            Socket socket = new Socket("localhost", 7777);
            out = new PrintWriter(socket.getOutputStream(), true);
            new Thread(new ServerListener(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetInterface() {
        questionDropdown.setSelectedIndex(0);
        chatArea.setText("");
    }

    private class ServerListener implements Runnable {
        private BufferedReader in;

        public ServerListener(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                String response;
                while ((response = in.readLine()) != null) {
                    chatArea.append("\uD83D\uDCBB Server: " + response + "\n"); // Add server icon before the message
                    chatArea.setCaretPosition(chatArea.getDocument().getLength()); // Auto-scroll to the bottom
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void startClient() {
        SwingUtilities.invokeLater(() -> {
            Client client = new Client();
            client.setVisible(true);
        });
    }
}
