package com.kamilglazer.blackjack.Players;

public class Player extends Participant{
    private int balance;

    public Player(int balance) {
        super();
        this.balance = balance;
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

}
