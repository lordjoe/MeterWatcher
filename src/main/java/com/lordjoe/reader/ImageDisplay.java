package com.lordjoe.reader;

/**
 * com.lordjoe.reader.ImageDisplay
 * User: Steve
 * Date: 3/3/24
 */

 
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageDisplay extends JPanel {
    private BufferedImage image;

    public ImageDisplay(BufferedImage img) {
        this.image = img;
        // Set a preferred size for the custom panel.
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image on the panel.
        g.drawImage(image, 0, 0, this);
    }

    public static void displayImage( BufferedImage img) {
        // Create a BufferedImage object for demonstration purposes.


        // Create the frame to display the image.
        JFrame frame = new JFrame("Image Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ImageDisplay(img));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
