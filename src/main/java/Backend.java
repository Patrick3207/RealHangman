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
        //wählt zufälliges Wort aus File "words.json" aus, das erraten werden muss

        JSONParser parser = new JSONParser();

        //https://www.educative.io/edpresso/how-to-generate-random-numbers-in-java
        Random rand = new Random(); //zum Erzeugen einer Zufallszahl
        int upperbound = 100;
        int choice = rand.nextInt(upperbound); //gibt zufällige Zahl aus zwischen 0 und [upperbound - 1]
        System.out.println(choice); //ausgabe aktuell nur für testzwecke

        //https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library
        try {
            JSONObject a = (JSONObject) parser.parse(new FileReader("src/words.json"));
            JSONArray b = (JSONArray) a.get("words"); //"files"enthält array mit 100 Worten
            String word = (String) b.get(choice); //wort an der Stelle [Zufallszahl] wird aus dem array ausgewählz
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
        //Usereingabe um Wort zu erraten

        Scanner scanner = new Scanner(System.in);

        //https://www.edureka.co/community/2379/is-it-possible-to-take-char-input-from-scanner
        System.out.println("guess:");
        //try = strict --> Eingabe wird nur akzeptiert, wenn genau ein char eingegeben wird
        try {
            char guess = scanner.next(".").charAt(0);
            return guess;
        } catch (Exception ex) {
            System.out.println("Invalid!(guess)");
            return 0;
        }
    }

    public static char guessCheck(char guess){
        //https://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou
        //wenn Kleinbuchstabe --> to UpperCase (um Probleme mit Casesensitiveness zu umgehen)
        if (guess >= 'a' && guess <= 'z') {
            System.out.print(Character.toUpperCase(guess)); //ausgabe aktuell nur für testzwecke
            return Character.toUpperCase(guess);//ausgabe aktuell nur für testzwecke
        } else if (guess >= 'A' && guess <= 'Z') {
            System.out.println(guess);
            return guess; //ausgabe aktuell nur für testzwecke
            // else: wenn kein letter --> invalid
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

        int lives = 5; // Momentan noch willkrülich gewählt
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
                    }
                }
                if(Arrays.equals(visual, compare)){
                    System.out.println(visual);
                    System.out.println("You won!");
                    break;
                }
                System.out.println(visual);
            }

        }
        if(lives == 0){
            System.out.println("You lost!");
        }
    }
}
