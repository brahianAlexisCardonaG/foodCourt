package com.project.foodCourt.infrastructure.out.twilio;

import com.project.foodCourt.domain.model.orderresponse.OrderDishResponseModel;
import com.project.foodCourt.domain.model.orderresponse.OrderResponseModel;
import com.project.foodCourt.domain.spi.ISmsNotificationPort;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TwilioSmsAdapter implements ISmsNotificationPort {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromPhoneNumber;

    @Override
    public void sendOrderReadyNotification(String phoneNumber,
                                           Long orderId,
                                           List<OrderDishResponseModel> orderDishResponseModel) {
        try {
            Twilio.init(accountSid, authToken);
            
            StringBuilder dishesText = new StringBuilder();
            for (OrderDishResponseModel dish : orderDishResponseModel) {
                dishesText.append(String.format("\n- %s (x%d)", dish.getName(), dish.getQuantity()));
            }
            
            String messageBody = String.format(
                "¡Tu pedido #%d está listo para recoger! 🍽️\n\nPlatos:%s\n\nDirígete al restaurante para retirar tu orden.",
                orderId, dishesText.toString()
            );
            
            Message message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(fromPhoneNumber),
                messageBody
            ).create();
            
            log.info("SMS enviado exitosamente. SID: {}, Para: {}, Orden: {}", 
                message.getSid(), phoneNumber, orderId);
                
        } catch (Exception e) {
            log.error("Error enviando SMS para orden {}: {}", orderId, e.getMessage());
        }
    }
}