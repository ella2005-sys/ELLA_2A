/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MAIN;
import javax.swing.*;
import java.awt.*;

public class ImageTextExample extends JFrame {
    public ImageTextExample() {
        setTitle("Image with Text");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load image
        ImageIcon background = new ImageIcon("h7 Top Career Paths in the Cleaning Industry.jpg"); // your picture file

        // JLabel for image
        JLabel imageLabel = new JLabel(background);
        imageLabel.setLayout(new BorderLayout()); // allow adding components on top

        // Add text on top
        JLabel textLabel = new JLabel("Home Services Made Easy", JLabel.CENTER);
        textLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 24));
        textLabel.setForeground(Color.WHITE); // make it visible
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textLabel.setVerticalAlignment(SwingConstants.CENTER);

        imageLabel.add(textLabel); // text over image

        add(imageLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImageTextExample().setVisible(true);
        });
    }
}

