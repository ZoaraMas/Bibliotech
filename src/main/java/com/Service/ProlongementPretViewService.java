package com.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entite.ProlongementPretView;
import com.Repository.ProlongementPretViewRepository;

@Service
public class ProlongementPretViewService {
    @Autowired
    private ProlongementPretViewRepository prolongementPretViewRepository;

    public List<ProlongementPretView> findAll() {
        return this.prolongementPretViewRepository.findAll();
    }
}
