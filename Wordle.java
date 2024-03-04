import java.io.InputStream;
import java.util.Scanner;

public class Wordle {

    private static String word;
    private static boolean isRunning = true;
    private static boolean firstRun = true;
    private static int guessCount = 0;

    private final String[] wordList = {"which", "apple", "pizza", "brown", "black", "pines", "crown", "spoke", "child", "gloves", "blond", "mouse", "board"};

    public static void main(String[] args) {

        //Starts the game
        start();

    }

    private static void start(){

        //Creates input obj
        Scanner input = new Scanner(System.in);

        //Creates newGame obj
        Wordle newGame = new Wordle();

        //Picks the word
        newGame.createWord();

        //Creates game loop
        while(isRunning) {

            //Checks if it's the users first run so the intro doesn't get repetitive
            if(firstRun) {
                System.out.println("Welcome to Wordle! When you make a guess '+' will denote that a letter from your guess is correct but in the wrong spot and '-' will denote that a letter is not used. You have six guesses. Good Luck!");
                firstRun = false;
            }

            //Checks if the user has not exceeded six guesses
            String inputGuess;
            if(guessCount <= 6) {

                inputGuess = input.nextLine();

                System.out.println("Guess Result: " + checkGuess(inputGuess));

                guessCount++;

            }else{
                System.out.println("You used all of your guesses. The correct answer was: " + word);
                break;
            }

            //Creates the endgame
            if(inputGuess.equalsIgnoreCase(word)){
                System.out.println("\nCongrats you got it!");
                isRunning = false;
            }
        }
    }

    private void createWord(){
        //Chooses a random word from the word list
        word = wordList[(int) (Math.random() * wordList.length)];
    }

    private static String checkGuess(String guess) {
        StringBuilder newString = new StringBuilder();

        try {

            //Creates object for file
            InputStream inputStream = Wordle.class.getResourceAsStream("fiveLetters.txt");
            Scanner scanner = new Scanner(inputStream);
            boolean isRealWord = false;

            //Checks if the word is a real word
            while (scanner.hasNextLine()) {
                String wordFromFile = scanner.nextLine();
                if (guess.equalsIgnoreCase(wordFromFile)) {
                    isRealWord = true;
                    break;
                }
            }

            //If it is a real word, check the guess
            if (isRealWord) {
                for (int i = 0; i < guess.length(); i++) {
                    char guessedChar = guess.charAt(i);
                    char targetChar = word.charAt(i);

                    if (guessedChar == targetChar) {
                        newString.append(guessedChar);
                    } else if (word.contains(String.valueOf(guessedChar))) {
                        newString.append("+");
                    } else {
                        newString.append("-");
                    }
                }
            } else {
                System.out.println("Please input a real five letter word!");
            }

            scanner.close();

            //Check if there is a file
        } catch (NullPointerException e) {
            System.out.println("Cannot find file");
            e.printStackTrace();
        }
        //Return the guess
        return newString.toString();
    }
}
