package com.lordjoe.reader;

import net.sourceforge.tess4j.*;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
/**
 * com.lordjoe.reader.TesseractHolder
 * User: Steve
 * Date: 3/3/24
 */
public class TesseractHolder {

    private static TesseractHolder INSTANCE = new TesseractHolder();

    public static  TesseractHolder getInstance() {
        return INSTANCE;
    }
    public static final TesseractHolder[] EMPTY_ARRAY = {};

    private   Tesseract tesseract;
    private TesseractHolder() {
        tesseract = new Tesseract();
        // Set PSM to recognize a single line
        tesseract.setPageSegMode( 7);
        tesseract.setTessVariable("tessedit_char_whitelist", "0123456789-.");
        tesseract.setTessVariable("tessedit_pageseg_mode", "7");


        tesseract.setTessVariable("user_defined_dpi", "270");
      //tesseract.setOcrEngineMode();
 //       tesseract.setLanguage("eng");
  //      tesseract.setOcrEngineMode(1);
        // pull libraries out of the jar and add to path
        File tmpFolder =  LoadLibs.extractTessResources("win32-x86-64");
        System.setProperty("java.library.path", tmpFolder.getPath());
    }

    public String readImage(File f) {
        try {
                return tesseract.doOCR(f).trim();
        } catch (TesseractException e) {
            System.out.println(e.getMessage());
         //   throw new RuntimeException(e);
            return("bad parse");
        }
    }

    public String readImage(File f, Rectangle r) {
        try {
            return tesseract.doOCR(f,r).trim();
        } catch (TesseractException e) {
            throw new RuntimeException(e);

        }
    }

    public String readImage(BufferedImage f) {
        try {
            return tesseract.doOCR(f);
        } catch (TesseractException e) {
            throw new RuntimeException(e);

        }
     }

    public String readImage(BufferedImage f, Rectangle r) {
        try {
            return tesseract.doOCR(f,r);
        } catch (TesseractException e) {
            throw new RuntimeException(e);

        }
    }

}
