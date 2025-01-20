package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;

public class ImageRetriever {
    private JFrame frame;
    private JLabel imageLabel;
    private JButton retrieveButton;

    public ImageRetriever() {
        // Initialize GUI components
        frame = new JFrame("Retrieve Image");
        imageLabel = new JLabel("Image will appear here", SwingConstants.CENTER);
        retrieveButton = new JButton("Retrieve Image");

        // Configure Layout
        frame.setLayout(new BorderLayout());
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(retrieveButton, BorderLayout.SOUTH);

        // Add Action Listener
        retrieveButton.addActionListener(e -> retrieveImageFromDatabase(2)); // Example: Fetch image with ID 1

        // Finalize Frame
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void retrieveImageFromDatabase(int imageId) {
        try {
            Connect connect = new Connect(); // Use the Connect class
            String query = "SELECT image FROM images WHERE id = ?";
            PreparedStatement statement = connect.c.prepareStatement(query);
            statement.setInt(1, imageId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("image");
                InputStream is = new ByteArrayInputStream(imageBytes);
                ImageIcon imageIcon = new ImageIcon(new ImageIcon(is.readAllBytes()).getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                imageLabel.setIcon(imageIcon);
                imageLabel.setText(null);
            } else {
                JOptionPane.showMessageDialog(frame, "No image found!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error retrieving image: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ImageRetriever();
    }
}

