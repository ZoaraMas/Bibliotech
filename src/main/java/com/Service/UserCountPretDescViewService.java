package com.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.UserCountPretDescView;
import com.Repository.UserCountPretDescViewRepository;

@Service
public class UserCountPretDescViewService {
    @Autowired
    private UserCountPretDescViewRepository userCountPretDescViewRepository;

    public List<UserCountPretDescView> findAll() {
        return this.userCountPretDescViewRepository.findAll();
    }
}
