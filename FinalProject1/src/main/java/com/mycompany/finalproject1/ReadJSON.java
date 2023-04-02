package com.mycompany.finalproject1;


//import gson library in order to use functions to read the json file

import com.google.gson.*;
//import all java io and utilities libraries
import java.io.*;
import java.util.*;

public class ReadJSON {
    public static void main(String[] args) {
        //Initialize gson library
        Gson gson = new Gson();

        try {
            // Read the JSON file into a JsonObject using buffered reader
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\riyan\\Documents\\projectFiles\\Test_Scenario.json"));
            JsonObject jsonObject = JsonParser.parseReader(br).getAsJsonObject();

            // Get the values from the JsonObject, name is a string, processing elements is an array
            String name = jsonObject.get("name").getAsString();
            JsonArray processingElements = jsonObject.getAsJsonArray("processing_elements");

            // Print the values
            System.out.println("Name: " + name);

            // Iterate over the processing_elements array
            for (JsonElement element : processingElements) {
                JsonObject processingElement = element.getAsJsonObject();
                /*
                get the element type, element type can be:
                        name
                        length
                        content
                        count
                        split
                        list
                        rename
                        print
                    if none of the following values is found, the program will display an error "invalid elemen requested, please modify the file and try again"
                 */
                String type = processingElement.get("type").getAsString();

                /*
                get the input entries, type can only be 2 options
                        local:
                            path is a string, get path as a string
                        remte:
                            repositoryId is a string, get as string
                            entryId is an int, get as int
                 */
                JsonArray inputEntries = processingElement.getAsJsonArray("input_entries");
                JsonArray parameters = processingElement.getAsJsonArray("parameters");

                System.out.println("Processing Element: " + type);

                // Print the input_entries array
                if (inputEntries.size() > 0) {
                    System.out.println("Input Entries:");

                    //use iterator to iterate through elements within the array
                    Iterator<JsonElement> iterate = inputEntries.iterator();
                    //run a while loop that runs until there are no more values to iterate
                    while (iterate.hasNext()) {
                        //create a JSON object that iterates the next element of the array
                        JsonObject inputEntry = iterate.next().getAsJsonObject();

                        //create a string type to read the type (local or remote)
                        String fileType = inputEntry.get("type").getAsString();

                        //run a switch case method to read for local or remote type

                        switch (fileType){
                            case "local":
                                //get the path, string value
                                String path = inputEntry.get("path").getAsString();
                                //call local file object read file method enter path as parameter
                                break;
                            case "remte":
                                //get repositoryId String and entryId int
                                String repoId = inputEntry.get("repositoryId").getAsString();
                                int entryId = inputEntry.get("entryId").getAsInt();
                                //call remote file object methods fill in parameters with above variables
                                break;
                            default:
                                //error value is incorrect
                                throw new Exception("Invalid file read location, please modify file and try again. Files can only be read from local drive or remote (remte) drive");
                        }

                    }
                }

                // Print the parameters array
                if (parameters.size() > 0) {
                    System.out.println("Parameters:");
                    Iterator<JsonElement> it = parameters.iterator();
                    while (it.hasNext()) {

                        /*
                        use switch case methods based on the processing element
                            name: has one parameter name (string) and one parameter value (string)
                            length: two parameter names (strings), 2 parameter values (long & string)
                            content: one parameter name (string), one parameter value (string)
                            count: 2 parameter names, 2 parameter values (string & int)
                            split: one parameter name, one parameter value (int)
                            rename: one parameter name, one parameter value (string)
                            print: N/A
                         */
                        JsonObject parameter = it.next().getAsJsonObject();
                        String paramName = parameter.get("name").getAsString();
                        String paramValue = parameter.get("value").getAsString();
                        System.out.println("Name: " + paramName + ", Value: " + paramValue);
                    }
                }

                System.out.println("--------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
