package com.kamilglazer.blackjack;

import com.kamilglazer.blackjack.Players.Dealer;
import com.kamilglazer.blackjack.Players.Player;
import com.kamilglazer.blackjack.animations.Animation;
import com.kamilglazer.blackjack.cards.Card;
import com.kamilglazer.blackjack.cards.DeckOfCards;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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

    private DeckOfCards deck;
    private Player player;
    private Dealer dealer;
    private Animation animation;
    private int currentBet;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deck = DeckOfCards.getInstance();
        player = new Player(100);
        dealer = new Dealer(false);
        this.animation = new Animation(dealer,dealerCard1, dealerCard3, dealerCard4, dealerCard5, dealerCard6);
        startNewGame();
    }

    public void startNewGame(){
        if(player.placeBet(currentBet)){
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

            animation.animateCard(dealerCard1,deck.getBackOfCardImage());;

            resetButton.setDisable(true);
            updateUI();
        }else{
            resultText.setText("Not enough money to place the bet.");
        }

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
        enableGameControls();
        startNewGame();
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
            playerHandValueText.setText("Player Hand: " + player.calculateHandValue());
            dealerHandValueText.setText("Dealer Hand: " + dealer.calculateActualValue());
            return;
        }else{
            playerHandValueText.setText("Player Hand: " + player.calculateHandValue());
            dealerHandValueText.setText("Dealer Hand: " + dealer.calculateHandValue());
        }

        updateCardImages();
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
        }
        disableGameControls();
    }

    private void disableGameControls(){
        dealer.setFirstCardRevealed(false);
        nextCardButton.setDisable(true);
        standButton.setDisable(true);
        resetButton.setDisable(false);
    }

    private void enableGameControls(){
        nextCardButton.setDisable(false);
        standButton.setDisable(false);
    }

    private void updateCardImages() {
        playerCard1.setImage(!player.getHand().isEmpty() ? player.getHand().get(0).getImage() : null);
        playerCard2.setImage(player.getHand().size() > 1 ? player.getHand().get(1).getImage() : null);
        playerCard3.setImage(player.getHand().size() > 2 ? player.getHand().get(2).getImage() : null);
        playerCard4.setImage(player.getHand().size() > 3 ? player.getHand().get(3).getImage() : null);
        playerCard5.setImage(player.getHand().size() > 4 ? player.getHand().get(4).getImage() : null);
        playerCard6.setImage(player.getHand().size() > 5 ? player.getHand().get(5).getImage() : null);

        dealerCard2.setImage(dealer.getHand().size() > 1 ? dealer.getHand().get(1).getImage() : null);
        dealerCard3.setImage(dealer.getHand().size() > 2 ? dealer.getHand().get(2).getImage() : null);
        dealerCard4.setImage(dealer.getHand().size() > 3 ? dealer.getHand().get(3).getImage() : null);
        dealerCard5.setImage(dealer.getHand().size() > 4 ? dealer.getHand().get(4).getImage() : null);
        dealerCard6.setImage(dealer.getHand().size() > 5 ? dealer.getHand().get(5).getImage() : null);
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

