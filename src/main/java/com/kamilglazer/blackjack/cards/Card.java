package com.kamilglazer.blackjack.cards;

import javafx.scene.image.Image;

import java.util.Arrays;
import java.util.List;

public class Card {
    private String faceName;
    private String suit;
    private Image image;
    private int value;


    public Card(String faceName, String suit) {
        this.faceName = faceName;
        this.suit = suit;
        String fileName = this.suit + "-" + this.faceName +".png";
        String imagePath = "/com/kamilglazer/blackjack/images/" + fileName;
        this.image = new Image(getClass().getResource(imagePath).toString());
        this.value = getValueForCard(this.faceName);
    }

    private int getValueForCard(String faceName) {
        return switch (faceName) {
            case "Jack", "Queen", "King" -> 10;
            case "Ace" -> 11;
            default -> Integer.parseInt(faceName);
        };
    }


    public static List<String> getValidFaceNames(){
        return Arrays.asList("2","3","4","5","6","7","8","9","10","Jack",
                "Queen","King","Ace");
    }

    public static List<String> getValidSuits(){
        return Arrays.asList("Hearts","Diamond","Spades","Clubs");
    }

    public void setSuit(String suit) {
        List<String> validSuits = Card.getValidSuits();
        suit = suit.toLowerCase();

        if(validSuits.contains(suit)){
            this.suit = suit;
        }else{
            throw new IllegalArgumentException("valid suits are: "+ validSuits);
        }
    }


    public void setFaceName(String faceName) {
        List<String> validFaceNames = Card.getValidFaceNames();
        faceName = faceName.toLowerCase();

        if(validFaceNames.contains(faceName)){
            this.faceName = faceName;
        }else{
            throw new IllegalArgumentException("Valid face names are: " + validFaceNames);
        }
    }

    public String getFaceName() {
        return faceName;
    }

    public String getSuit() {
        return suit;
    }



    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
