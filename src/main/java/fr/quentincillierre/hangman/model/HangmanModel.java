package fr.quentincillierre.hangman.model;



import java.util.HashSet;
import java.util.Set;

public class HangmanModel {
    private final String wordToGuess;
    private final int maxWrongs;
    private int currentWrongs;
    private Set<Character> guessedLetter = new HashSet<>();
    private Integer score;
   private boolean isguessed=true;
   private Integer attemp=0;
   
    


    public HangmanModel(String wordToGuess) {
        this.wordToGuess=wordToGuess.toUpperCase();
        this.maxWrongs=10;
        this.score=0;  
        this.guessedLetter.add('-');
        this.guessedLetter.add(' ');

    }
  
    public void reset(){
        this.currentWrongs =0;
    }
    public void resetGuessedLetter(){
        guessedLetter.clear();
    }
    public String getWordToGuess() {
        return wordToGuess;
    }

    public int getCurrentWrongs() {
        return currentWrongs;
    }

    public Set<Character> getGuessedLetter() {
        return guessedLetter;
    }
    public Integer getScore(){
        return this.score;
    }

    public boolean isIsguessed() {
        return isguessed;
    }
    public Integer getAttemp() {
        return attemp;
    }
    public void tryLetter(Character letter) {
        String upperCaseLetter = letter.toString().toUpperCase();
        if(!getWordToGuess().contains(upperCaseLetter)){
            this.currentWrongs +=1;
            isguessed = false;
        }else{
            int i=1;
           if(isguessed==true){
            i++;
            this.score+=(100/wordToGuess.length())*i;
           }else{
            this.score+=(100/wordToGuess.length());
            i=1;
           }
           isguessed=true;
        }
        this.guessedLetter.add(upperCaseLetter.charAt(0));
        attemp++;
    }
    

    public String getHiddenWord() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<wordToGuess.length();i++){
            if(guessedLetter.contains(this.wordToGuess.charAt(i))){
                stringBuilder.append(this.wordToGuess.charAt(i));
            }else{
                stringBuilder.append("_");
            }
        }
        return stringBuilder.toString().trim();
    }

    public boolean isWin() {
        for(Character c : getWordToGuess().toCharArray()){
            if (!getGuessedLetter().contains(c)){
                return false;
            }
        }
        return true;
    
    }

    public boolean isLose() {
        return getCurrentWrongs()>=maxWrongs;
    
    }

    public static void main(String[] args) {
        HangmanModel model = new HangmanModel("JAVA");
        int num =100/model.wordToGuess.length();
        System.out.printf("%d",num);
       }



}
