package com.project.foodCourt.infrastructure.out.twilio;

import com.project.foodCourt.domain.model.orderresponse.OrderDishResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TwilioSmsAdapterTest {

    @InjectMocks
    private TwilioSmsAdapter twilioSmsAdapter;
    
    private List<OrderDishResponseModel> orderDishes;
    
    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(twilioSmsAdapter, "accountSid", "test-sid");
        ReflectionTestUtils.setField(twilioSmsAdapter, "authToken", "test-token");
        ReflectionTestUtils.setField(twilioSmsAdapter, "fromPhoneNumber", "whatsapp:+1234567890");
        
        OrderDishResponseModel dish = new OrderDishResponseModel();
        dish.setName("Test Dish");
        dish.setQuantity(2);
        orderDishes = List.of(dish);
    }
    
    @Test
    void sendOrderReadyNotification_Success() {
        try (MockedStatic<Twilio> twilioMock = mockStatic(Twilio.class)) {
            
            twilioSmsAdapter.sendOrderReadyNotification(
                "whatsapp:+1234567890", 
                1L, 
                orderDishes, 
                "123456"
            );
            
            twilioMock.verify(() -> Twilio.init("test-sid", "test-token"));
        }
    }
    
    @Test
    void sendOrderReadyNotification_Exception() {
        try (MockedStatic<Twilio> twilioMock = mockStatic(Twilio.class)) {
            twilioMock.when(() -> Twilio.init(any(), any())).thenThrow(new RuntimeException("Twilio error"));
            
            // Should not throw exception, just log error
            twilioSmsAdapter.sendOrderReadyNotification(
                "whatsapp:+1234567890", 
                1L, 
                orderDishes, 
                "123456"
            );
            
            twilioMock.verify(() -> Twilio.init("test-sid", "test-token"));
        }
    }
}