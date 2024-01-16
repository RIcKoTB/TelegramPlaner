package com.example.telegramplaner.da.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Lesson {
    private String name;
    private DayOfWeek dayOfWeek;
    private LocalTime reminderTime;

    public Lesson(String name, DayOfWeek dayOfWeek, LocalTime reminderTime) {
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.reminderTime = reminderTime;
    }

    public String getName() {
        return name;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setReminderTime(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }
}