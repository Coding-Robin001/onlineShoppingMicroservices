package com.robin.microservices.notification_service.service;

import com.robin.microservices.notification_service.placeOrder.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed", groupId = "notification-group")
    public void listen(final OrderPlacedEvent orderPlacedEvent) {
        log.info("Consumed message from 'order-placed' topic: {}", orderPlacedEvent);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom("robinSpringShop@email.com");
            helper.setTo(orderPlacedEvent.getEmail());
            helper.setSubject(String.format("Your order with order number %s was placed successfully", orderPlacedEvent.getOrderNumber()));
            helper.setText(String.format("""
                    Hi,

                    Your order with order number %s was placed successfully.

                    Best regards,
                    RobinSpring Shop
                    """, orderPlacedEvent.getOrderNumber()));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("Order notification email sent successfully!");
        } catch (MailException ex) {
            log.error("An error occurred while sending the email notification", ex);
            throw new RuntimeException("Failed to send email notification", ex);
        }
    }
}
