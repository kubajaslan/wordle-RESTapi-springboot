package com.example.wordleapi.service;

import com.example.wordleapi.dao.WordleRepository;
import com.example.wordleapi.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    @Autowired
    WordleRepository wordleRepository;

    @Override
    public List<Word> findAll() {
        return wordleRepository.findAll();
    }
}
