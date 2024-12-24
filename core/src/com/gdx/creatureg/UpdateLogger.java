package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class UpdateLogger {
    private static final String[] keywords = new String[]{"Added", "Removed", "Updated"};
    private static String[][] arrayArray;
    private static String[] added, removed, updated, oldArray;
    private static String enteredInfo = "";
    private static File updateFile;
    private static FileWriter updateFileWriter;
    private static Scanner updateFilerScanner;
    private static int requestNo = 0;
    private static boolean dataRequested = false;

    UpdateLogger(boolean isDebug){
        if (isDebug) {
            added = new String[]{""};
            removed = new String[]{""};
            updated = new String[]{""};
            arrayArray = new String[][]{added, removed, updated};
            if (UserInput.getUserInputBoolean("Would you like to create an update log?")) {
                requestUpdateInfo();
                dataRequested = true;
            }
            else{
                dataRequested = false;
            }
        }
    }

    private static void temp(String[] wow){
        for (int i = 0; i < wow.length; i++){
            System.out.println(wow[i]);
        }
    }

    private static void requestUpdateInfo(){
        for (int i = 0; i < arrayArray.length; i++){
            while (true){
                oldArray = arrayArray[i]; //Save the previously entered update info
                enteredInfo = UserInput.getUserInput("What was " + keywords[i] + " in this update? (Enter 'End' or 'E' to move on)", true);
                if (enteredInfo.equalsIgnoreCase("END") || enteredInfo.equalsIgnoreCase("E")){
                    break; //If the user enters E or End, move to the next bit of update info. (E.G: Added -> Removed -> Updated)
                }
                else{
                    if (arrayArray[i].length == 1 && arrayArray[i][0].isEmpty()) { //If the length of the current update info is 1 and the first item is an empty string...
                        arrayArray[i][0] = enteredInfo; //Put the entered info in the place of the empty string.
                    }
                    else { //Else...
                        arrayArray[i] = new String[arrayArray[i].length + 1]; //Make the current array equal to a new array that is one item bigger than it is now.
                        for (int x = 0; x < oldArray.length; x++){
                            arrayArray[i][x] = oldArray[x]; //Put the update info back into the current array using the data saved to the oldArray.
                        }
                        arrayArray[i][arrayArray[i].length - 1] = enteredInfo; //Put the entered update info at the end of the array.
                    }
                    temp(arrayArray[i]);
                }
            }
        }
    }

    public static boolean createUpdateLog(String versionNo){
        if (dataRequested) {
            updateFile = Gdx.files.internal("updates.txt").file();
            try {
                if (!updateFile.exists()) {
                    if (!updateFile.createNewFile()) {
                        throw new Exception("Error: Unable to create updates.txt in assets directory (Is the directory read-only?).");
                    }
                }
                if (!updateFile.canWrite()) {
                    throw new Exception("Error: updates.txt cannot be written to (Is the directory read-only?).");
                }
                updateFileWriter = new FileWriter(updateFile, true);
                updateFileWriter.write("\nVersion " + versionNo + ":\n");
                for (int i = 0; i < arrayArray.length; i++) {
                    if (!arrayArray[i][0].isEmpty()) {
                        updateFileWriter.write("\t" + keywords[i] + ": \n");
                        for (int x = 0; x < arrayArray[i].length; x++) {
                            updateFileWriter.write("\t\t-" + arrayArray[i][x] + "\n");
                        }
                    }
                }
                updateFileWriter.close();
                System.out.println("Update log successfully created for version " + BuildHandler.getVersionNoString());
                return true;
            } catch (Exception error) {
                System.out.println("An error occurred when trying to update updates.txt");
                error.printStackTrace();
                return false;
            }
        }
        else{
            System.out.println("Update log not created for version " + BuildHandler.getVersionNoString());
            return false;
        }
    }
}
