package fr.quentincillierre.hangman.controller;

import fr.quentincillierre.hangman.model.Animal;
import fr.quentincillierre.hangman.model.AnimalRepository;
import fr.quentincillierre.hangman.model.Food;
import fr.quentincillierre.hangman.model.Fruit;
import fr.quentincillierre.hangman.model.Place;
import fr.quentincillierre.hangman.model.FoodRepository;
import fr.quentincillierre.hangman.model.FruitsRepository;
import fr.quentincillierre.hangman.model.HangmanModel;
import fr.quentincillierre.hangman.model.PlaceRepository;
import fr.quentincillierre.hangman.model.WordRepository;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class GameController {
  
    @FXML
    private GridPane keyBoardGrid;

    @FXML
    private ImageView imageView;

    private HangmanModel model;
    
    @FXML
    private Label wordLabel;

    @FXML
    private Label resultLabel;

    @FXML
    private Button scoreLabel;

    private String wordToGuess;

    @FXML
    private Button tryagainButton;
    @FXML
    private Button highScoreLabel;
    @FXML
    private Button streakLabel;

    @FXML
    private Button nextwordLabel;
    
    @FXML
    private Text textText;

    @FXML
    private TextField diamondText;
    @FXML
    private Button animal;
    @FXML
    private Button place;
    @FXML
    private Button fruits;
    @FXML
    private Button food;
    @FXML
    private Button hehe;
    @FXML
    private Label timerLabel;
    
    private Integer i=0;
    private Integer gold=0;
    private Integer j=0;
    private Integer attemp=0;
    private Integer getRight=0;
    private Integer attempPerWord=0;
    private Integer diamond=50;
        FoodRepository foodRepository = new FoodRepository();
        FruitsRepository fruitsRepository = new FruitsRepository();
        PlaceRepository placeRepository = new PlaceRepository();
        AnimalRepository animalRepository = new AnimalRepository();
        WordRepository wordRepository = new WordRepository();
        Animal animals = animalRepository.getRandomWord();
        Food foods = foodRepository.getRandomWord();
        Fruit fruit =  fruitsRepository.getRandomWord();
        Place places = placeRepository.getRandomPlace();

    private Timeline timer;
    private Integer timeLeft = 30;
    
    private String category;
    
    public void initialize() {
        generateKeyboard();
        setOnAction();
       setUpCategoryButtons();
    }
    public void startNewGame(String word){
         if(timer != null){
        timer.stop();
    }
        wordToGuess=word;
        model = new HangmanModel(word);
        startTimer();
         resultLabel.setText("");
        textText.setText("");

        generateKeyboard();   // or enable existing keyboard
       refreshUI();
    }

    public void setUpCategoryButtons(){
        for(Node node: keyBoardGrid.getChildren()){
            if(node instanceof Button button ){
                button.setDisable(true);
            }
        };
        animal.setOnAction(event -> {
            category = "ANIMAL";
            hehe.setText(animals.getDefinition());
            startNewGame(animals.getWord());
        });
        place.setOnAction(event -> {
            category = "PLACE";
            hehe.setText(places.getDefinition());
            startNewGame(places.getWord());
        });
        fruits.setOnAction(event -> {
            hehe.setText(fruit.getDefinition());
            category = (fruit.getWord());
        });
        food.setOnAction(event -> {
            category = "FOOD";
            hehe.setText(foods.getDefinition());
            startNewGame(foods.getWord());
        });
    }
    public void nextword() {
        generateKeyboard();
        refreshUI();
    }
    public void nextrepository(){
        switch (category) {
            case "ANIMAL":
            Animal newAnimal = animalRepository.getRandomWord();
           
            startNewGame(newAnimal.getWord());
                break;
            case "PLACE":
                Place newPlace = placeRepository.getRandomPlace();
                hehe.setText(newPlace.getDefinition());
                startNewGame(newPlace.getWord());
                break;
            case "FRUITS":
                Fruit newFruit = fruitsRepository.getRandomWord();
                hehe.setText(newFruit.getDefinition());
                startNewGame(newFruit.getWord());
                break;
            case "FOOD":
                Food newFood = foodRepository.getRandomWord();
                hehe.setText(newFood.getDefinition());
                startNewGame(newFood.getWord());
                break;
        
            default:
                break;
        }
    }

    private void refreshUI() {
        imageView.setImage(new Image(getClass().getResource("/pictures/%d-hangman.png".formatted(model.getCurrentWrongs())).toExternalForm()));
        this.wordLabel.setText(model.getHiddenWord());
        if(attempPerWord==0){
            tryagainButton.setDisable(true);
        } else {
            tryagainButton.setDisable(false);
        }
         if(gold>=10){
                nextwordLabel.setDisable(false);
            } else {
                nextwordLabel.setDisable(true);
            }
              
        if(model.isLose()){
            textText.setText("Game over! Better luck next time.");
            this.wordLabel.setText(wordToGuess);
            i=0;
            this.streakLabel.setText("0");
            
            attemp++;
             if(timer != null){
            timer.stop();
            }
            for(Node node: keyBoardGrid.getChildren()){
            if(node instanceof Button button){
                button.setDisable(true);
            }
          }
         
          attempPerWord=0;
          getRight=0;
        }
        
        
        if(model.isWin()){
            textText.setText("You win! Congratulations bro!");
             getRight++;
           attemp++;
           if(model.isWin()){
            if(timer != null){
             timer.stop();
                }
             textText.setText("You Win");
            }
          disabled(false);
         diamondText.setText("💎 :"+(diamond+=20));
          attempPerWord=0;
            j++;
        }
       
         this.streakLabel.setText("STREAK :"+getRight.toString());
         
        
                this.highScoreLabel.setText("STATUS: "+j.toString()+"/"+attemp);
    }

    public void handleKeyboardInput(String character){
        char letter = Character.toUpperCase(character.charAt(0));
        model.tryLetter(letter);
        attempPerWord++;
        if(model.isIsguessed()==false){
            textText.setText("Wrong Guess!");
        }
        if(model.isIsguessed()==true){
            textText.setText("YOU ARE RIGHT! ");
            gold +=10;
            this.scoreLabel.setText("GOLD :"+gold.toString());
        }
        
        refreshUI();

    }

    private void generateKeyboard() {
         keyBoardGrid.getChildren().clear(); 
         int columns = 13;
       for(int i=0; i<26; i++){
        char letter = (char) ('A'+ i);
        Button button = new Button(String.valueOf(letter));
        
        button.setOnAction(event -> {
            handleKeyboardInput(button.getText());
            button.setDisable(true);
        } );
        
        int col = i%columns;
        int row = i/columns;
        keyBoardGrid.add(button,col,row);
       }
    }
   

    private void setOnAction(){
        
        tryagainButton.setOnAction(event -> {
            nextrepository();
            if(attempPerWord>0){
                if(gold==0){
                gold=0;
                } else {
                    gold -=10;
                }
                attemp++;
                getRight=0;
            }
            textText.setText(" ");
            model.reset();
            this.resultLabel.setText(" ");
            model.resetGuessedLetter();
            this.wordLabel.setText(model.getHiddenWord());
            streakLabel.setText("STREAK :"+getRight.toString());
        });
        
        nextwordLabel.setOnAction(event -> {
             if(timer != null){
        timer.stop();
    }   startTimer();
            disabled(false);
            textText.setText(" ");
            this.gold -=10;
            model.reset();
            this.resultLabel.setText(" ");
            scoreLabel.setText("GOLD :" + gold.toString());
            model.resetGuessedLetter();
            this.wordLabel.setText(model.getHiddenWord());
            nextword();
        });
    }
private void disabled(boolean enabled) {
    for (Node node : keyBoardGrid.getChildren()) {
        if (node instanceof Button button) {
            button.setDisable(!enabled);
        }
    }
}
    private void startTimer() {

    timeLeft = 30;

    timer = new Timeline(
        new KeyFrame(Duration.seconds(1), event -> {

            timeLeft--;

            timerLabel.setText("Time: " + timeLeft);

            if(timeLeft <= 0){
                timer.stop();
                tryagainButton.setDisable(false);
                textText.setText("Time Out!");
                attemp++;
                wordLabel.setText(wordToGuess);

                for(Node node : keyBoardGrid.getChildren()){
                    if(node instanceof Button button){
                        button.setDisable(true);
                    }
                }
            }
        })
    );

    timer.setCycleCount(Timeline.INDEFINITE);
    timer.play();
}

}