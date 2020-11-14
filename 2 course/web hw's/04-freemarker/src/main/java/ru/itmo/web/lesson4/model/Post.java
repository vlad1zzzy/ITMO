package ru.itmo.web.lesson4.model;

public class Post {
    private final long id, user_id;
    private final String title, text;

    public Post(long id, String title, String text, long user_id) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.user_id = user_id;
    }

    public long getId() {return id;}

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public long getUser_id() {
        return user_id;
    }
}
