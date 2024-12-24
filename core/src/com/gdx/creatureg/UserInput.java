package com.gdx.creatureg;
import java.nio.file.Path;
import java.util.Scanner;

public class UserInput {
    static Scanner uInput = new Scanner(System.in);

    public static String getUserInput(String message, boolean checkEmpty){
        if (!message.equals("")) {
            System.out.print(message + ": ");
        }
        else{
            System.out.print("Input: ");
        }
        String input;
        if (!checkEmpty) {
            input = uInput.nextLine();
        }
        else {
            while (true){
                input = uInput.nextLine();
                if (!input.isEmpty()){
                    break;
                }
                else{
                    System.out.println("Input cannot be blank.");
                    pauseForEnterKey();
                }
            }
        }
        return input;
    }

    public static void pauseForEnterKey(){
        System.out.print("Press ENTER to continue");
        uInput.nextLine();
    }

    public static boolean getUserInputBoolean(String message){
        String input = "";
        Boolean returnVal = false;
        while (true) {
            input = getUserInput(message, true).toUpperCase();
            if (input.equals("Y") || input.equals("YES")){
                returnVal = true;
                break;
            }
            else if (input.equals("N") || input.equals("NO")){
                break;
            }
            else{
                System.out.println("\nPlease enter Y or N!\n");
                pauseForEnterKey();
            }
        }
        return returnVal;
    }

    public static Integer getUserInputInteger(String message){
        String input = "";
        Integer returnVal = null;
        while (true){
            input = getUserInput(message, true);
            try{
                returnVal = Integer.parseInt(input);
                return returnVal;
            }
            catch (Exception error){
                System.out.println("\nPlease enter a valid number!\n");
                pauseForEnterKey();
            }
        }
    }

    public static void closeUserInput(){
        uInput.close();
    }
}
