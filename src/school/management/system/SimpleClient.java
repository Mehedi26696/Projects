package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class SimpleClient extends JFrame {
    private JComboBox<String> questionDropdown;
    private JTextArea chatArea;
    private JButton sendButton;
    private JButton refreshButton;
    private PrintWriter out;

    public SimpleClient() {
        setTitle("Dhaka High School Chatbot: ");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Set background color for the entire frame
        getContentPane().setBackground(new Color(0x282828)); // Dark gray background

        // Chat area with user-defined styles
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Raleway", Font.PLAIN, 18));
        chatArea.setBackground(new Color(0x282828)); // Match frame background
        chatArea.setForeground(new Color(0xDCDCF0)); // Light font color
        chatArea.setMargin(new Insets(10, 15, 10, 10)); // Inner margin for the chat area
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Predefined questions
        String[] questions = {
            "What are the school timings?",
            "How can I contact the principal?",
            "What are the upcoming events?",
            "Can I get the admission form?",
            "What is the school fee structure?",
            "Where can I find the school calendar?",
            "What are the rules for student attendance?",
            "How do I report an issue?",
            "What extracurricular activities are offered?",
            "What is the school's history?"
        };

        // Dropdown for selecting questions
        questionDropdown = new JComboBox<>(questions);
        questionDropdown.setFont(new Font("Raleway", Font.PLAIN, 14));
        questionDropdown.setPreferredSize(new Dimension(300, 30));

        // Send button
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Raleway", Font.BOLD, 16));
        sendButton.setBackground(new Color(0x4CAF50)); // Green color
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Refresh button
        refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Raleway", Font.BOLD, 16));
        refreshButton.setBackground(new Color(0xFF5722)); // Orange color
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Button hover effects
        sendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(new Color(0x388E3C)); // Darker green
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                sendButton.setBackground(new Color(0x4CAF50)); // Original green
            }
        });

        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0xE64A19)); // Darker orange
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                refreshButton.setBackground(new Color(0xFF5722)); // Original orange
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
        refreshButton.addActionListener(new ActionListener() {
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
        buttonPanel.add(refreshButton);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleClient client = new SimpleClient();
            client.setVisible(true);
        });
    }
}
