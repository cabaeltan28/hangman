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
    @FXML 
    private Button hintsButton;
    
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
        Animal newAnimal = animalRepository.getRandomWord();
        Place newPlace = placeRepository.getRandomPlace();
        Fruit newFruit = fruitsRepository.getRandomWord();
        Food newFood = foodRepository.getRandomWord();

    private Timeline timer;
    private Integer timeLeft = 30;
    
    private String category;
    private String newCategory;
    private int timailhan=0;
    
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
            category = "ANIMALS";
            hehe.setText(" ");
            hintsBUTTON1();
            startNewGame(animals.getWord());
        });
        place.setOnAction(event -> {
            category = "PLACES";
            hehe.setText(" ");
            hintsBUTTON1();
            startNewGame(places.getWord());
        });
        fruits.setOnAction(event -> {
            category = "FRUIT";
            hehe.setText(" ");
            hintsBUTTON1();
            startNewGame(fruit.getWord());
        });
        food.setOnAction(event -> {
            category = "FOODS";
            hehe.setText(" ");
            hintsBUTTON1();
            startNewGame(foods.getWord());
        });
    }
    public void hintsBUTTON1(){
         hintsButton.setOnAction(event -> {
            gold-=10;
            timailhan++;
            scoreLabel.setText("Gold : "+gold.toString());
            switch (category) {
                case "ANIMALS":
                    hehe.setText(animals.getDefinition());
                    break;
                case "PLACES":
                    hehe.setText(places.getDefinition());
                    break;
                case "FRUIT":
                    hehe.setText(fruit.getDefinition());
                    break;
                case "FOODS":
                    break;
                default:
                    break;
            }
            hintsButton.setDisable(true);
        });
    }
    public void hintsBUTTON2(){
         hintsButton.setOnAction(event -> {
            gold-=10;
            timailhan++;
            scoreLabel.setText("Gold : "+gold.toString());
            switch (category) {
                case "ANIMALS":
                    hehe.setText(newAnimal.getDefinition());
                    break;
                case "PLACES":
                    hehe.setText(newPlace.getDefinition());
                    break;
                case "FRUIT":
                    hehe.setText(newFruit.getDefinition());
                    break;
                case "FOODS":
                    hehe.setText(newFood.getDefinition());
                    break;
                default:
                    break;
            }
        });
    }
    public void nextword() {
        generateKeyboard();
        refreshUI();
    }
    public void nextrepository(){
        switch (category) {
            case "ANIMALS":
                hehe.setText(" ");
                newAnimal = animalRepository.getRandomWord();
                hintsBUTTON2();
                startNewGame(newAnimal.getWord());
                break;
            case "PLACES":
                hehe.setText(" ");
                newPlace = placeRepository.getRandomPlace();
                hintsBUTTON2();
                startNewGame(newPlace.getWord());
                break;
            case "FRUIT":
                hehe.setText(" ");
                newFruit = fruitsRepository.getRandomWord();
                hintsBUTTON2();
                startNewGame(newFruit.getWord());
                break;
            case "FOODS":
                hehe.setText(" ");
                newFood = foodRepository.getRandomWord();
                hintsBUTTON2();
                startNewGame(newFood.getWord());
                break;
        
            default:
                break;
        }
    }

    private void refreshUI() {
        imageView.setImage(new Image(getClass().getResource("/pictures/%d.png".formatted(model.getCurrentWrongs())).toExternalForm()));
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
                scoreLabel.setText("Gold : 0");
            }
        if(gold<=0){
                hintsButton.setDisable(true);
            } else if(gold>0 && timailhan <=0){
                hintsButton.setDisable(false);
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
          timailhan=0;
          attempPerWord=0;
          getRight=0;
          nextwordLabel.setDisable(true);
          hintsButton.setDisable(true);
        }
        
        
        if(model.isWin()){
            textText.setText("You win! Congratulations bro!");
             getRight++;
           j++;
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
          timailhan=0;
           nextwordLabel.setDisable(true);
          hintsButton.setDisable(true);
           
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
            hehe.setText(" ");
            nextrepository();
            hintsBUTTON1();
            hintsBUTTON2();
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
            hehe.setText(" ");
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
            if(gold>0){
                hintsButton.setDisable(false);
            }
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

                textText.setText("Time Out!");

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