import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Backend {

    public static String word() {

        JSONParser parser = new JSONParser();

        Random rand = new Random();
        int upperbound = 100;
        int choice = rand.nextInt(upperbound);
        System.out.println(choice); //ausgabe aktuell nur für testzwecke

        //https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
        try {
            JSONObject a = (JSONObject) parser.parse(new FileReader("src/words.json"));
            JSONArray b = (JSONArray) a.get("words");
            String word = (String) b.get(choice);
            System.out.println(word.toUpperCase()); //ausgabe aktuell nur für testzwecke
            return word.toUpperCase(); //Spiel wird nur mit Großbuchstaben gespielt, um Problem mit Casesensitiveness zu umgehen
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static char guess() {

        Scanner scanner = new Scanner(System.in);

        //https://www.edureka.co/community/2379/is-it-possible-to-take-char-input-from-scanner
        //https://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou
        //https://stackoverflow.com/questions/3696441/converting-a-char-to-uppercase
        System.out.println("guess:");
        //try = strict --> Eingabe wird nur akzeptiert, wenn genau ein char eingegeben wird
        // if-statement in try: wenn kein letter --> invalid, wenn Kleinbuchstabe --> to UpperCase
        try {
            char guess = scanner.next(".").charAt(0);
            return guess;
        } catch (Exception ex) {
            System.out.println("Invalid!(guess)");
            return 0;
        }
    }

    public static char guessCheck(char guess){

        if (guess >= 'a' && guess <= 'z') {
            System.out.print(Character.toUpperCase(guess)); //ausgabe aktuell nur für testzwecke
            return Character.toUpperCase(guess);//ausgabe aktuell nur für testzwecke
        } else if (guess >= 'A' && guess <= 'Z') {
            System.out.println(guess);
            return guess; //ausgabe aktuell nur für testzwecke
        } else {
            System.out.println("Invalid!(guessChecked)"); //ausgabe aktuell nur für testzwecke
            return 0;
        }

    }

    private static String already = "";

    public static int abzug (char guessCheck, String word){

        int abzug = 0;

        if(guessCheck == 0){
            System.out.println("Invalid!(checkWithWord)");
        }else if(already.contains(guessCheck + "")){
            System.out.println("You already tried that one!");
        }else if(word.contains(guessCheck + "") && !already.contains(guessCheck + "")){
            System.out.println("Correct!");
            already = already + guessCheck;
            abzug = 2;
        }
        else if(!word.contains(guessCheck + "") && !already.contains(guessCheck + "")){
            System.out.println("Wrong!");
            already = already + guessCheck;
            abzug = 1;
        }
        return abzug;
    }

    public static void main(String[] args) throws FileNotFoundException {

        int lives = 5;
        String word = word();
        char [] compare = word.toCharArray();
        char [] visual = new char[word.length()];

        for(int i = 0; i < visual.length; i++){
            visual [i] = '_';
        }

        System.out.println(visual);

        while(lives > 0){
            char guess = guessCheck(guess());
            int abzug = abzug(guess, word);
            if(abzug == 1){
                lives = lives - abzug;
            }else if(abzug == 2){
                for(int i = 0; i < word.length(); i++){
                    if(guess == compare[i]){
                        visual[i] = guess;
                        System.out.println(visual);
                    }
                }
                if(Arrays.equals(visual, compare)){
                    System.out.println("You won!");
                    break;
                }
            }

        }
        if(lives == 0){
            System.out.println("You lost!");
        }
    }
}
