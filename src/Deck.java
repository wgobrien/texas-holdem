/*
 * William O'Brien
 * Deck.java
 */

public class Deck {
    
    private Card[] cards;
    private int top; // the index of the top of the deck
    
    public Deck()
    {
        // 52 card deck
        cards = new Card[52];
        top = 0;
        
        int index = 0;
        for( int suit = 1; suit <= 4; suit++){
            for( int rank = 1; rank <= 13; rank++){
                //int index = (suit-1)*13+rank;
                cards[index] = new Card(suit, rank);
                index++;
            }
        }
        
    }
    
    public void shuffle()
    {
        for(int x = 0; x < 4; x++){
            for (int i = 0; i < 52; i++) {
                int random = (int)(Math.random()*52);
                Card temp = cards[random];
                cards[random] = cards[i];
                cards[i] = temp;   
            }
        }
        // shufle the deck
    }
    
    public Card deal() {
        // deal the top card in the deck
        if (top == 52) {
            top = 0;
            shuffle();
        }
        return cards[top++];
    }
}
