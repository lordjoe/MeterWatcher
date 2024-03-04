package com.lordjoe.reader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * com.lordjoe.reader.CameraCapture
 * User: Steve
 * Date: 3/2/24
 */
public class CameraCapture {
    public static final CameraCapture[] EMPTY_ARRAY = {};
    public static File captureRectangleAndSave(CameraManager cm, Rectangle area, String baseName) {
        BufferedImage image = cm.getBufferedImage();
        BufferedImage croppedImage = image.getSubimage(area.x, area.y, area.width, area.height);

        try {
            File output = generateUniqieFile(baseName,"png");
            ImageIO.write(croppedImage, "PNG", output);
            return output;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static File generateUniqieFile(String baseName,String extension)  {
        int added = 0;
        while(true) {
            String formattedNumber = String.format("%06d", added);
            String name = baseName + "_" + formattedNumber + "." + extension;
            File ret = new File(name)  ;
              if(!ret.exists() )
                return ret;
            added++;
        }
    }

}
