package ru.itmo.wp.form;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PostForm {
    @NotNull
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @Size(min = 1, max = 65000)
    @Lob
    private String text;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "(([a-z]{3,15})+[\\s]*)*", message = "Expected lowercase Latin letters and WS. Tag[3-15]")
    private String tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
