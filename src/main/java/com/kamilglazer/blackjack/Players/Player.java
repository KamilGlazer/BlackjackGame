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

    public Memento save() {
        return new Memento(this.balance);
    }

    public void restore(Memento memento){
        this.balance = memento.balance;
    }

    public static class Memento{
        private int balance;

        public Memento(int balance) {
            this.balance = balance;
        }

        public int getBalance() {
            return balance;
        }
    }

}
