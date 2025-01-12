package com.kamilglazer.blackjack;

import com.kamilglazer.blackjack.Players.Dealer;
import com.kamilglazer.blackjack.Players.Player;
import com.kamilglazer.blackjack.animations.Animation;
import com.kamilglazer.blackjack.cards.Card;
import com.kamilglazer.blackjack.cards.DeckOfCards;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Button nextCardButton;

    @FXML
    private Button standButton;

    @FXML
    private Button clearBet;

    @FXML
    private Button placeBet;

    @FXML Button resetButton;

    @FXML
    private ImageView deckImageView;

    @FXML
    private ImageView activeCardImageView;

    @FXML
    private Text playerHandValueText;

    @FXML
    private Text dealerHandValueText;

    @FXML
    private Text resultText;

    @FXML
    private ImageView playerCard1, playerCard2, playerCard3,playerCard4, playerCard5, playerCard6;
    @FXML
    private ImageView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5, dealerCard6;

    @FXML
    private ImageView chip10;
    @FXML
    private ImageView chip25;
    @FXML
    private ImageView chip50;
    @FXML
    private ImageView chip100;
    @FXML
    private Text betText;

    @FXML
    private Text balance;

    private DeckOfCards deck;
    private Player player;
    private Dealer dealer;
    private Animation animation;
    private int currentBet;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deck = DeckOfCards.getInstance();
        player = new Player(1000);
        dealer = new Dealer(false);


        this.animation = new Animation(dealer,dealerCard1, dealerCard3, dealerCard4, dealerCard5, dealerCard6);
        startNewGame();
    }




    public void startNewGame(){

            resultText.setText(" ");
            player.clearHand();
            dealer.clearHand();
            deck.shuffle();
            dealer.setFirstCardRevealed(false);

            player.addCardToHand(deck.dealTopCard());
            dealer.addCardToHand(deck.dealTopCard());
            player.addCardToHand(deck.dealTopCard());
            dealer.addCardToHand(deck.dealTopCard());

            dealerCard1.setImage(deck.getBackOfCardImage());
            dealerCard2.setImage(deck.getBackOfCardImage());
            playerCard1.setImage(deck.getBackOfCardImage());
            playerCard2.setImage(deck.getBackOfCardImage());

            resetButton.setDisable(true);
            nextCardButton.setDisable(true);
            standButton.setDisable(true);
            balance.setText("" + player.getBalance());
    }

    @FXML
    public void nextCardButtonPushed(){
        player.addCardToHand(deck.dealTopCard());
        updateUI();

        Card lastCard = player.getHand().get(player.getHand().size()-1);
        ImageView nextCardSlot = getNextCardSlot(player.getHand().size() - 1);

        animation.animateCard(nextCardSlot,lastCard.getImage());

        if(player.isBusted()){
            animation.revealDealerCard();
            updateUI();
            resultText.setText("You Busted! Dealer Wins.");
            disableGameControls();
        }
    }

    @FXML
    public void standButtonPushed(){
        while(dealer.shouldHit()){
            dealer.addCardToHand(deck.dealTopCard());
        }

        animation.revealDealerCard();
        updateUI();
        determineWinner();
    }

    @FXML
    public void resetButtonPushed(){
        currentBet=0;
        betText.setText("Current bet: 0");
        placeBet.setDisable(false);
        clearBet.setDisable(false);
        playerHandValueText.setText("0");
        dealerHandValueText.setText("0");
        clearCardsImage();
        startNewGame();
    }

    @FXML
    public void placeBetButtonClicked(){

        if(currentBet < 50){
            resultText.setText("Minimum bet is 20!");
            return;
        }

        if(player.placeBet(currentBet)){
            placeBet.setDisable(true);
            clearBet.setDisable(true);
            nextCardButton.setDisable(false);
            standButton.setDisable(false);
            balance.setText(""+player.getBalance());
            animation.flipCard(playerCard1,deck.getBackOfCardImage(),player.getHand().get(0).getImage());
            animation.flipCard(playerCard2,deck.getBackOfCardImage(),player.getHand().get(1).getImage());
            animation.flipCard(dealerCard2,deck.getBackOfCardImage(),dealer.getHand().get(1).getImage());
            updateUI();
        }else{
            resultText.setText("You dont have enough money!");
        }

    }


    @FXML
    public void onChipClicked(javafx.scene.input.MouseEvent event) {
        if (event.getSource() == chip10) {
            currentBet += 10;
        } else if (event.getSource() == chip25) {
            currentBet += 25;
        } else if (event.getSource() == chip50) {
            currentBet += 50;
        } else if (event.getSource() == chip100) {
            currentBet += 100;
        }

        betText.setText("Current bet: " + currentBet);
    }

    @FXML
    public void clearBetButtonClicked(){
        currentBet = 0;
        betText.setText("Current bet: "+ currentBet);
    }


    private void updateUI(){
        if(dealer.isFirstCardRevealed()){
            playerHandValueText.setText("" + player.calculateHandValue());
            dealerHandValueText.setText("" + dealer.calculateActualValue());
        }else{
            playerHandValueText.setText("" + player.calculateHandValue());
            dealerHandValueText.setText("" + dealer.calculateHandValue());
        }
    }

    private void determineWinner(){
        if (dealer.isBusted()) {
            resultText.setText("Dealer Busted! You Win!");
            player.addMoney(currentBet*2);
        } else if (player.calculateHandValue() > dealer.calculateActualValue()) {
            resultText.setText("You Win!");
            player.addMoney(currentBet*2);
        } else if (player.calculateHandValue() < dealer.calculateActualValue()) {
            resultText.setText("Dealer Wins.");
        } else {
            resultText.setText("It's a Tie.");
            player.addMoney(currentBet);
        }
        disableGameControls();
    }

    private void disableGameControls(){
        dealer.setFirstCardRevealed(false);
        nextCardButton.setDisable(true);
        standButton.setDisable(true);
        resetButton.setDisable(false);
    }


    private ImageView getNextCardSlot(int handSize) {
        return switch (handSize) {
            case 1 -> playerCard2;
            case 2 -> playerCard3;
            case 3 -> playerCard4;
            case 4 -> playerCard5;
            case 5 -> playerCard6;
            default -> null;
        };
    }


    private void clearCardsImage() {
        for (int i = 0; i < 6; i++) {
            getPlayerCardSlot(i).setImage(null);
        }

        for (int i = 0; i < 6; i++) {
            getDealerCardSlot(i).setImage(null);
        }
    }

    private ImageView getPlayerCardSlot(int index) {
        return switch (index) {
            case 0 -> playerCard1;
            case 1 -> playerCard2;
            case 2 -> playerCard3;
            case 3 -> playerCard4;
            case 4 -> playerCard5;
            case 5 -> playerCard6;
            default -> null;
        };
    }

    private ImageView getDealerCardSlot(int index) {
        return switch (index) {
            case 0 -> dealerCard1;
            case 1 -> dealerCard2;
            case 2 -> dealerCard3;
            case 3 -> dealerCard4;
            case 4 -> dealerCard5;
            case 5 -> dealerCard6;
            default -> null;
        };
    }


    @FXML
    private void onMouseEntered(javafx.scene.input.MouseEvent event) {
        ImageView chip = (ImageView) event.getSource();
        scaleChip(chip, true);
    }

    @FXML
    private void onMouseExited(javafx.scene.input.MouseEvent event) {
        ImageView chip = (ImageView) event.getSource();
        scaleChip(chip, false);
    }

    private void scaleChip(ImageView chip, boolean enlarge) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), chip);
        scaleTransition.setToX(enlarge ? 1.2 : 1.0);
        scaleTransition.setToY(enlarge ? 1.2 : 1.0);
        scaleTransition.play();
    }
}

