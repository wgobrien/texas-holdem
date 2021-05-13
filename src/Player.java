/*
 * William O'Brien
 * Player.java
 */

import java.util.ArrayList;

public class Player {
    
     
    private ArrayList<Card> hand; // the player's cards
    private double bankroll;
    private double bet;
        
    public Player(){        
        // create a player here
        bankroll = 10;
        hand = new ArrayList<Card>();
        bet = 0;
    }

    public void addCard(Card c){
        // add the card c to the player's hand
        hand.add(c);
    }

    public void removeCard(Card c){
        // remove the card c from the player's hand
        hand.remove(c);
    }
        
    public void bets(double amt){
        // player makes a bet
        this.bet = amt;
        bankroll -= amt;
    }

    public void winnings(double odds){
        //  adjust bankroll if player wins
        // player wins n times as many coins n betted
        bankroll += odds*bet;
    }

    public double getBankroll(){
        // return current balance of bankroll
        return bankroll;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

}
