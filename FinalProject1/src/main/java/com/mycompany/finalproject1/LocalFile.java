package com.mycompany.finalproject1;

import java.util.*;
import java.io.*;

public abstract class LocalFile {
    private String path;

    //initialize file path

    public void LocalEntry(String path) {
        this.path = path;
    }

    //get file path
    public String getPath() {
        return path;
    }
    //boolean to see if path is a file

    public boolean isFile() {
        File file = new File(path);
        return file.isFile();
    }


    //boolean to see if path is a directory
    public boolean isDirectory() {
        File file = new File(path);
        return file.isDirectory();
    }

    //reads lines within file
    public List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        return lines;
    }

    //reads all content within file
    public String readContent() throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line).append("\n");
        }
        reader.close();
        return content.toString();
    }


    //counts number of files within a directory
    public void fileCount() throws Exception {
        //path for directory
        File directory = new File(path);

        //counter variable
        int counter = 0;

        //convert directory to a list of entries (use require non null to make sure directory has content within it)
        for (File file: Objects.requireNonNull(directory.listFiles())){
            //if the element is a file or directory increase counter by one
            if (file.isFile() || file.isDirectory()){
                counter++;
            }
        }
    }


    /*
           Override file counter method in order to print max number of file entries
              Run the first for loop to count the number of files then run another one to print max number of files
        */
}