package com.project.foodCourt.infrastructure.out.twilio;

import com.project.foodCourt.domain.model.orderresponse.OrderDishResponseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TwilioSmsAdapterTest {

    @InjectMocks
    private TwilioSmsAdapter twilioSmsAdapter;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(twilioSmsAdapter, "accountSid", "test_account_sid");
        ReflectionTestUtils.setField(twilioSmsAdapter, "authToken", "test_auth_token");
        ReflectionTestUtils.setField(twilioSmsAdapter, "fromPhoneNumber", "+1234567890");
    }

    @Test
    void sendOrderReadyNotification_ShouldNotThrowException() {
        OrderDishResponseModel dish = new OrderDishResponseModel();
        dish.setName("Pizza");
        dish.setQuantity(2);
        
        assertDoesNotThrow(() -> {
            twilioSmsAdapter.sendOrderReadyNotification("+0987654321", 123L, List.of(dish));
        });
    }
}