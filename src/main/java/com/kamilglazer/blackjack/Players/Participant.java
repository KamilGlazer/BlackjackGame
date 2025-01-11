package com.kamilglazer.blackjack.Players;

import com.kamilglazer.blackjack.cards.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Participant {
    protected List<Card> hand;

    public Participant() {
        this.hand = new ArrayList<>();
    }

    public void addCardToHand(Card card){
        hand.add(card);
    }

    public int calculateHandValue(){
        int sum=0;
        int aces = 0;

        for(Card card : hand) {
            sum += card.getValue();
            if("Ace".equals(card.getFaceName())){
                aces++;
            }
        }

        while(sum > 21 && aces > 0) {
            sum -= 10;
            aces--;
        }

        return sum;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void clearHand(){
        hand.clear();
    }

    public boolean isBusted() {
        return calculateHandValue() > 21;
    }
}
