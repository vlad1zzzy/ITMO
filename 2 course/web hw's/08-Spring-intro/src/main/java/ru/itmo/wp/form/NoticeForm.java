package ru.itmo.wp.form;

import org.hibernate.annotations.Type;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class NoticeForm {
    @Lob @Type(type = "org.hibernate.type.TextType") @NotEmpty
    @Size(max = 30, message = "Oversize notice content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
