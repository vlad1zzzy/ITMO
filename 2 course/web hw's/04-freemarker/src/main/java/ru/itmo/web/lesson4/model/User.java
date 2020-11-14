package ru.itmo.web.lesson4.model;

import java.util.ArrayList;

public class User {
    private final long id;
    private final String handle;
    private final String name;
    private final Color color;
    private final ArrayList<Integer> postsInd;

    public User(long id, String handle, String name, Color color, ArrayList<Integer> postsInd) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.color = color;
        this.postsInd = postsInd;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public ArrayList<Integer> getPosts() {
        return postsInd;
    }

    public void addPost(int index) {
        postsInd.add(index);
    }
}
