package com.helpchain.services;

import com.helpchain.models.User;
import com.helpchain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_shouldSaveUserWhenEmailIsNotTaken() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User savedUser = userService.createUser(user);

        // Assert
        assertEquals("test@example.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createUser_shouldThrowExceptionWhenEmailIsTaken() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(user);
        });
        assertEquals("Email já cadastrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_shouldReturnUserWhenFound() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserById(1L);

        // Assert
        assertEquals("test@example.com", foundUser.getEmail());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserById_shouldThrowExceptionWhenNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserById(1L);
        });
        assertEquals("Usuário não encontrado", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getAllUsers_shouldReturnAllUsers() {
        // Arrange
        List<User> users = List.of(new User(), new User()); 
        when(userRepository.findAll()).thenReturn(users); 

        // Act
        Iterable<User> foundUsers = userService.getAllUsers();

        // Assert
        assertNotNull(foundUsers);
        assertEquals(2, ((List<User>) foundUsers).size()); // Casting para List para verificar o tamanho
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void deleteUser_shouldDeleteUserWhenExists() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(true);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteUser_shouldThrowExceptionWhenNotFound() {
        // Arrange
        when(userRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(1L);
        });
        assertEquals("Usuário não encontrado para exclusão", exception.getMessage());
        verify(userRepository, never()).deleteById(1L);
    }
}
