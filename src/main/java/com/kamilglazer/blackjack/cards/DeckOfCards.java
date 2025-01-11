package com.kamilglazer.blackjack.cards;

import javafx.scene.image.Image;

import java.util.*;

public class DeckOfCards {
    private static final DeckOfCards deckOfCards = null;
    private ArrayList<Card> deck;
    private Image backOfCardImage;



    private DeckOfCards() {
        initializeNewDeckOfCards();
        String imagePath = "/com/kamilglazer/blackjack/images/card_back.png";
        backOfCardImage = new Image(getClass().getResource(imagePath).toString());
    }


    public static DeckOfCards getInstance(){
        return Objects.requireNonNullElseGet(deckOfCards, DeckOfCards::new);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public Image getBackOfCardImage() {
        return backOfCardImage;
    }

    public void setBackOfCardImage(Image backOfCardImage) {
        this.backOfCardImage = backOfCardImage;
    }


    public Card dealTopCard()
    {
        if (deck.isEmpty()) {
            initializeNewDeckOfCards();
        }
        return deck.remove(0);
    }

    private void initializeNewDeckOfCards(){
        List<String> suits = Card.getValidSuits();
        List<String> faceNames = Card.getValidFaceNames();
        this.deck = new ArrayList<>();
        for (String suit:suits)
        {
            for (String faceName:faceNames)
                this.deck.add(new Card(faceName,suit));
        }
        System.out.println("New Deck of cards!");
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }
}
