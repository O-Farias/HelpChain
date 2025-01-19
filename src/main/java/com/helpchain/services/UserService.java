package com.helpchain.services;

import com.helpchain.models.User;
import com.helpchain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Método para criar um novo usuário
    public User createUser(User user) {
        // Validação simples: verifica se o email já está cadastrado
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }
        return userRepository.save(user);
    }

    // Método para buscar usuário pelo ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    // Método para listar todos os usuários
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Método para deletar um usuário pelo ID
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado para exclusão");
        }
        userRepository.deleteById(id);
    }
}
