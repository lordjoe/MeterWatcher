package com.lordjoe.reader;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * com.lordjoe.reader.DragRectPanel
 * User: Steve
 * Date: 3/2/24
 */
public class DragRectPanel  extends JPanel implements NewImageListener  {
     private boolean drawing = false; // Flag to indicate drawing in progress
    private Point startPoint;
    private Rectangle selectionRect;
 



    private CameraManager manager;
    private JLabel label;

    private final GlassPane gp;
    public DragRectPanel(CameraManager cm,Rectangle[] rect) {

        manager = cm;
        setLayout(new BorderLayout());

        ImageIcon im = cm.getImage();
         label = new JLabel(im);
        this.add(label,BorderLayout.CENTER);

        gp = new GlassPane();
        gp.setVisible(true);

        
           MouseAdapter adapter = new MouseAdapter() {
            private Point startPoint = new Point(0,0);

            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                drawing = true;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = Math.min(startPoint.x, e.getX());
                int y = Math.min(startPoint.y, e.getY());
                int width = Math.abs(e.getX() - startPoint.x);
                int height = Math.abs(e.getY() - startPoint.y);
                setRectangle(x, y, width, height);
                System.out.println("x.y,width,height " + x  + y + " " + width + " " + height);
                repaint(); // Repaint the panel to update the rectangle as it is being dragged
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                drawing = false;
                repaint(); // Final repaint to ensure the last position of rectangle is shown
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);

     }




    private void setRectangle(int x, int y, int width, int height) {
        selectionRect.x = x;
        selectionRect.y = y;
        selectionRect.width = width;
        selectionRect.height = height;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (drawing) {
            g.setColor(Color.BLUE);
            g.drawRect(selectionRect.x, selectionRect.y, selectionRect.width, selectionRect.height); // Draw the rectangle
        }
    }


    @Override
    public void onNewImage(ImageIcon icon) {
        label.setIcon(icon);
    }

    private class GlassPane extends JComponent {
        public GlassPane() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (selectionRect != null) {
                g.setColor(Color.BLUE);
                ((Graphics2D) g).draw(selectionRect);
            }
        }
    }
}
