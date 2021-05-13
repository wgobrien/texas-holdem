/*
 * William O'Brien
 * Card.java
 */

public class Card implements Comparable<Card>
{
    
    private int suit; // use integers 1-4 to encode the suit
    private int rank; // use integers 1-13 to encode the rank
    
    public Card(int s, int r){
        //make a card with suit s and value v
        suit = s;
        rank = r;
    }
    
    public int compareTo(Card c){
        // use this method to compare cards so they may be easily sorted
        if (this.rank < c.getRank())
        {
            return 1;
        }
        return 0;
    }
    
    public int getRank(){
       return this.rank;
    }
    
    public int getSuit(){
       return this.suit;
    }
    
    public String toString(){
        // use this method to easily print a Card object
        String rankString = "";
        if (rank == 1) {
            rankString = "Ace";
        }
        else if (rank == 11) {
            rankString = "Jack";
        }
        else if (rank == 12) {
            rankString = "Queen";
        }
        else if (rank == 13) {
            rankString = "King";
        }
        else {
            rankString = Integer.toString(rank);
        }
        
        String suitString = "";
        if (suit == 1) {
            suitString = "Clubs";
        }
        else if (suit == 2) {
            suitString = "Diamonds";
        }
        else if (suit == 3) {
            suitString = "Hearts";
        }
        else {
            suitString = "Spades";
        }
        
        return rankString + " of " + suitString;
    }
}

