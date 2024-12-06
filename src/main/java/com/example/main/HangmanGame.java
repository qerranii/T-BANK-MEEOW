package com.example.main;

import javafx.application.Application;
import javafx.stage.Stage;

public class HangmanGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        // создаём новый объект игры, который будет хранить всю информацию о процессе.
        GameSession gameSession = new GameSession();

        // создаём интерфейс игры, который будет отображать всё на экране.
        // передаём сюда окно (primaryStage) и саму игровую сессию (gameSession).
        GameUI gameUI = new GameUI(primaryStage, gameSession);

        // настраиваем интерфейс игры (например, кнопки, текст, изображения и т.д.).
        gameUI.initializeUI();
    }

    public static void main(String[] args) {
        // запускаем приложение, чтобы оно открылось на экране.
        launch(args);
    }
}
