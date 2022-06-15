//package com.company.Cards;
//
//import com.company.Cards.Card;
//
//public class Logik {
//    public static boolean cardCanBePlayed(Card handCard, Card ablageStapelCard) {
//
//        System.out.println("Ihre Eingabe war: " + handCard);
//
//        if (handCard.getValue() == ablageStapelCard.getValue()) {
//            if (handCard.getValue().equals("WILD")) {
//                if (handCard.getType() == ablageStapelCard.getType()) {
//                    return true;
//                }
//            }
//            return true;
//        } else if (handCard.getType() == ablageStapelCard.getType()) {
//            return true;
//        }
//        return false;
//    }
//
//}

//        if (!deck.containsCard(card)) {
//            System.out.println("Bitte gültige Karte eingeben.");
//            currentPlayer.removeCardFromHand(handCard);
//        } else {
//
//        }
//        if (stapel.obersteKarte() != card) {
//            if (card.getValue() == stapel.obersteKarte().getValue()) {
//                stapel.ablegen(card);
//            } else if (card.getType() == stapel.obersteKarte().getType()) {
//                stapel.ablegen(card);
//            } else {
//                System.out.println("Not allowed to play.");
//                //TODO: heben hier implementieren
//            }
//        }
//        if (card.getType() == stapel.obersteKarte().getType() && card.getValue() == stapel.obersteKarte().getValue()) {
//            stapel.ablegen(card);
//        }
//
//        System.out.println("Oberste Karte: " + stapel.obersteKarte());
//
//
//        System.out.println("You chose card " + card);
//        stapel.ablegen(card);
//        stapel.obersteKarte();
//
//        return false;
//    }



