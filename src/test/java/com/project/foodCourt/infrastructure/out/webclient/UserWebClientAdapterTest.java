package com.project.foodCourt.infrastructure.out.webclient;

import com.project.foodCourt.domain.model.feignclient.UserRoleResponse;
import com.project.foodCourt.infrastructure.out.webclient.mapper.IUserWebClientMapper;
import com.project.foodCourt.infrastructure.out.webclient.response.UserRoleResponseWebClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserWebClientAdapterTest {

    @Mock
    private UserWebClient userWebClient;
    
    @Mock
    private IUserWebClientMapper userWebClientMapper;
    
    @InjectMocks
    private UserWebClientAdapter userWebClientAdapter;
    
    private UserRoleResponseWebClient webClientResponse;
    private UserRoleResponse domainResponse;
    
    @BeforeEach
    void setUp() {
        webClientResponse = new UserRoleResponseWebClient();
        webClientResponse.setId(1L);
        webClientResponse.setFirstName("Test");
        webClientResponse.setLastName("User");
        
        domainResponse = new UserRoleResponse();
        domainResponse.setId(1L);
        domainResponse.setFirstName("Test");
        domainResponse.setLastName("User");
    }
    
    @Test
    void getUserById_Success() {
        when(userWebClient.getUserById(1L)).thenReturn(webClientResponse);
        when(userWebClientMapper.toUserRoleResponse(webClientResponse)).thenReturn(domainResponse);
        
        UserRoleResponse result = userWebClientAdapter.getUserById(1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getFirstName());
        assertEquals("User", result.getLastName());
        verify(userWebClient).getUserById(1L);
        verify(userWebClientMapper).toUserRoleResponse(webClientResponse);
    }
    
    @Test
    void getUserById_ReturnsNull() {
        when(userWebClient.getUserById(1L)).thenReturn(null);
        when(userWebClientMapper.toUserRoleResponse(null)).thenReturn(null);
        
        UserRoleResponse result = userWebClientAdapter.getUserById(1L);
        
        assertNull(result);
        verify(userWebClient).getUserById(1L);
        verify(userWebClientMapper).toUserRoleResponse(null);
    }
}