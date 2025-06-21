package com.job_portal.job_portal.mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void SendSimpleMessage(MailBody mailBody) throws MessagingException {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(mailBody.getTo());
            helper.setText(mailBody.getText(),true);
            helper.setSubject(mailBody.getSubject());
            javaMailSender.send(mailMessage);
        }catch (MessagingException exception){
            System.err.println("Failed to send email: " + exception.getMessage());
        }
    }
}
