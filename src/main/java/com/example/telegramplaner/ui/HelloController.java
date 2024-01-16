package com.example.telegramplaner.ui;

import com.example.telegramplaner.bl.BotLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws TelegramApiException {
        welcomeText.setText("Welcome to JavaFX Application!");

        BotLogic bot = new BotLogic();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            botsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}