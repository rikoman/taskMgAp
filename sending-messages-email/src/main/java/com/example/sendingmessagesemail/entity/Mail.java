package com.example.sendingmessagesemail.entity;

import lombok.Data;

@Data
public class Mail {
    private String recipient;
    private String subject;
    private String text;
}
