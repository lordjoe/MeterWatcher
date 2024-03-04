package com.lordjoe.reader;

/**
 * com.lordjoe.reader.WebCamPanel
 * User: Steve
 * Date: 3/3/24
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class WebCamPanel extends JPanel {
    private Point startPoint = null;
    private Rectangle currentRect = null;

    private final ImageSource cm;
    private BufferedImage image;

    public WebCamPanel(ImageSource cm) {
        this.cm = cm;
          Timer timer = new Timer(200, e -> updateImage());
        timer.start();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                startPoint = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentRect = new Rectangle(startPoint.x, startPoint.y, Math.abs(e.getX() - startPoint.x), Math.abs(e.getY() - startPoint.y));
                String name = Main.getSourceName();
                BufferedImage bufferedImage = cm.getBufferedImage();
                int imageWidth = bufferedImage.getWidth();
                int imageHeight = bufferedImage.getHeight();
                double widthRatio = (double)imageWidth /  getPanelWidth();
                double heightRatio = (double)imageHeight /  getPanelHeight();
                Rectangle imageRect = new Rectangle(
                        (int)(currentRect.x * widthRatio),
                        (int)(currentRect.y * heightRatio),
                        (int)(currentRect.width * widthRatio),
                        (int)(currentRect.height * heightRatio)
                        );


                 if(currentRect.height > 10 && currentRect.width > 10)   {
                      Component me = e.getComponent();
                    while(me.getParent() != null)
                        me = me.getParent();
                    LabelMakerDialog dlg = new LabelMakerDialog((Frame)me,cm,imageRect );
                    dlg.setVisible(true);
                }
                e.getComponent().repaint();
            }
        });

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentRect = new Rectangle(startPoint.x, startPoint.y, Math.abs(e.getX() - startPoint.x), Math.abs(e.getY() - startPoint.y));
                e.getComponent().repaint();
            }
        });

      }

    public int getPanelWidth() {
        return this.getWidth();
    }
    public int getPanelHeight() {
        return this.getHeight();
    }

    private void updateImage() {
        image = cm.getBufferedImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        if (currentRect != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.draw(currentRect);
        }
    }

    public static void camera_main(String[] args) {
        JFrame frame = new JFrame("Webcam Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CameraManager cm = new CameraManager();
        frame.add(new WebCamPanel(cm));
        frame.setSize(640, 480);
        frame.setVisible(true);
    }
}
