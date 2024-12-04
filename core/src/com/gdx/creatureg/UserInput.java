package com.gdx.creatureg;
import java.util.Scanner;

public class UserInput {
    static Scanner uInput = new Scanner(System.in);

    public static String getUserInput(String message){
        if (!message.equals("")) {
            System.out.print(message + ": ");
        }
        else{
            System.out.print("Input: ");
        }
        String input = uInput.nextLine();
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
            input = getUserInput(message).toUpperCase();
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

    public static void closeUserInput(){
        uInput.close();
    }
}
