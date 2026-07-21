package fr.quentincillierre.hangman.model;

public class Place {
    private final String word;
    private final String definition;

    public Place(String word, String definition) {
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
