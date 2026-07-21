package fr.quentincillierre.hangman.model;

public class Animal {
    private final String word;
    private final String definition;

    public Animal(String word, String definition) {
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
