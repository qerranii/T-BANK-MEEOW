package com.example.main;

import javafx.scene.control.TextField;

import java.util.List;

public class WordHighlight {

    public static void highlightWord(List<TextField> inputFields, String guessedWord, String selectedWord) {
        // проходим по каждой букве в угаданном слове.
        for (int i = 0; i < guessedWord.length(); i++) {
            TextField inputField = inputFields.get(i); // получаем поле ввода для текущей буквы.
            char guessedChar = guessedWord.charAt(i); // угаданная буква.
            char actualChar = selectedWord.charAt(i); // настоящая буква в загаданном слове.

            // если буквы совпадают, подсвечиваем зелёным.
            if (guessedChar == actualChar) {
                inputField.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                // если буква есть в слове, но не на своём месте, подсвечиваем жёлтым.
            } else if (selectedWord.contains(String.valueOf(guessedChar))) {
                inputField.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                // если буквы нет в слове, подсвечиваем серым.
            } else {
                inputField.setStyle("-fx-background-color: gray; -fx-text-fill: white;");
            }
        }
    }
}
