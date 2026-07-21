package fr.quentincillierre.hangman.model;

public class Food {
    private final String word;
    private final String definition;

    public Food(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }
    public String getWord() {
        return word;
    }
    public String getDefinition() {
        return definition;
    }
}
