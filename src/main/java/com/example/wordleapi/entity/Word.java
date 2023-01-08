package com.example.wordleapi.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "word")
    private String text;

    public Word() {
    }

    public Word(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


//    public int lastIndexOf(String letter) {
//        return text.lastIndexOf(letter);
//    }
//
//    public int charAt(int index){
//        return text.charAt(index);
//    }
}
