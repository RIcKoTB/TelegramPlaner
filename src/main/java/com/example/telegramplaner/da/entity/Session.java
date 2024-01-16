package com.example.telegramplaner.da.entity;

import java.time.LocalDateTime;

public class Session {
    int userId;
    private LocalDateTime date;

    /**
     * Конструктор класу Session.
     *
     * @param userId Ідентифікатор користувача.
     * @param date   Дата сесії.
     */
    public Session(int userId, LocalDateTime date) {
        this.userId = userId;
        this.date = date;
    }

    /**
     * Отримує ідентифікатор користувача.
     *
     * @return Ідентифікатор користувача.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Отримує дату сесії.
     *
     * @return Дата сесії.
     */
    public LocalDateTime getDate() {
        return date;
    }
}
