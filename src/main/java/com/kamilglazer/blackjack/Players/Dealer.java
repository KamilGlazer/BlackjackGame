package com.kamilglazer.blackjack.Players;

import com.kamilglazer.blackjack.cards.Card;

public class Dealer extends Participant{

    private boolean isFirstCardRevealed;


    public Dealer(boolean isFirstCardRevealed) {
        super();
        this.isFirstCardRevealed = isFirstCardRevealed;
    }

    public boolean shouldHit(){
        return calculateActualValue() < 17;
    }

    @Override
    public int calculateHandValue() {
        int sum=0;
        int aces = 0;

        boolean isFirstCard = true;

        for(Card card : hand) {

            if(isFirstCard){
                isFirstCard=false;
                continue;
            }

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

    public int calculateActualValue(){
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


    public boolean isFirstCardRevealed() {
        return isFirstCardRevealed;
    }

    public void setFirstCardRevealed(boolean firstCardRevealed) {
        isFirstCardRevealed = firstCardRevealed;
    }

    @Override
    public boolean isBusted() {
        return calculateActualValue() > 21;
    }
}
