package com.lordjoe.reader;

import com.github.sarxos.webcam.*;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CameraManager implements WebcamListener,ImageSource {
    public static void guaranteeOpen(Webcam cam) {
        if(!cam.open())
            cam.open();
    }

   // private final WebcamDevice webcamDEV;
    private final Webcam  webcam;

    private boolean open;
    private BufferedImage img;
    private ImageIcon icon;

    private final Set<NewImageListener> listeners = new HashSet<NewImageListener>();

    public CameraManager() {
        WebcamDriver driver = Webcam.getDriver();
        webcam = Webcam.getDefault();
        List<WebcamDevice> devices = driver.getDevices();

     //   webcamDEV = devices.get(0);
        webcam.open();
        ImageIcon image =  getImage();
        webcam.addWebcamListener(this);

        

    }

    public Webcam getWebCam() {
        return webcam;
    }

    public void addNewImageListener(NewImageListener added) {
        listeners.add(added);
    }

    public void removeNewImageListener(NewImageListener added) {
        listeners.remove(added);
    }

    public ImageIcon getImage() {
           BufferedImage newImg = webcam.getImage();
        if(newImg != null)    {
            img = newImg;
            icon = new ImageIcon(img);
            return icon;
        }
        return null;
    }

    public BufferedImage getBufferedImage() {
        BufferedImage newImg = webcam.getImage();
        if(newImg != null)    {
            img = newImg;
            icon = new ImageIcon(img);
            return img;
        }

        return img;
    }

    @Override
    public void webcamOpen(WebcamEvent webcamEvent) {
        img = webcamEvent.getImage();
    }

    @Override
    public void webcamClosed(WebcamEvent webcamEvent) {
        img = null;
    }

    @Override
    public void webcamDisposed(WebcamEvent webcamEvent) {

    }

    @Override
    public void webcamImageObtained(WebcamEvent webcamEvent) {
        img = webcamEvent.getImage();
        icon = new ImageIcon(img);
        for (NewImageListener listener : listeners) {
            listener.onNewImage(icon);
        }
    }
}
