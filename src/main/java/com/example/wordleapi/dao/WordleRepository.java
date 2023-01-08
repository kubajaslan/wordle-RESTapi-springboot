package com.example.wordleapi.dao;

import com.example.wordleapi.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordleRepository extends JpaRepository<Word, Integer> {

}
