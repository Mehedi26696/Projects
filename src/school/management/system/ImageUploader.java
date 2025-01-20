package school.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;

public class ImageUploader {
    private JFrame frame;
    private JLabel imageLabel;
    private JButton uploadButton, saveButton;
    private File selectedFile;

    public ImageUploader() {
        // Initialize GUI components
        frame = new JFrame("Image Upload");
        imageLabel = new JLabel("No Image Selected", SwingConstants.CENTER);
        uploadButton = new JButton("Choose Image");
        saveButton = new JButton("Save to Database");

        // Configure Layout
        frame.setLayout(new BorderLayout());
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(uploadButton, BorderLayout.NORTH);
        frame.add(saveButton, BorderLayout.SOUTH);

        // Add Action Listeners
        uploadButton.addActionListener(new UploadAction());
        saveButton.addActionListener(new SaveAction());

        // Finalize Frame
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private class UploadAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                imageLabel.setText(selectedFile.getName());
                imageLabel.setIcon(new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath())
                        .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
            }
        }
    }

    private class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedFile != null) {
                saveImageToDatabase(selectedFile);
            } else {
                JOptionPane.showMessageDialog(frame, "No image selected!");
            }
        }
    }

    private void saveImageToDatabase(File file) {
        try {
            Connect connect = new Connect(); // Use the Connect class
            String query = "INSERT INTO images (name, image) VALUES (?, ?)";
            PreparedStatement statement = connect.c.prepareStatement(query);

            statement.setString(1, file.getName());
            FileInputStream fis = new FileInputStream(file);
            statement.setBinaryStream(2, fis, (int) file.length());
            statement.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Image saved successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error saving image: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ImageUploader();
    }
}
