package fr.quentincillierre.hangman.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FoodRepository {
    private final List<Food> words = new ArrayList<>();
    private final Random random = new Random();

    public FoodRepository(){
        InputStream is = getClass().getResourceAsStream("/food.txt");

        try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(is)))) {
            while (scanner.hasNextLine()) {

    String line = scanner.nextLine().trim();

    if(line.isEmpty()) {
        continue;
    }

    String[] parts = line.split("\\|");

    if(parts.length == 2) {
        words.add(new Food(
                parts[0].trim().toUpperCase(),
                parts[1].trim()
        ));
    }
}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Food getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }
    
}