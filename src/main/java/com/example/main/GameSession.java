package com.example.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameSession {
    private static final List<String> words = new ArrayList<>();
    private String selectedWord;
    private int attempts;
    private int currentAttempt;

    static {
        loadWordsFromFile(); // загружаем список слов в статический блок, чтобы они были доступны сразу.
    }

    public GameSession() {
        // инициализация новой сессии игры
        Random rand = new Random();
        selectedWord = words.get(rand.nextInt(words.size())); // выбираем случайное слово.
        attempts = 5; // количество попыток игрока.
        currentAttempt = 0; // текущая попытка игрока.
    }

    private static void loadWordsFromFile() {
        // Путь к файлу russian_words.txt
        String filePath = "russian_words.txt"; // Убедитесь, что файл находится в правильном месте.

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Убираем лишние пробелы.
                if (line.length() == 5) { // Добавляем только слова длиной 5 символов.
                    words.add(line.toLowerCase()); // Добавляем слово в список (в нижнем регистре).
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    public String getSelectedWord() {
        return selectedWord; // возвращаем выбранное слово.
    }

    public int getAttempts() {
        return attempts; // возвращаем количество оставшихся попыток.
    }
    public int getCurrentAttempt() {
        return currentAttempt; // возвращаем номер текущей попытки.
    }

    public void incrementAttempt() {
        currentAttempt++; // увеличиваем номер текущей попытки.
    }

    public void decrementAttempts() {
        attempts--; // уменьшаем количество оставшихся попыток.
    }

    public void resetGame() {
        // сбрасываем игру, выбираем новое слово и восстанавливаем количество попыток.
        Random rand = new Random();
        selectedWord = words.get(rand.nextInt(words.size()));
        attempts = 15;
        currentAttempt = 0;
    }

    public boolean checkWord(String word) {
        return word.equals(selectedWord); // проверяем, совпадает ли введённое слово с выбранным.
    }

    public boolean isGameOver() {
        return attempts <= -1; // проверяем, закончились ли попытки.
    }

    public void reduceAttempts() {
        attempts--; // уменьшаем количество оставшихся попыток.
    }
}
