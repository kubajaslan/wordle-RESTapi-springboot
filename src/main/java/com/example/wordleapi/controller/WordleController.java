package com.example.wordleapi.controller;

import com.example.wordleapi.entity.Word;
import com.example.wordleapi.exception.MalformedRequestParamException;
import com.example.wordleapi.service.WordService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/words")
public class WordleController {

    private WordService wordService;
    private ArrayList<String> words;
    private ArrayList<String> wordsRemove;

    @Autowired
    public WordleController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostConstruct
    public void loadWords() {
        wordsRemove = new ArrayList<>();
        List<Word> wordList = wordService.findAll();
        words = new ArrayList<>();

        for (Word word : wordList) {
            words.add(word.getText());
        }

    }

    @GetMapping("")
    public ArrayList<String> getWords() {
        return words;
    }


    @GetMapping("/reset")
    public ArrayList<String> reset() {

        List<Word> wordList = wordService.findAll();
        words = new ArrayList<>();
        wordsRemove = new ArrayList<>();

        for (Word word : wordList) {
            words.add(word.getText());
        }

        return words;
    }

    @GetMapping("/param")
    public List<String> excludeLettersParameter(@RequestParam Map<String, String> map) {


        String excludeString = map.get("exclude");
        String yellowInclude = map.get("yellow");
        String greenInclude = map.get("green");

        words = exclude(excludeString);

        words = include(yellowInclude, "yellow");
        words = include(greenInclude, "green");


        return words;

    }


    public ArrayList<String> exclude(String excludeString) {
        if (excludeString != null) {
            excludeString = excludeString.toLowerCase();
            for (String word : words) {
                for (int i = 0; i < excludeString.length(); i++) {
                    if (word.indexOf(excludeString.charAt(i)) > -1) {
                        wordsRemove.add(word);
                    }
                }
            }
            words.removeAll(wordsRemove);
        }
        return words;
    }

    public ArrayList<String> include(String includeString, String colour) {


        Map<Integer, String> includeMap = new HashMap<>();


        if (includeString != null) {

            includeString = includeString.toLowerCase();

            for (int i = 0; i < includeString.length(); i += 2) {

                int tempInt = 0;

                String tempString = null;


                //checking if the request is properly formed (if the green string follows the patters digit-char-digit-char... etc)

                if (!Character.isDigit(includeString.charAt(i))) {
                    throw new MalformedRequestParamException("The pattern of the GREEN or YELLOW request parameter does not follow the pattern digit-char-digit-char...");
                }

                if (includeString.length() % 2 != 0) {
                    throw new MalformedRequestParamException("The pattern of the GREEN or YELLOW request parameter does not follow the pattern digit-char-digit-char... and is not even length");
                }

                tempInt = Character.getNumericValue(includeString.charAt(i));

                tempString = Character.toString(includeString.charAt(i + 1));

                includeMap.put(tempInt, tempString);
            }

            for (String word : words) {

                for (Map.Entry<Integer, String> entry : includeMap.entrySet()) {
                    String letter = entry.getValue();
                    int index = entry.getKey();

                    boolean wordContainsLetterAtGivenIndex = Character.toString(word.charAt(index - 1))
                                                                      .equals(letter);

                    if (colour == "green") {
                        if (!wordContainsLetterAtGivenIndex) {
                            wordsRemove.add(word);

                        }
                    }

                    if (colour == "yellow") {
                        if (!word.contains(letter)) {
                            wordsRemove.add(word);
                        }

                        if (word.contains(letter) && wordContainsLetterAtGivenIndex) {
                            wordsRemove.add(word);

                        }
                    }

                }
            }

            words.removeAll(wordsRemove);
        }


        return words;

    }
}


