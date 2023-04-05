package com.mycompany.finalproject1;

import java.io.*;
import java.util.*;

/*
    take a list of entries and integer "max" which has a value greater than 0.
    for every entry that is a directory, open the directory and count the number of files within it
        if the number of files is less than or equal to "max", print all files
        if the number of files is greater than "max", print "max" number of files (order of files does not matter)
    ignore any elements that are not directories
 */

public class ListElement extends LocalFile {

    String path = "C:\\Users\\riyan\\Documents\\projectFiles\\Directory_files";

    //Override file counter method in order to print max number of file entries


    private int max = 0;

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax(){
        return max;
    }
    @Override
    public void fileCount() throws Exception {
        //path for directory
        File directory = new File(getPath());

        if (isDirectory()) {

            //counter variable
            int counter = 0;

            int max = getMax();

            //convert directory to a list of entries (use require non null to make sure directory has content within it)
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                //if the element is a file or directory increase counter by one
                if (file.isFile() || file.isDirectory()) {
                    counter++;
                    if (counter <= max) {
                        System.out.println(file.getName());
                    } else {
                        break;
                    }
                }
            }
            if (counter <= max) {
                System.out.printf("\nAll %d entries accounted for", max);
            } else {
                System.out.printf("\n%d entries printed, %d entry/entries unaccounted for", max, (counter - max));
            }
        } else {
            throw new Exception("Entry must be a directory to run the list element");
        }
    }
}
