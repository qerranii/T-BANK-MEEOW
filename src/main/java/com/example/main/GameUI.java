package com.example.main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameUI {

    private Stage primaryStage;
    private GameSession gameSession;
    private GridPane gridPane;
    private Label attemptsLabel;
    private Label messageLabel;
    private List<List<TextField>> inputFields; // список для хранения полей ввода для каждой строки (каждое слово - это строка с полями).

    public GameUI(Stage primaryStage, GameSession gameSession) {
        this.primaryStage = primaryStage;
        this.gameSession = gameSession;
        this.inputFields = new ArrayList<>();
    }

    public void initializeUI() {
        VBox root = new VBox(10); // основное окно, в котором будут элементы управления.
        root.setPadding(new Insets(10)); // добавляем отступы.

        gridPane = new GridPane();
        gridPane.setHgap(10); // горизонтальные промежутки между элементами.
        gridPane.setVgap(10); // вертикальные промежутки.

        // метка с количеством оставшихся попыток
        attemptsLabel = new Label("Попытки: " + gameSession.getAttempts());
        messageLabel = new Label(); // для отображения сообщений пользователю.
        gridPane.add(attemptsLabel, 0, 0, 5, 1);
        gridPane.add(messageLabel, 0, 1, 5, 1);

        // кнопка для отправки попытки
        Button guessButton = new Button("А ты угадай)))");
        guessButton.setOnAction(e -> processGuess()); // обработчик нажатия на кнопку.
        gridPane.add(guessButton, 0, 2, 5, 1);

        root.getChildren().add(gridPane); // добавляем элементы на экран.

        // создаём виртуальную клавиатуру
        Keyboard keyboard = new Keyboard(gameSession, this);
        root.getChildren().add(keyboard.createKeyboard()); // добавляем клавиатуру на экран.

        // начальные поля для ввода.
        addNewRowForInput();

        Scene scene = new Scene(root, 600, 600); // создаём сцену.
        primaryStage.setScene(scene); // устанавливаем сцену в окно.
        primaryStage.setTitle("Ратмир Бурдюков"); // заголовок окна.
        primaryStage.show(); // показываем окно.
    }

    public void addNewRowForInput() {
        List<TextField> rowFields = new ArrayList<>();
        GridPane rowGrid = new GridPane();
        rowGrid.setHgap(5); // горизонтальные промежутки между полями.
        rowGrid.setVgap(5); // вертикальные промежутки.

        // создаём поля ввода для каждой буквы.
        for (int i = 0; i < 5; i++) {
            TextField inputField = new TextField();
            inputField.setMaxWidth(30); // ограничиваем ширину.
            inputField.setPromptText("Буква"); // подсказка для поля.
            inputField.setAlignment(Pos.CENTER); // выравнивание текста по центру.
            inputField.setEditable(false); // делаем поле не редактируемым.
            rowFields.add(inputField);
            rowGrid.add(inputField, i, 0); // добавляем поля в строку.
        }
        inputFields.add(rowFields); // добавляем строку в список полей.

        gridPane.add(rowGrid, 0, gameSession.getCurrentAttempt() + 3); // добавляем строку на экран.
    }

    public void processGuess() {
        // собираем слово, которое ввёл пользователь
        StringBuilder guessedWord = new StringBuilder();
        for (TextField inputField : inputFields.get(gameSession.getCurrentAttempt())) {
            guessedWord.append(inputField.getText().trim().toLowerCase()); // добавляем буквы в слово
        }

        // если слово не заполнено полностью, показываем ошибку.
        if (guessedWord.length() != 5) {
            messageLabel.setText("Слово должно быть длиной 5 букв!");
            return;
        }

        // проверяем, угадано ли слово.
        if (gameSession.checkWord(guessedWord.toString())) {
            messageLabel.setText("Поздравляем, вы угадали слово!");
        } else {
            gameSession.decrementAttempts(); // уменьшаем количество попыток.
            messageLabel.setText("Неправильно! Попробуйте снова.");

            // если попытки закончились, сообщаем об этом.
            if (gameSession.isGameOver()) {
                messageLabel.setText("Вы проиграли! Загаданное слово: " + gameSession.getSelectedWord());
                return;
            }
        }

        gameSession.incrementAttempt(); // увеличиваем текущую попытку.
        updateAttempts(); // обновляем количество оставшихся попыток.
        addNewRowForInput(); // добавляем новую строку для следующего ввода.

        // подсвечиваем все строки с попытками.
        highlightAllWords();
    }

    public void updateAttempts() {
        attemptsLabel.setText("Попытки: " + gameSession.getAttempts()); // обновляем метку с оставшимися попытками.
    }

    public void highlightAllWords() {
        // подсвечиваем все строки, включая текущую.
        for (int i = 0; i <= gameSession.getCurrentAttempt(); i++) {
            List<TextField> row = inputFields.get(i);
            StringBuilder guessedWord = new StringBuilder();
            for (TextField inputField : row) {
                guessedWord.append(inputField.getText().trim().toLowerCase());
            }
            WordHighlight.highlightWord(row, guessedWord.toString(), gameSession.getSelectedWord()); // подсвечиваем буквы в строке.
        }
    }

    public List<List<TextField>> getInputFields() {
        return inputFields; // возвращаем список всех полей ввода.
    }
}
