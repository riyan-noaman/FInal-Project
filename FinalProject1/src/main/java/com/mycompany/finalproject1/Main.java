package com.mycompany.finalproject1;

import java.io.IOException;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {

        //"C:\\Users\\riyan\\Documents\\projectFiles\\Test_Scenario.json"

        ReadJSON json = new ReadJSON();

        //read json file
        json.readJson("C:\\Users\\riyan\\Documents\\projectFiles\\test_scenario.json");

        //System.out.println(json.getElements());

        for (int i = 0; i < json.elements.size(); i++) {

            String element = json.elements.get(i);
            switch (element) {
                case "NameFilter":
                /*
                   use variables in name filter class in order to run the filter
                */
                    break;
                case "LengthFilter":
                /*
                   Take values and store into length class
                     fill parameters with the initialized variables
                */
                    break;
                case "ContentFilter":
                    break;
                case "CountFilter":

                /*
                  Take values and store into count class
                      fill parameters with the initialized variables
                */

                    break;
                case "Split":

                /*
                  Take values and store into split class
                      fill parameters with initialized variables
                */
                    break;
                case "List":
                    //create list object, fill in parameters with information from json file

                    //if the process type is local do the following
                    if (json.isLocal()) {
                        ListElement list = new ListElement();

                        list.setMaxValue(json.getMaxValue());
                        list.setPath(json.getPath());
                        list.fileCount();
                    }

                case "Rename":

                /*
                  Take values and store into rename class
                      fill parameters and initialized variables
                */
                    break;
                case "Print":
                /*
                  Call print class
                */

                    break;
            }
        }

    }
}

