package com.example.telegramplaner.da.entity;

/**
 * Клас, що представляє користувача системи.
 */
public class User {

    /**
     * Унікальний ідентифікатор користувача.
     */
    private int id;

    /**
     * Логін користувача.
     */
    private String login;

    /**
     * Пароль користувача.
     */
    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


