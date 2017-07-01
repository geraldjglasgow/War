/**
 * Created by SSLGhost on 6/29/17.
 */
public class Card {
    private char suit;
    private int value;

    public Card(char suit, int value){
        this.suit = suit;
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public char getSuit(){
        return this.suit;
    }
}
