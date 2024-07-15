/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 *
 * @author hoang
 */
@Service
public class sendEmail {

    @Autowired
    private JavaMailSender sender;

    public void send(String toEmail, String subject, String message) {
        MimeMessage mimeMessage = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            helper.setFrom("berksbeanscoffee@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(message);
            sender.send(mimeMessage);
        } catch (MessagingException e) {
            // Xử lý lỗi gửi email
            e.printStackTrace();
        }
    }
}
