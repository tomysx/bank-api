package com.example.apibank.services;

import com.example.apibank.model.AppUser;
import com.example.apibank.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setName("tomysx");

        when(userRepository.save(user)).thenReturn(user);

        AppUser result = userService.registerUser(user);

        assertEquals(user, result);
    }

    @Test
    public void testGetUserByIdFound() {
        AppUser user = new AppUser();
        user.setId(1L);
        user.setName("tomysx");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        AppUser result = userService.getUserById(1L);

        assertEquals(user, result);
    }

    @Test
    public void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(1L));
    }
}