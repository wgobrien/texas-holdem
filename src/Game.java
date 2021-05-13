/*
 * William O'Brien
 * Game.java
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    
    private Player p;
    private Deck cards;
    private int[] suitCount = new int[4];
    private int[] rankCount = new int[13];
    private ArrayList<Card> testHandList;
    private boolean check;
    
    public Game(String[] testHand){

        // encoding for cards
        // c = clubs
        // d = diamonds
        // h = hearts
        // s = spades
        // 1-13 correspond to ace-king
        // example: s1 = ace of spades
        // example: testhand = {s1, s13, s12, s11, s10} = royal flush
        
        p = new Player();
        cards = new Deck();
        
        testHandList = new ArrayList<Card>();
         
        for (int x = 0; x < 5; x++) {
            int rank = Integer.parseInt(testHand[x].substring(1));
            
            int suit = 0;
            String suitString = testHand[x].substring(0,1);
            if (suitString.equals("c")) {
                suit = 1;
            }
            else if (suitString.equals("d")) {
                suit = 2;
            }
            else if (suitString.equals("h")) {
                suit = 3;
            }
            else if (suitString.equals("s")) {
                suit = 4;
            }
            
            Card testCard = new Card(suit, rank);
            testHandList.add(testCard);
        }
        check = true;
    }
    
    public Game(){
        // No-argument constructor is to actually play a normal game
        p = new Player();
        cards = new Deck();
        check = false;
    }
    
    public void play(){
        // play the game
        Scanner input = new Scanner(System.in);
        
       cards.shuffle();
        
        System.out.println("You have " + p.getBankroll() + " tokens in your bankroll. Would you like to play (y/n)? ");
        String reply = input.nextLine();
        while (p.getBankroll() > 0 && !reply.equals("n")) {
            System.out.println("How many tokens would you like to bet (1-5)? ");
            int bet = input.nextInt();
            while (bet < 1 || bet > 5) {
                System.out.println("Not a valid bet! Enter a number 1-5: ");
                bet = input.nextInt();
            }
            p.bets(bet);
            
            if (check) {
                for (int x = 0; x < 5; x++) {
                    p.addCard(testHandList.get(x));
                }
            }
            else {
                buildHand();
            }
            System.out.println("Here are your cards: \n" + revealHand(p.getHand()));
            
            System.out.println("Reject cards? (all/some/none) ");
            String reject = input.next();
            if (reject.equals("all")) {
                clearHand();
                buildHand();
                System.out.println("Your new hand: \n" + revealHand(p.getHand()));
            }
            else if (reject.equals("some")) {
                System.out.println("Which card would you like to remove? (Enter a number 1-5 correlating to card, 0 to stop removing cards)");
                int replyCard = input.nextInt();
                if (replyCard != 0){
                        p.getHand().set(replyCard-1,cards.deal());
                }
                
                int count = 1;
                while(replyCard != 0 && count < 4){
                    System.out.println("Which card would you like to remove? (Enter a number 1-5 correlating to card, 0 to stop removing cards)");
                    replyCard = input.nextInt();
                    if (replyCard != 0){
                        p.getHand().set(replyCard-1,cards.deal());
                        count++;
                    }
                }
                
                System.out.println("Your new hand: \n" + revealHand(p.getHand()));
            }
            
            //counter(p.getHand());
            System.out.println( "You have: " + checkHand(p.getHand()) + "!");
            
            if (p.getBankroll() > 0) {
                System.out.println("\nYou now have " + p.getBankroll() + " tokens in your bankroll. Would you like to keep playing (y/n)? ");
                reply = input.next();
            }
            else {
                System.out.print("\nOut of tokens! ");
            }
            //reply = input.nextLine();
            clearHand();
            clearCount();
        }
        System.out.println("Thanks for playing!");
    }
    
    public String checkHand(ArrayList<Card> hand){
        counter(p.getHand());
        String checkHand = "";
        if (royalFlush(hand)) {
            p.winnings(250);
            checkHand = "Royal Flush";
        }
        else if (straightFlush(hand)) {
            p.winnings(50);
            checkHand = "Straight flush";
        }
        else if (fourKind(hand)) {
            p.winnings(25);
            checkHand = "Four of a kind";
        }
        else if (fullHouse(hand)) {
            p.winnings(6);
            checkHand = "Full house";
        }
        else if (flush(hand)) {
            p.winnings(5);
            checkHand = "Flush";
        }
        else if (straight(hand)) {
            p.winnings(4);
            checkHand = "Straight";
        }
        else if (threeKind(hand)) {
            p.winnings(3);
            checkHand = "Three of a kind";
        }
        else if (twoPair(hand)) {
            p.winnings(2);
            checkHand = "Two pair";
        }
        else if (onePair(hand)) {
            p.winnings(1);
            checkHand = "One pair";
        }
        else {
            p.winnings(0);
            checkHand = "No pair";
        }
        return checkHand;
    }
    
    public String revealHand(ArrayList<Card> hand){
        // shows your current hand
        String revealHand = "";
        for (int i = 0; i < hand.size() - 1; i++) {
            revealHand += hand.get(i).toString() + ", ";
        }
        revealHand += hand.get(hand.size() - 1);
        return revealHand;
    }
    
    public void buildHand(){
        // builds a hand
        for (int x = 0; x < 5; x++) {
            p.addCard(cards.deal());
        }
    }
    
    public void clearHand(){
        // clears the hand
        for (int x = p.getHand().size() - 1; x >= 0; x--) {
            p.removeCard(p.getHand().get(x));
        }
    }
    
    public void counter(ArrayList<Card> hand) {
        // creates two arrays to keep track of how many of each suit and rank appear in your hand
        for (int x = 0; x < 5; x++) {
            suitCount[hand.get(x).getSuit() - 1]++;
        }
        for (int x = 0; x < 5; x++) {
            rankCount[hand.get(x).getRank() - 1]++;
        }
    }
    
    public void clearCount() {
        //clears the count arrays
        for (int x = 0; x < 4; x++) {
            suitCount[x] = 0;
        }
        for (int x = 0; x < 13; x++) {
            rankCount[x] = 0;
        }
    }
    
    public boolean onePair(ArrayList<Card> hand) {
        for (int x = 0; x < 13; x++){
            if (rankCount[x] == 2) {
                return true;
            }
        }
        return false;
    }
    
    public boolean twoPair(ArrayList<Card> hand) { 
        int count = 0;
        for (int x = 0; x < 13; x++){
            if (rankCount[x] == 2) {
                count++;
            }
        }
        if (count == 2) {
            return true;
        }
        return false;
    }
    
    public boolean threeKind(ArrayList<Card> hand) {
        for (int x = 0; x < 13; x++){
            if (rankCount[x] == 3) {
                return true;
            }
        }
        return false;
    }
    
    public boolean straight(ArrayList<Card> hand) {
        int count = 0;
        for (int x = 0; x < 9; x++){
            if (rankCount[x] == 1 && rankCount[x+1] == 1 && rankCount[x+2] == 1 && rankCount[x+3] == 1 && rankCount[x+4] == 1) {
                return true;
            }
        }
        return false;
    }
    
    public boolean flush(ArrayList<Card> hand) {
        for (int x = 0; x < 4; x++){
            if (suitCount[x] == 5) {
                return true;
            }
        }
        return false;
    }
    
    public boolean fullHouse(ArrayList<Card> hand) {
        int threeCount = 0;
        int twoCount = 0;
        for (int x = 0; x < 13; x++){
            if (rankCount[x] == 3) {
                threeCount++;
            }
            if (rankCount[x] == 2) {
                twoCount++;
            }
        }
        if (threeCount == 1 && twoCount == 1) {
            return true;
        }
        return false;
    }

    public boolean fourKind(ArrayList<Card> hand) {
        for (int x = 0; x < 13; x++){
            if (rankCount[x] == 4) {
                return true;
            }
        }
        return false;
    }
            
    public boolean straightFlush(ArrayList<Card> hand) {
        return straight(hand) && flush(hand);
    }
            
    public boolean royalFlush(ArrayList<Card> hand) {
        if (flush(hand)) {
            if ((rankCount[0] == 1) && (rankCount[12] == 1) && (rankCount[11] == 1) && (rankCount[10] == 1) && (rankCount[9] == 1)) {
                     return true;
            }
        }
        return false;
    }
          
}

