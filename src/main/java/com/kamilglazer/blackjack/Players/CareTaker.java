package com.kamilglazer.blackjack.Players;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private Player player;
    private List<Player.Memento> history = new ArrayList<>();

    public CareTaker(Player player) {
        this.player = player;
    }

    public void save(){
        history.add(player.save());
    }

    public List<String> getBalanceHistory() {
        List<String> balances = new ArrayList<>();
        for (Player.Memento memento : history) {
            balances.add("" + memento.getBalance());
        }
        return balances;
    }

}
