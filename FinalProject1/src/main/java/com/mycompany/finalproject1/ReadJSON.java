package com.mycompany.finalproject1;


//import gson library in order to use functions to read the json file

import com.google.gson.*;
//import all java io and utilities libraries
import java.io.*;
import java.util.*;

public class ReadJSON {
    //Initialize gson library
    private String fileType;
    private String name;
    private boolean hasEntry;
    private String path;
    private String repoId;
    private int entryId;
    private String nameFilter;
    private String nameValue;
    private String length;
    private String operator;
    private String opValue;
    private long lenValue;
    private String contentFilter;
    private String contentValue;
    private String countFilter;
    private String min;
    private String keyValue;
    private int minValue;
    private String split;
    private int linesValue;
    private String rename;
    private String max;
    private int maxValue;
    private String suffix;
    private String elemType;

    public void readJson(String filePath) {
        Gson gson = new Gson();
        try {
            // Read the JSON file into a JsonObject using buffered reader
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            JsonObject jsonObject = JsonParser.parseReader(br).getAsJsonObject();

            // Get the values from the JsonObject, name is a string, processing elements is an array
            this.name = jsonObject.get("name").getAsString();
            JsonArray processingElements = jsonObject.getAsJsonArray("processing_elements");


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
                this.elemType = processingElement.get("type").getAsString();

                /*
                get the input entries, type can only be 2 options
                        local:
                            path is a string, get path as a string
                        remte:
                            repositoryId is a string, get as string
                            entryId is an int, get as int
                 */
                JsonArray inputEntries = processingElement.getAsJsonArray("input_entries");
                //check to see if there is an empty input entry.
                //      If empty, the previous entries that were printed should be used for the following filter

                //create a boolean value which refers to having file path or not

                if (inputEntries.isEmpty()) {
                    this.hasEntry = false;
                    //FIND WAY TO USE PREVIOUS OUTPUT
                }
                JsonArray parameters = processingElement.getAsJsonArray("parameters");


                if (inputEntries.size() > 0) {
                    //use iterator to iterate through elements within the array
                    Iterator<JsonElement> iterate = inputEntries.iterator();
                    //run a while loop that runs until there are no more values to iterate
                    while (iterate.hasNext()) {
                        //create a JSON object that iterates the next element of the array
                        JsonObject inputEntry = iterate.next().getAsJsonObject();

                        //create a string type to read the type (local or remote)
                        this.fileType = inputEntry.get("type").getAsString();

                        //run a switch case method to read for local or remote type

                        switch (fileType) {
                            case "local":
                                //get the path, string value
                                this.path = inputEntry.get("path").getAsString();
                                this.hasEntry = true;
                                //call local file object read file method enter path as parameter
                                break;
                            case "remte":
                                //get repositoryId String and entryId int
                                this.repoId = inputEntry.get("repositoryId").getAsString();
                                this.entryId = inputEntry.get("entryId").getAsInt();
                                this.hasEntry = true;
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
                    for (JsonElement jsonElement : parameters) {

                        JsonObject parameter = jsonElement.getAsJsonObject();
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


                        switch (elemType) {
                            case "NameFilter":
                                this.nameFilter = parameter.get("name").getAsString();
                                this.nameValue = parameter.get("value").getAsString();

                                /*
                                    use variables in name filter class in order to run the filter
                                 */
                                break;
                            case "LengthFilter":

                                /*
                                if the string value for the name of the filter is not the filter name (length in this situation),
                                  the program will assume it is reading the operator value and will store that input within the operator variable.
                                Otherwise, it will store it in length variable
                                */

                                if (Objects.equals(parameter.get("name").getAsString(), "Length")) {
                                    this.length = parameter.get("name").getAsString();
                                } else {
                                    this.operator = parameter.get("name").getAsString();
                                }

                                //read value as a string and then use conditions to differentiate between strings and ints
                                this.opValue = parameter.get("value").getAsString();

                                //if the value is an integer, it is assumed to be the length else it is the operator variable
                                //integer value is found by using regex values for integers
                                if (opValue.matches("\\d+")) {
                                    //store the value as an integer since it is refering to length value
                                    this.lenValue = Integer.parseInt(opValue);
                                }
                               /*
                                 Take values and store into length class
                                    fill parameters with the initialized variables
                                */
                                break;
                            case "ContentFilter":
                                this.contentFilter = parameter.get("name").getAsString();
                                this.contentValue = parameter.get("value").getAsString();
                                break;
                            case "CountFilter":

                                /*
                                if the string value for the name of the filter is not the filter name (length in this situation),
                                  the program will assume it is reading the key value and will store that input within the contentKey variable.
                                Otherwise, it will store it in min variable
                                */

                                if (Objects.equals(parameter.get("name").getAsString(), "Key")) {
                                    this.countFilter = parameter.get("name").getAsString();
                                } else {
                                    this.min = parameter.get("name").getAsString();
                                }

                                //read value as a string and then use conditions to differentiate between strings and ints
                                this.keyValue = parameter.get("value").getAsString();

                                //if the value is an integer, it is assumed to be the length else it is the operator variable
                                //integer value is found by using regex values for integers
                                if (keyValue.matches("\\d+")) {
                                    //store the value as an integer since it is refering to length value
                                    this.minValue = Integer.parseInt(keyValue);

                                    //need to make sure min value is greater than 0
                                    if (minValue < 0) {
                                        throw new Exception("Min value has to be greater than 0");
                                    }
                                }

                               /*
                                 Take values and store into count class
                                    fill parameters with the initialized variables
                                */

                                break;
                            case "Split":
                                this.split = parameter.get("name").getAsString();
                                this.linesValue = parameter.get("value").getAsInt();

                                //check line value to make sure it is greater than 0
                                if (linesValue < 0) {
                                    throw new Exception("Line value has to be greater than 0");
                                }

                                /*
                                  Take values and store into split class
                                    fill parameters with initialized variables
                                 */
                                break;
                            case "List":
                                this.max = parameter.get("name").getAsString();
                                this.maxValue = parameter.get("value").getAsInt();
                            case "Rename":
                                this.rename = parameter.get("name").getAsString();
                                this.suffix = parameter.get("value").getAsString();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get methods for all variables
    public String getElemType(){
        return elemType;
    }

    public String getName() {
        return name;
    }

    public boolean isHasEntry() {
        return hasEntry;
    }

    public int getEntryId() {
        return entryId;
    }

    public long getLenValue() {
        return lenValue;
    }

    public String getFileType() {
        return fileType;
    }

    public String getContentFilter() {
        return contentFilter;
    }

    public String getLength() {
        return length;
    }

    public String getCountFilter() {
        return countFilter;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public String getNameValue() {
        return nameValue;
    }

    public String getOperator() {
        return operator;
    }

    public String getPath() {
        return path;
    }

    public String getRepoId() {
        return repoId;
    }

    public int getMinValue() {
        return minValue;
    }

    public int getLinesValue() {
        return linesValue;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public String getRename() {
        return rename;
    }

    public String getMin() {
        return min;
    }

    public String getOpValue() {
        return opValue;
    }

    public String getSplit() {
        return split;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public String getMax() {
        return max;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getContentValue() {
        return contentValue;
    }

}