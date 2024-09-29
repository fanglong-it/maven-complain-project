package fpt.uni.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSender {

    public static final String SENDER_EMAIL = "cunplong.1@gmail.com";
    public static final String SENDER_PASSWORD = "rhxd xaiz jqxt fvjc";

    public static void main(String[] args) {
        // Provide your email credentials and mail server details
        String receiverEmail = "fang.longpc@gmail.com";
        String emailTitle = "Test Email";
        String emailContent = "Hello, this is a test email.";
        sendEmail(receiverEmail, emailTitle, emailContent);
    }

    public static void sendEmail(String receiverEmail, String emailTitle, String emailContent) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Change this to your mail server
        properties.put("mail.smtp.port", "587"); // Change this to your mail server's port

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject(emailTitle);
            message.setText(emailContent);

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email. Error: " + e.getMessage());
        }
    }
}
