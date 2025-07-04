package com.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.User;
import com.Repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository UserRepository;

    public User findByInscriptionId(Long idInscription) {
        return this.UserRepository.findByIdInscription(idInscription)
                .orElseThrow(() -> new IllegalArgumentException(
                        "User with inscription ID " + idInscription + " not found."));
    }

    // Verifier si les deux utilisateurs existent
    public boolean userExists(long idUser) {
        User user = this.findById(idUser);
        if (user == null)
            return false;
        return true;
    }

    public User findById(long id) {
        return this.UserRepository.findById(id).orElse(null);
    }

    public User login(String mail, String password) {
        List<User> liste = this.findAll();
        for (int i = 0; i < liste.size(); i++) {
            if (liste.get(i).loginMatch(mail, password))
                return liste.get(i);
        }
        return null;
    }

    public List<User> findAll() {
        return this.UserRepository.findAll();
    }
}
