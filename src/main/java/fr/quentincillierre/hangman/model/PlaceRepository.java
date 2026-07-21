package fr.quentincillierre.hangman.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PlaceRepository {
    private final List<Place> words = new ArrayList<>();
    private final Random random = new Random();

    public PlaceRepository() {
        InputStream is = getClass().getResourceAsStream("/place.txt");

        try (Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(is)))) {
           while (scanner.hasNextLine()) {

    String line = scanner.nextLine().trim();

    if(line.isEmpty()) {
        continue;
    }

    String[] parts = line.split("\\|");

    if(parts.length == 2) {
        words.add(new Place(
                parts[0].trim().toUpperCase(),
                parts[1].trim()
        ));
    }
}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Place getRandomPlace() {
        return words.get(random.nextInt(words.size()));
    }
}