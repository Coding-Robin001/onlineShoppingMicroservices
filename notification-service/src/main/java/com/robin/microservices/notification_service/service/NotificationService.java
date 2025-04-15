package com.robin.microservices.notification_service.service;

import com.robin.microservices.notification_service.placeOrder.OrderPlacedEvent;

@Service
public class NotificationService {

    

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(final OrderPlacedEvent orderPlacedEvent){
        log.info("consumed message from order-placed topic ", orderPlacedEvent);
//        send email to customer
        MineMessagePreparator messagePreparator = mineMessage -> {
            MineMessageHelper messageHelper = new MineMessageHelper(mineMessage);
            messageHelper.setFrom("robinSpringShop@email.com");
            messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("your order with orderNumber %s is paced successfully ", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""

             Hi

             your order with order number %s was placed successfully.

             Best regards
             robinSpring shop

             """,
                    orderPlacedEvent.getOrderNumber()
            ));
        };

        try {
            javaMailSender.send(messagePreparator);
            log.info("order notification sent successfully!")
        } catch (MailException){
            log.error("an error occured while sending notification");
            throw new RuntimeException("exception occured when sending notifications");
        }

    }
}
