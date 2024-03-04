package com.lordjoe.reader;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * com.lordjoe.reader.WebCaptureWithName
 * User: Steve
 * Date: 3/2/24
 */
public class WebCaptureWithName {
    Rectangle[] rectholder;
      String name;
    public   void WebcamCaptureWithName(CameraManager cm ,String[] nameHolder, Rectangle[] rectholder) {
        this.rectholder = rectholder;
         initUI(cm);
         while(name == null) {
             try {
                 Thread.sleep(50);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         }

    }

      private void initUI(CameraManager cm ) {
        Rectangle selectedRectangle = null;
          JFrame frame = new JFrame("Webcam Capture - Select Area");
          frame.setLayout(new BorderLayout());
  //      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setSize(640, 480);
           
         DragRectPanel dragRect = new DragRectPanel(cm,rectholder );
          frame.add(dragRect,BorderLayout.NORTH);
          frame.add(inputPanel(),BorderLayout.SOUTH);


          frame.setVisible(true);
      //    frame.pack();
      //    frame.setLocationRelativeTo(null); // Center the frame


      }

    public JPanel inputPanel() {

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create panel for text fields and buttons
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2, 5, 5)); // 3 rows, 2 columns, gaps of 5px

        // Create labels
        JLabel cameraLabel = new JLabel("Camera Name:");
        JLabel rectangleLabel = new JLabel("Rectangle Name:");

        // Create text fields
        JTextField cameraTextField = new JTextField();

        JTextField rectangleTextField = new JTextField();

        // Create buttons
        JButton newRectangleButton = new JButton("New Rectangle");

        JButton saveButton = new JButton("Save");

        // Add components to the input panel
        inputPanel.add(cameraLabel);
        inputPanel.add(cameraTextField);
        inputPanel.add(rectangleLabel);
        inputPanel.add(rectangleTextField);
        inputPanel.add(newRectangleButton);
        inputPanel.add(saveButton);

        // Add input panel to the main panel
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // ActionListener for buttons
        newRectangleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add action for New Rectangle button
                // You can implement what happens when this button is clicked
                System.out.println("New Rectangle button clicked");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add action for Save button
                // You can implement what happens when this button is clicked
                System.out.println("Save button clicked");
            }
        });

        // Add main panel to the frame
        return mainPanel;

     }


}
