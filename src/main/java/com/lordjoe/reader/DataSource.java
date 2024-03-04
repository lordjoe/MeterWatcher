package com.lordjoe.reader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * com.lordjoe.reader.DataSource
 * User: Steve
 * Date: 3/3/24
 */
public class DataSource {
     private final ImageSource source;
     private final Rectangle rect;
     private final String name;

    public DataSource(  String name,final ImageSource source, final Rectangle rect) {
        this.source = source;
        this.rect = rect;
        this.name = name;
    }

    public ImageSource getSource() {
        return source;
    }

    public Rectangle getRect() {
        return rect;
    }

    public String getName() {
        return name;
    }


    public BufferedImage getSelectedImage()   {
        TesseractHolder th = TesseractHolder.getInstance();
        BufferedImage img =  source.getBufferedImage();
        BufferedImage sub = img.getSubimage(rect.x,rect.y,rect.height,rect.width);
         return sub;
    }

    public String getText() {
        TesseractHolder th = TesseractHolder.getInstance();
        BufferedImage img =  source.getBufferedImage();
        if(img == null)
            return "" ;
        return th.readImage(img,rect).trim();

    }
}
