package com.company.Cards;

import java.util.ArrayList;

public class AblageStapel {
    public ArrayList<Card> ablageStapel;


    public AblageStapel() {
        ablageStapel = new ArrayList<>();
    }

    public ArrayList<Card> getAblageStapel() {
        return ablageStapel;
    }

    //eine methode damit eine karte schon da ist, und eine um karten hinzufügen.

    public void ersteKarte(Deck deck) {
        deck.drawCard();
        ablageStapel.add(deck.drawCard());
        System.out.println("Die erste Karte ist : " + ablageStapel);
    }

    public Card obersteKarte(){
       // System.out.println("DEBUG OUTPUT: Hello, funktion oberstekarte on da road");
        Card card = ablageStapel.get(ablageStapel.size() - 1);

        return card;
    }

    public void ablegen(Card karte) {
        ablageStapel.add(karte);
    }
}
