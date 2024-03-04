package com.lordjoe.reader;


import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.lordjoe.reader.CameraCapture.captureRectangleAndSave;
import static com.lordjoe.reader.DynamicTextImage.dynamic_main;

/**
 * com.lordjoe.reader.Main
 * User: Steve
 * Date: 3/2/24
 */
public class Main {

  
    public static final Properties watcherProperties = new Properties();

    public static Map<String,DataSource> sources = new HashMap<String,DataSource>();

    private static String[]  sourceNames = { "Voltage", "Temperature", "X", "Y", "Z"};
    public static String getSourceName() {
        return sourceNames[sources.size() % sourceNames.length];
    }

    public static void addSource(DataSource ds) {
        sources.put(ds.getName(),ds);
    }

    public static String getJSON() {
        JSONObject json = new JSONObject();
        for (String s : sources.keySet()) {
            DataSource dataSource = sources.get(s);
            json.put(s, dataSource.getText());
         }

        String jsonString = json.toString();

        return jsonString;
    }




    private static void selection(String[] args) {

        String[] nameHolder  = { "Stopwatch"};
        Rectangle[] rectholder    = { new Rectangle(10,10,100,100) };

        CameraManager cm = new CameraManager();
        WebCaptureWithName wc = new WebCaptureWithName();
        wc.WebcamCaptureWithName(cm,nameHolder,rectholder);

        Rectangle rectangle = rectholder[0];
        String outputFileName = nameHolder[0];

        File f = captureRectangleAndSave(cm, rectangle, outputFileName);
    }

    public static class shower implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String json = getJSON();
            System.out.println(json);
            if(json.length() > 3)
                sendJSON(json);
        }
    }

    public static void sendJSON(String json) {
        try {
            // URL of the REST API endpoint
            String target = watcherProperties.getProperty("server");
            URL url = new URL(target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Set the request method to POST
            conn.setRequestMethod("POST");

            // Enable input and output streams
            conn.setDoOutput(true);

            // Set the request content-type header to application/json
            conn.setRequestProperty("Content-Type", "application/json");

            // Write the JSON data to the request's output stream
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Get the response code to ensure the request was processed
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            conn.disconnect();

        } catch (Exception e) {
            System.out.println("Server Error");
        }
    }

    public static void main(String[] args) throws Exception {

        File props = new File("watcher.properties");
        FileReader rdr = new FileReader(props);
        watcherProperties.load(rdr);
        Timer out = new Timer(Integer.parseInt(watcherProperties.getProperty("refresh")),new shower() ) ;
        out.start();
        dynamic_main(args);
 //         WebCamPanel.camera_main(args);
    //    recognition(args);
     //   selection(args);
    }

}
