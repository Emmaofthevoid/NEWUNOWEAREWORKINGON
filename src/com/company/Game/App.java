package com.company.Game;

import com.company.Cards.*;
import com.company.Players.HumanPlayer;
import com.company.Players.Player;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Scanner;

public class App {
    private Deck deck = new Deck();
    private ArrayList<Player> players = new ArrayList<Player>();
    private AblageStapel stapel = new AblageStapel();
    private final Scanner input;
    private final PrintStream output;
    private Player currentPlayer;
    private boolean exit = false;
    private int currentPlayerIndex;
    private Card playedCard;

    public App(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
    }


    // gameloop implementieren
    public void Run() {
        // alles was einmal stattfindet: handout(take card), namen eingeben,
        initialize();
        //karteAblegen();
        exit = false;
        //ganze spiel schleife
        playCard();
//            currentPlayer = nextPlayer();

    }

    //TODO: Schleife draus machen! Darf/Muss noch verändert werden --> Doppelschleife

    //TODO: Karten sollen aus der Hand UND aus dem Deck entfernt werden, wenn sie am Stapel landen - momentan
    //werden sie nämlich immer mehr...

    //karte muss ausgewählt werden, dann gespielt, entfernt von die handout und im stapel hingefügt
    //spielrunden(game), draw/remove card


    public void initialize() {
        deck.shuffle();
        addPlayer();
        for (Player sp : players) {
            handOut(sp);
        }

        currentPlayerIndex = (int) (Math.random() * (3)); // random index between 0 and 3
        currentPlayer = players.get(currentPlayerIndex);
        System.out.println("The first player is: " + currentPlayer.getName());

        stapel.ersteKarte(deck);
        if (stapel.obersteKarte().value == Value.SKIP) {
            nextPlayer();
        } else if (stapel.obersteKarte().value == Value.DRAWTWO) {
            currentPlayer.takeCard(deck.drawCard());
            currentPlayer.takeCard(deck.drawCard());
            currentPlayer = nextPlayer();
        } else if (stapel.obersteKarte().value == Value.COLOR) {
            changeColor(input);
            nextPlayer();

        } else if (stapel.obersteKarte().value == Value.PLUS4) {
            deck.add(stapel.removeFromStapel());
            deck.shuffle();
            stapel.ersteKarte(deck);
        }
    }

    public String readInput(Scanner eingabe) {
        System.out.println("Bitte Karte eingeben: ");
        String var = eingabe.nextLine();

        return var;
    }

    private void printState() {
        //welche karte gespielt wurde
        System.out.println("Das ist die Hand von player:  " + currentPlayer.getName());
        output.println(currentPlayer.printHand());
    }

    public void addPlayer() {
        String name;
        for (int j = 0; j < 4; j++) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter your name");

            name = scanner.next();

            System.out.println("Hallo, " + name);
            HumanPlayer sp1 = new HumanPlayer(name);
            players.add(sp1);
        }
    }

    public void handOut(Player spieler) {

        for (int i = 0; i < 7; i++) {
            spieler.takeCard(deck.drawCard());

        }
    }


    public void playCard() {
        // System.out.println("----------------------------------- this is play card");

        boolean exit = false;
        //TODO, if first card is draw 2, then draw 2
//        //TODO: verbessern
        currentPlayer.getHand().contains(playedCard);
        // playedCard.setCard(input.toString());

        exit = false;
        while (!exit) {
            printState();
            // countCards();
            System.out.println("Bitte Karte eingeben oder 'draw' schreiben: ");
            String cardInput = input.nextLine();
            cardInput.toLowerCase(Locale.ROOT);
            System.out.println("Ihre Eingabe war: " + cardInput);
            Card validCard = currentPlayer.checkIfCardIsInHandCards(cardInput);
            System.out.println("checkIfCardIsInHandCards: return value: " + validCard);

            Card card = deck.drawCard();

            if (cardInput.equals("DRAW".toLowerCase(Locale.ROOT))) {//Karte wird gezogen
                currentPlayer.takeCard(card);
                System.out.println("Du hast folgende Karte gezogen: " + card);
                if (!cardCanBePlayed(card, stapel.obersteKarte())) { //drawn card can't be played
                    currentPlayer.takeCard(card);
                    System.out.println("Die Karte wird der Hand hinzugefügt.");
                    currentPlayer = nextPlayer();
                    continue;
                }
                if (cardCanBePlayed(card, stapel.obersteKarte())) { // drawn card can be played
                    stapel.ablegen(card);
                    System.out.println("Karte wurde abgelegt. Oberste Karte: " + stapel.obersteKarte());
                }

                currentPlayer = nextPlayer();
                //TODO: "weiter", wenn Spieler keine passende Karte gezogen hat
                //currentPlayer = nextPlayer();
                continue;

            } else if (validCard == null) { //Karte existiert nicht
                System.out.println("Ihre Eingabe war falsch, bitte noch mal: " + currentPlayer.printHand() + "Erste Karte: " + stapel.obersteKarte());
                continue;

            } else { // Karte existiert
                if (cardCanBePlayed(validCard, stapel.obersteKarte())) {
                    //  System.out.println("old oberste karte : " + stapel.obersteKarte());
                    stapel.ablegen(validCard);
                    validCard = stapel.obersteKarte();
                    System.out.println("new oberste karte : " + validCard);
                    currentPlayer.removeCardFromHand(validCard.toString());

                    System.out.println("Hand des Spielers: ");
                    currentPlayer.printHand();
                    currentPlayer = nextPlayer();
                    System.out.println("Neuer Spieler: " + currentPlayer.getName() + " " + currentPlayer.printHand());
                } else {
                    // wenn falsche Karte gespielt wurde (Strafkarte und nächster Spieler)
                    currentPlayer.takeCard(deck.drawCard());
                    currentPlayer = nextPlayer();

                }
            }

            assert validCard != null;

            if (validCard.getValue() == Value.DRAWTWO) {
                card = deck.drawCard();
                currentPlayer.takeCard(card);
                currentPlayer.takeCard(card);

                continue;
            }

            //TODO: Challenging-Möglichkeit zum Anzweifeln der Karte VERBESSERN - geht noch nicht!
            if (validCard.getValue() == Value.PLUS4 || validCard.getValue() == Value.COLOR) {
                skipPlayer();
                skipPlayer();
                skipPlayer();

                System.out.println("Welche Farbe willst du, aldaaa?");

                changeColor(input);
                skipPlayer();
            }

            //TODO: ANZWEIFELN FUNKTIONIERT NICHT!!!!!!!!! ARGH FUCK THAT STUPID SHIT!!!!!
            if (validCard.getValue() == Value.PLUS4) {
                System.out.println("Willst du die Karte anzweifeln? 'Ja' / 'Nein'");
                cardInput = input.nextLine();
                if (cardInput.toLowerCase(Locale.ROOT).equals("ja")) {
                    if (!checkIfOtherCardCanBePlayed()) {
                        skipPlayer();
                        skipPlayer();
                        skipPlayer();
                        System.out.println("Es spielt: " + currentPlayer.getName());
                        for (int i = 0; i < 4; i++) {
                            currentPlayer.takeCard(deck.drawCard());
                            currentPlayer = nextPlayer();
                        }
                    } else {
                        skipPlayer();
                        skipPlayer();
                        skipPlayer();
                        System.out.println("Es spielt: " + currentPlayer.getName());
                        for (int i = 0; i < 6; i++) {
                            currentPlayer.takeCard(deck.drawCard());
                        }
                        currentPlayer = nextPlayer();
                    }
                    continue;
                }
                if (cardInput.toLowerCase(Locale.ROOT).equals("nein")) { //dieser Teil funktioniert, hurra!
                    for (int i = 0; i < 4; i++) {
                        currentPlayer.takeCard(deck.drawCard());
                    }
                }
                continue;
            }

            // TODO schleife implementieren
            if (validCard.value == Value.REVERSE) {
                System.out.println("METHODE REVERSE WIRD AUFGERUFEN");

                reverseDirection();
            }
            if (validCard.value == Value.SKIP) {
                System.out.println("METHODE SKIP WIRD AUFGERUFEN");
                skipPlayer();
            }
            if (validCard.value == Value.COLOR && validCard.type == Type.WILD) {
                changeColor(input);
            }
        }
    }


    public void reverseDirection() {
        currentPlayer = nextPlayer();
        currentPlayer = nextPlayer();
        System.out.println(currentPlayer.getName() + currentPlayer.printHand());
    }

    public void
    changeColor(Scanner input) {
        String cardInput = input.nextLine();
        if (cardInput.equals("BLUE".toLowerCase(Locale.ROOT))) {
            stapel.obersteKarte().setType(Type.BLUE);

        } else if (cardInput.equals("YELLOW".toLowerCase(Locale.ROOT))) {
            stapel.obersteKarte().setType(Type.YELLOW);

        } else if (cardInput.equals("RED".toLowerCase(Locale.ROOT))) {
            stapel.obersteKarte().setType(Type.RED);

        } else if (cardInput.equals("GREEN".toLowerCase(Locale.ROOT))) {
            stapel.obersteKarte().setType(Type.GREEN);

        } else {
            System.out.println("Bitte gültige Farbe wählen, du Ei!");
            changeColor(input);
        }
    }

    public void skipPlayer() {
        currentPlayer = nextPlayer();
        System.out.println("SKIP PLAYER : " + currentPlayer.getName() + currentPlayer.printHand());
    }

    public Player nextPlayer() {
        //  System.out.println("Momentan spielt: " + currentPlayerIndex + " NAME DES SPIELERS : " + currentPlayer.getName());
        currentPlayerIndex++;
        if (currentPlayerIndex > 3) {
            currentPlayerIndex = 0;
        }

        return players.get(currentPlayerIndex);
    }

//    public void countCards() {
//        System.out.println(deck.deck.size() + stapel.ablageStapel.size() + players.get(0).getHand().size() + players.get(1).getHand().size() + players.get(2).getHand().size() + players.get(3).getHand().size());
//    }

    public boolean cardCanBePlayed(Card handCard, Card ablageStapelCard) {
        //  System.out.println("FUNKTION CARD CAN BE PLAYED");
        System.out.println("Ihre Eingabe war: " + handCard.toString());
        //handCard.setCard(input.toString());

        if (handCard.getType() == ablageStapelCard.getType()) { //z.B. beide sind rote Karten
            //  System.out.println("Type of hand card: " + handCard.getType());
            System.out.println("Die oberste Karte ist: " + handCard);
            return true;
        } else if (handCard.getValue() == ablageStapelCard.getValue()) { //z.B. beide haben den Wert 3
            System.out.println("Die oberste Karte ist: " + handCard);
            return true;
        } else if (handCard.type == Type.WILD) {
            return true;
        }
//        else if(input.nextLine().equals("draw")){
//            System.out.println("Du möchtest eine Karte ziehen " + stapel.obersteKarte());
//            return false;
//        }
        else if (handCard.getValue() != ablageStapelCard.getValue() && handCard.getType() != ablageStapelCard.getType()) {
            System.out.println("Karte kann nicht gespielt werden, bleibt auf der Hand: " + handCard);
            return false;
        }
        //TODO: Strafkarte wird nicht mehr aktiviert - sollte sie aber!!!
        System.out.println("Karte kann nicht gespielt werden. Du hast es verkackt, strafkarte for your body. Oberste Karte : " + stapel.obersteKarte());
        return false;

    }

    public boolean checkIfOtherCardCanBePlayed() {
        boolean otherCardCouldBePlayed = false;
        for (Card c : currentPlayer.getHand()) {

            if (c.value.equals(stapel.vorletzteKarte().value) || c.type.equals(stapel.vorletzteKarte().type)) {
                otherCardCouldBePlayed = true;
            }
        }
        if (otherCardCouldBePlayed == true) {
            System.out.println("Der letzte Spieler hat die Karte zu Unrecht gespielt und muss 4 Karten heben!");
            return true;
        } else
            System.out.println("Falsche Anschuldigungen! Du musst 6 Karten heben!");
            return false;
    }
}

