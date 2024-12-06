package com.example.main;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;

import java.util.List;

public class Keyboard {

    private GameSession gameSession;
    private GameUI gameUI;

    public Keyboard(GameSession gameSession, GameUI gameUI) {
        this.gameSession = gameSession;
        this.gameUI = gameUI;
    }

    public GridPane createKeyboard() {
        // создаём панель для клавиатуры
        GridPane keyboardGrid = new GridPane();
        keyboardGrid.setHgap(5); // горизонтальные промежутки между кнопками.
        keyboardGrid.setVgap(5); // вертикальные промежутки между кнопками.
        keyboardGrid.setAlignment(Pos.CENTER); // выравнивание клавиатуры по центру.

        // создаём строки с буквами для клавиатуры.
        String[] row1 = {"Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "Щ", "З", "Х", "Ъ"};
        String[] row2 = {"Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э"};
        String[] row3 = {"Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю"};

        // добавляем строки клавиш на панель.
        addRowToKeyboard(keyboardGrid, row1, 0);
        addRowToKeyboard(keyboardGrid, row2, 1);
        addRowToKeyboard(keyboardGrid, row3, 2);

        // кнопка "удалить" (backspace).
        Button backspaceButton = new Button("⬅");
        backspaceButton.setMinSize(40, 40); // задаём размер кнопки.
        backspaceButton.setOnAction(e -> onBackspace()); // обработчик нажатия.
        keyboardGrid.add(backspaceButton, 11, 0); // добавляем кнопку на панель.

        return keyboardGrid; // возвращаем панель клавиатуры.
    }

    private void addRowToKeyboard(GridPane grid, String[] row, int rowIndex) {
        // добавляем строку букв на панель
        for (int i = 0; i < row.length; i++) {
            String letter = row[i];
            Button keyButton = new Button(letter);
            keyButton.setMinSize(40, 40); // размер кнопки.
            keyButton.setOnAction(e -> onKeyPress(letter)); // обработчик нажатия на кнопку.
            grid.add(keyButton, i, rowIndex); // добавляем кнопку на нужную строку.
        }
    }

    private void onKeyPress(String letter) {
        // находим первое пустое поле для ввода и добавляем в него букву.
        for (TextField inputField : gameUI.getInputFields().get(gameSession.getCurrentAttempt())) {
            if (inputField.getText().isEmpty()) {
                inputField.setText(letter); // добавляем букву в пустое поле.
                break;
            }
        }
    }

    private void onBackspace() {
        // удаляем последнюю введённую букву.
        List<TextField> row = gameUI.getInputFields().get(gameSession.getCurrentAttempt());
        for (int i = row.size() - 1; i >= 0; i--) {
            TextField inputField = row.get(i);
            if (!inputField.getText().isEmpty()) {
                inputField.clear(); // очищаем поле.
                break;
            }
        }
    }
}
