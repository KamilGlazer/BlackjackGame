package com.kamilglazer.blackjack.Players;

public class Player extends Participant{
    private int balance;
    private int currentBet;

    public Player(int balance) {
        super();
        this.balance = balance;
        this.currentBet = 0;
    }

    public int getBalance() {
        return balance;
    }

    public boolean placeBet(int betAmount) {
        if (balance >= betAmount) {
            balance -= betAmount;
            return true;
        } else {
            return false;
        }
    }

    public void addMoney(int amount){
        this.balance += amount;
    }

    public void winBet(){
        this.balance += currentBet *2 ;
        this.currentBet = 0;
    }

    public void loseBet() {
        this.currentBet = 0;
    }

    public int getCurrentBet(){
        return this.currentBet;
    }
}
