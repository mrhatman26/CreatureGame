package com.gdx.creatureg;

import com.badlogic.gdx.Gdx;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class BuildHandler {
    private static File buildFile;
    private static FileWriter buildFileWriter;
    private static Scanner buildFileScanner;
    private static int[] versionNo = new int[]{0, 0, 0, 0};;
    private static String versionNoString;
    private static boolean isMinor, isMajor, isRelease, update;
    BuildHandler(boolean run){
        if (run) {
            isMinor = false;
            isMajor = false;
            isRelease = false;
            askReleaseType();
            versionNoToString();
        }
    }

    private static void askReleaseType(){
        if (UserInput.getUserInputBoolean("Do you want to update the version number?")){
            if (UserInput.getUserInputBoolean("(Release.Major.Minor.build)\nIs this release minor?")){
                isMinor = true;
                return;
            }
            if (UserInput.getUserInputBoolean("(Release.Major.Minor.build)\nIs this release Major?")){
                isMajor = true;
                return;
            }
            if (UserInput.getUserInputBoolean("(Release.Major.Minor.build)\nIs this release a release?")){
                isRelease = true;
                return;
            }
            System.out.println("Version numbers not being update (This does not apply to the build number).");
        }
        else{
            System.out.println("Version numbers not being update (This does not apply to the build number).");
        }
    }

    private static String versionNoToString(){
        versionNoString = versionNo[0] + "." + versionNo[1] + "." + versionNo[2] + "." + versionNo[3];
        return versionNoString;
    }

    public static boolean updateNo(){
        buildFile = Gdx.files.internal("build.txt").file();
        try {
            //Check if file exists.
            if (!buildFile.exists()) {
                if (!buildFile.createNewFile()){
                    throw new Exception("Error: Unable to create build.txt in assets directory (Is the directory read-only?).");
                }
            }
            //Check if file can be written to.
            if (!buildFile.canWrite()){
                throw new Exception("Error: build.txt cannot be written to (Is the directory read-only?).");
            }
            //File is good to go.
            readVersionNo();
            if (isMinor){
                versionNo[3] = -1;
                versionNo[2] += 1;
            }
            if (isMajor){
                versionNo[3] = -1;
                versionNo[2] = 0;
                versionNo[1] += 1;
            }
            if (isRelease){
                versionNo[3] = -1;
                versionNo[2] = 0;
                versionNo[1] = 0;
                versionNo[0] += 1;
            }
            versionNo[3] += 1;
            buildFileWriter = new FileWriter(buildFile);
            buildFileWriter.write(versionNoToString());
            buildFileWriter.close();
            return true;
        }
        catch (Exception error){
            System.out.println("An error occurred when trying to update build.txt");
            error.printStackTrace();
            return false;
        }
    }

    public static String readVersionNo(){
        buildFile = Gdx.files.internal("build.txt").file();
        try {
            //Check if file exists.
            if (!buildFile.exists()) {
                if (!buildFile.createNewFile()){
                    throw new Exception("Error: Unable to create build.txt in assets directory (Is the directory read-only?).");
                }
            }
            //File is good to go.
            buildFileScanner = new Scanner(buildFile);
            String line = "";
            String[] lineSplit = new String[4];
            while (buildFileScanner.hasNextLine()){
                line = buildFileScanner.nextLine();
                if (!line.isEmpty()) {
                    lineSplit = line.split("\\.");
                    for (int i = 0; i < versionNo.length; i++) {
                        versionNo[i] = Integer.parseInt(lineSplit[i]);
                    }
                }
                else{
                    throw new Exception("Error: build.txt is empty.");
                }
            }
            buildFileScanner.close();
            return versionNoToString();
        }
        catch (Exception error){
            System.out.println("An error occurred when trying to read build.txt; returning '0.0.0.0' as the version number.");
            error.printStackTrace();
            return "0.0.0.0";
        }
    }
}
