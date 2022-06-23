package com.company.Players;

import com.company.Cards.Card;

import java.util.ArrayList;

public abstract class Player {
    private String name;
    private int points;
    private ArrayList<Card> hand;
    private boolean UNOStatus;

    public Player(String name) {
        this.name = name;
        this.points = 0;
        this.hand = new ArrayList<>() ;
        this.UNOStatus = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void takeCard(Card card) {
        hand.add(card);
    }

    public String printHand(){
        return hand.toString();
    }

    public Card removeCardFromHand(String cardInput) {

        for (int i = 0; i < getHand().size(); i++) {
            Card card = getHand().get(i);
            if (card.getCard().equals(cardInput)) { // checkt er nicht!!!!

                getHand().remove(card);

                return card;
            }
        }
        return null;
    }

    public Card checkIfCardIsInHandCards(String input){
        for (Card card : hand){
            //System.out.println("DEBUG: checkIfCardIsInHandCards, Input: " + input + ", aktuelle Karte: " + card.getCard());
            if(card.getCard().equals(input)){
                //System.out.println("DEBUG: checkIfCardIsInHandCards: found valid card: " + card);
                return card;
            }
        }
        //System.out.println("DEBUG: checkIfCardIsInHandCards: did not find card, returning null");
        return null;
    }

}

