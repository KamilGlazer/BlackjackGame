package com.kamilglazer.blackjack.animations;

import com.kamilglazer.blackjack.Players.Dealer;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation {

    private Dealer dealer;
    @FXML
    private ImageView dealerCard1, dealerCard3, dealerCard4, dealerCard5, dealerCard6;


    public Animation(Dealer dealer,ImageView dealerCard1,ImageView dealerCard3, ImageView dealerCard4, ImageView dealerCard5, ImageView dealerCard6) {
        this.dealer = dealer;
        this.dealerCard1 = dealerCard1;
        this.dealerCard3 = dealerCard3;
        this.dealerCard4 = dealerCard4;
        this.dealerCard5 = dealerCard5;
        this.dealerCard6 = dealerCard6;
    }

    @FXML
    public void revealDealerCard() {
        dealer.setFirstCardRevealed(true);
        Image backImage = dealerCard1.getImage();
        Image frontImage = dealer.getHand().get(0).getImage();

        flipCard(dealerCard1, backImage, frontImage);

        double delay = 0.5;
        for (int i = 2; i < dealer.getHand().size(); i++) {
            int index = i;
            PauseTransition delayTransition = new PauseTransition(Duration.seconds(delay));
            delayTransition.setOnFinished(event -> {
                ImageView nextCardSlot = getDealerCardSlot(index);
                if (nextCardSlot != null) {
                    animateCard(nextCardSlot, dealer.getHand().get(index).getImage());
                }
            });
            delayTransition.play();
        }
    }

    private ImageView getDealerCardSlot(int handSize) {
        return switch (handSize) {
            case 2 -> dealerCard3;
            case 3 -> dealerCard4;
            case 4 -> dealerCard5;
            case 5 -> dealerCard6;
            default -> null;
        };
    }

    public void animateCard(ImageView cardImageView, Image image) {
        if (image != null) {
            cardImageView.setImage(image);


            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.6), cardImageView); // Zwiększono czas
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();


            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), cardImageView); // Zwiększono czas
            translateTransition.setFromX(-50);
            translateTransition.setFromY(-50);
            translateTransition.setToX(0);
            translateTransition.setToY(0);
            translateTransition.play();
        } else {
            cardImageView.setImage(null);
        }
    }

    private void flipCard(ImageView cardImageView, Image backImage, Image frontImage) {
        ScaleTransition hideCard = new ScaleTransition(Duration.seconds(0.3), cardImageView);
        hideCard.setFromX(1);
        hideCard.setToX(0);

        hideCard.setOnFinished(event -> {
            cardImageView.setImage(cardImageView.getImage().equals(backImage) ? frontImage : backImage);
            ScaleTransition showCard = new ScaleTransition(Duration.seconds(0.3), cardImageView);
            showCard.setFromX(0);
            showCard.setToX(1);
            showCard.play();
        });

        hideCard.play();
    }


}
