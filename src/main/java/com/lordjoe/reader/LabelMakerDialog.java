package com.lordjoe.reader;

/**
 * com.lordjoe.reader.LabelMakerDialog
 * User: Steve
 * Date: 3/3/24
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class LabelMakerDialog extends JDialog {
    private JTextField nameTextField;
    private JButton saveButton, cancelButton;

    public LabelMakerDialog(Frame parent,final ImageSource cm,final  Rectangle rect) {
        super(parent, "Labelm Dialog", true);

        // Icon at the top
        BufferedImage bufferedImage = cm.getBufferedImage();

        BufferedImage subimage = bufferedImage.getSubimage(0,rect.y,rect.width + rect.x,rect.height);
        ImageIcon icon = new ImageIcon(subimage); // Replace with your icon path
        JLabel iconLabel = new JLabel(icon);

        // Label and text field
        JPanel namePanel = new JPanel(new FlowLayout());
        namePanel.add(new JLabel("Name:"));
        nameTextField = new JTextField(20);
        nameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                saveButton.setEnabled(nameTextField.getText().length() > 1);

            }

            @Override
            public void keyPressed(KeyEvent e) {
                saveButton.setEnabled(nameTextField.getText().length() > 1);

            }

            @Override
            public void keyReleased(KeyEvent e) {
                saveButton.setEnabled(nameTextField.getText().length() > 1);

            }
 
        });
        namePanel.add(nameTextField);

        // Buttons
        saveButton = new JButton("Save");
        saveButton.setEnabled(nameTextField.getText().length() > 1);
        cancelButton = new JButton("Cancel");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle save action
                DataSource ds = new DataSource(nameTextField.getText(),cm,rect);
                Main.addSource(ds);
                System.out.println("Save button clicked");
                dispose(); // Close dialog
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle cancel action
                 dispose(); // Close dialog
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Adding components to dialog
        getContentPane().add(iconLabel, BorderLayout.NORTH);
        getContentPane().add(namePanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack(); // Adjust dialog to components
        setLocationRelativeTo(parent); // Center on parent window
    }
}

