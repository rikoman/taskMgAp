package com.example.sendingmessagesemail.controller;

import com.example.sendingmessagesemail.entity.Mail;
import com.example.sendingmessagesemail.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/sendMessage")
public class EmailController {
    private final EmailService emailService;

    @PostMapping
    public String sendEmail(@RequestBody Mail request){
        emailService.sendEmail(request.getRecipient(), request.getSubject(), request.getText());
        return "Email sent successfully";
    }

}
