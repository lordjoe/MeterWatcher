package com.lordjoe.reader;

/**
 * com.lordjoe.reader.DynamicTextImage
 * User: Steve
 * Date: 3/3/24
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class DynamicTextImage implements ImageSource, ActionListener {


    private BufferedImage current;

    public DynamicTextImage() {
        updateImage();
        Timer timer = new Timer(5000, this);
        timer.start();
    }

    public BufferedImage getCurrentImage() {
        return current;
    }

    @Override
    public BufferedImage getBufferedImage() {
        return getCurrentImage();
    }

    public void updateImage() {
        // Create a buffered image
        int size = 40;
        int width = 12 * size;
        int height = 6 * size;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color and font
        Color bkg = new Color(225, 222, 183);
        g2d.setColor(bkg);
        g2d.fillRect(0, 0, width, height);
        Color fg = new Color(128, 128, 128);
        g2d.setColor(fg);
        g2d.setFont(new Font("Arial", Font.BOLD, size));

        // Generate random numbers for voltage, temperature, and speed
        Random random = new Random();
        int voltage = random.nextInt(100);
        int temperature = random.nextInt(35);
        int speed = random.nextInt(200);

        // Write text to the image
        g2d.drawString("Voltage: " + voltage, size / 2, size + 20);
        g2d.drawString("Temperature: " + temperature, size / 2, 2 * size + 20);
        g2d.drawString("Speed: " + speed, size / 2, 3 * size + 20);

        // Disposing graphics to commit them
        g2d.dispose();

        current = image;


    }

    public static void dynamic_main(String[] args) {
        JFrame frame = new JFrame("Text Source");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DynamicTextImage cm = new DynamicTextImage();
        frame.add(new WebCamPanel(cm));
        frame.setSize(640, 480);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateImage();

    }
}

