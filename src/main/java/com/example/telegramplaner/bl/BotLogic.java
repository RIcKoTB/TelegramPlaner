package com.example.telegramplaner.bl;
import com.example.telegramplaner.da.entity.Lesson;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.TextStyle;
import java.util.*;

public class BotLogic  extends TelegramLongPollingBot {

    private Map<String, String> lessons = new HashMap<>(); // Зберігає пари "день тижня" -> "урок"
    private Timer timer = new Timer();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            if (messageText.startsWith("/addlesson")) {
                String[] parts = messageText.split(" ");
                if (parts.length == 4) {
                    String dayOfWeek = parts[1].toLowerCase();
                    String lessonName = parts[2];
                    LocalTime reminderTime = LocalTime.parse(parts[3]); // Використовуйте LocalTime для обробки часу

                    Lesson lesson = new Lesson(lessonName, convertDayOfWeek(dayOfWeek), reminderTime);

                    sendMessage(chatId, "Урок на " + dayOfWeek + " додано: " + lessonName + ", нагадування о " + reminderTime + " до уроку.");

                    scheduleReminder(chatId, dayOfWeek, lesson);

                } else {
                    sendMessage(chatId, "Невірний формат команди. /addlesson [день тижня] [урок] [години:хвилини до уроку]");
                }
            }

             else if (messageText.startsWith("/getlessons")) {
                sendMessage(chatId, "Розклад уроків: " + lessons.toString());
            } else if (messageText.startsWith("/deletelesson")) {
                String[] parts = messageText.split(" ");
                if (parts.length == 2) {
                    String dayOfWeek = parts[1].toLowerCase();
                    lessons.remove(dayOfWeek);
                    sendMessage(chatId, "Урок на " + dayOfWeek + " видалено.");
                }
                else {
                    sendMessage(chatId, "Невірний формат команди. /deletelesson [день тижня]");
                }

            }
            else if (messageText.startsWith("/testreminder")) {
                testReminder(chatId); // Додавання команди для тестування нагадувань
            }
            else {
                sendMessage(chatId, "Невідома команда. Доступні команди: /addlesson, /getlessons, /deletelesson, /testreminder");
            }
        }

    }

    private DayOfWeek convertDayOfWeek(String dayOfWeek) {
        return DayOfWeek.valueOf(dayOfWeek.toUpperCase());
    }

    private void testReminder(long chatId) {
        int delayInSeconds = 5; // Затримка тестового нагадування (5 секунд)
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                sendReminderMessage(chatId, "Тестове нагадування: Урок на тестовий день - тестовий урок");
            }
        }, delayInSeconds * 1000);
    }

    private Date calculateReminderTime(int dayOfWeek, LocalTime reminderTime) {
        Calendar cal = Calendar.getInstance();

        // Встановлюємо день тижня з використанням значення з enum DayOfWeek
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

        cal.set(Calendar.HOUR_OF_DAY, reminderTime.getHour());
        cal.set(Calendar.MINUTE, reminderTime.getMinute());
        cal.set(Calendar.SECOND, 0);

        // Додати перевірку, щоб нагадування не виходило за межі поточного часу

        return cal.getTime();
    }


    public static int convertToNumber(String dayOfWeekText) {
        try {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(dayOfWeekText.toUpperCase(Locale.ENGLISH));
            return dayOfWeek.getValue();
        } catch (Exception e) {
            // Обробка винятків, якщо текст дня тижня введено неправильно
            System.err.println("Помилка конвертації: " + e.getMessage());
            return -1; // Повертаємо -1 для позначення помилки
        }
    }

    // Оновлений метод для планування нагадувань
    private void scheduleReminderTask(long chatId, Lesson lesson, String dayWeek) {

        int dayNumber = convertToNumber(dayWeek) + 1;

        System.out.println(dayNumber);

        Date reminderTime = calculateReminderTime(dayNumber, lesson.getReminderTime());

        System.out.println(lesson.getDayOfWeek() + " day week");
        System.out.println(reminderTime);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Перевірка часу
                if (isTimeToRemind(reminderTime)) {
                    sendReminderMessage(chatId, "Нагадування: За " +
                            lesson.getReminderTime().toString() + " до уроку '" +
                            lesson.getName() + "' на " + lesson.getDayOfWeek());
                }
            }
        }, reminderTime);
    }

    // Метод для перевірки часу
    private boolean isTimeToRemind(Date reminderTime) {
        // Отримати поточний час
        Date currentTime = new Date();
        System.out.println(currentTime);

        // Порівняти поточний час із часом нагадування
        return currentTime.after(reminderTime) || currentTime.equals(reminderTime);
    }


    private void scheduleReminder(long chatId, String dayOfWeek, Lesson lesson) {
        // Перетворення рядка dayOfWeek в перелічення DayOfWeek
        DayOfWeek enumDayOfWeek = DayOfWeek.valueOf(dayOfWeek.toUpperCase());

        System.out.println(enumDayOfWeek + "enum day");

        // Встановлення дня тижня та часу нагадування в об'єкт Lesson
        lesson.setDayOfWeek(enumDayOfWeek);

        // Планування нагадування
        scheduleReminderTask(chatId, lesson, dayOfWeek);
    }




    private void sendReminderMessage(long chatId, String messageText) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    private void sendMessage(Long chatId, String textToSend){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {

        }
    }

    @Override
    public String getBotUsername() {
        // Повертає ім'я вашого бота
        return "Reminder";
    }

    @Override
    public String getBotToken() {
        // Повертає токен вашого бота
        return "6442289130:AAF7PvfIaBGHKQMujlY24zdfPNTWZ9XB2tk";
    }
}
