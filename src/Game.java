/**
 * Created by Gerald Glasgow on 6/29/17.
 */
import java.util.LinkedList;
import java.util.Random;


public class Game {
    private LinkedList<Card> list = new LinkedList();       // original deck to deal from
    private LinkedList<Card> p1 = new LinkedList();         // player 1
    private LinkedList<Card> p2 = new LinkedList();         // player 2
    private int turns;                                      // number of turns it took to win
    private int totalturns;                                 // keeping track of total turns taken over many games
    private int winner;                                     // used to keep track of the winner every game
    private int p1winner=0;                                 // keeping track of how often player 1 wins
    private int p2winner=0;                                 // keeping track of how often player 2 wins
    private Random rnd = new Random();                      // for generating random numbers

    /**
     * Constructor to start the game by building the initial deck of cards, shuffling them,
     * dealing the deck out between 2 players, and finally playing WAR!
     */
    public Game(int n){
        for(int k=0;k<n;k++) {
            turns = 0;
            p1.clear();
            p2.clear();
            list.clear();
            buildDeck();
            shuffle();
            deal();
            play();
            //System.out.println("In " + turns + " turns the winner was" + " Player " + winner + ".");
        }
    }
    /* LOOP TO PLAY GAME AND WAR FUNCTION */
    public void play(){
        while(p1.size() != 0 && p2.size() != 0) {
            if (p1.peekFirst().getValue() > p2.peekFirst().getValue()) {         // player 1 wins hand
                player1Wins();
            } else if (p1.peekFirst().getValue() < p2.peekFirst().getValue()) {  // player 2 wins hand
                player2Wins();
            } else {                                                             // else cards are equal and war must happen
                war();
            }
            turns++;
        } // end while
        if (p1.size() == 0) {
            p2winner++;      // keeping track of how many times player 2 wins
            winner = 2;      // for printing out winner of individual games
        } else {
            p1winner++;      // keeping track of how many times player 1 wins
            winner = 1;      // for printing out winner of individual games
        }
        totalturns += turns;
    } // end play

    /* different types of war styles */
    public void war(){
        boolean winner = false;
        winner = isWinner(4);

        while(!winner){
            int i=4;
            int count = 5;
            while(!winner){
                //System.out.println(i + " " + p1.get(i-1).getValue() + " " + p2.get(i-1).getValue()); //for checking war outcomes
                if (p1.get(i-1).getValue() > p2.get(i-1).getValue()) {
                    for(int k=0;k<4;k++){
                        player1Wins();
                    }
                    winner = true;
                } else if(p1.get(i-1).getValue() < p2.get(i-1).getValue()){
                    for(int k=0;k<4;k++){
                        player2Wins();
                    }
                    winner = true;
                } else {
                    if (i == 1) {
                        while (!winner) {
                            winner = isWinner(count);
                            int max = Math.min(p1.size(), p2.size());
                            int b = 5;
                            while (b < max && !winner) {
                                winner = isWinner(count);
                                if (p1.get(b - 1).getValue() > p2.get(b - 1).getValue()) {
                                    for (int k = 0; k < count-1; k++) {
                                        if(p2.size() !=0){
                                            player1Wins();
                                        }
                                    }
                                    winner = true;
                                }
                                if (p1.get(b - 1).getValue() < p2.get(b - 1).getValue()) {
                                    for (int k = 0; k < count-1; k++) {
                                        if(p1.size() != 0) {
                                            player2Wins();
                                        }
                                    }
                                    winner = true;
                                }
                                count++;
                            } // end while
                        }
                    }
                }
                i--;
            }
        }
    }

    public void war1(){
        boolean winner = false;
        int n = 4;
        winner = isWinner(n);
        while(n<100 && !winner){
            winner = isWinner(n);
            if (!winner && p1.get(n-1).getValue() > p2.get(n-1).getValue() ) {
                for(int k=0;k<n;k++){
                    player1Wins();
                }
                winner = true;
            } else if(!winner && p1.get(n-1).getValue() < p2.get(n-1).getValue()){
                for(int k=0;k<n;k++){
                    player2Wins();
                }
                winner = true;
            }
            n+=3;
        }
    }

    /* WINNER GETS CARDS */
    public void player1Wins(){
        int n = rnd.nextInt(2);
        if(n == 1) {
            p1.addLast(p1.removeFirst());
            p1.addLast(p2.removeFirst());
        } else {
            p1.addLast(p2.removeFirst());
            p1.addLast(p1.removeFirst());
        }
    }

    public void player2Wins(){
        int n = rnd.nextInt(2);
        if(n == 1) {
            p2.addLast(p1.removeFirst());
            p2.addLast(p2.removeFirst());
        } else {
            p2.addLast(p2.removeFirst());
            p2.addLast(p1.removeFirst());
        }
    }

    public boolean isWinner(int count){
        boolean winner = false;
        if (p1.size() < count) {
            for (int a = 0; a < p1.size(); a++) {
                player2Wins();
            }
            winner = true;
        }
        if (p2.size() < count) {
            for (int a = 0; a < p2.size(); a++) {
                player1Wins();
            }
            winner = true;
        }
        return winner;
    }

    /* FUNCTIONS FOR BUILDING THE DECK, SHUFFLING, AND DEALING */
    public void buildDeck(){
        char [] suits = {'H', 'S', 'D', 'C'};
        int [] values = {2,3,4,5,6,7,8,9,10,11,12,13,14};
        for(int i=0;i<suits.length;i++){
            for(int k=0;k<values.length;k++){
                list.add(new Card(suits[i], values[k]));
            }
        }
    }

    public void shuffle(){
        for(int i=0;i<100000;i++){
            Card temp = list.remove(rnd.nextInt(51));
            list.add(rnd.nextInt(51), temp);
        }
    }

    public void deal() {
        while (!list.isEmpty()) {
            p1.add(list.removeFirst());
            if (!list.isEmpty()) {
                p2.add(list.removeFirst());
            }
        }
    }

    /* PRINTING LIST OF CARDS */
    public void printPlayer1(){
        for(int i=0;i<p1.size();i++) {
            System.out.print(p1.get(i).getSuit() + " ");
            System.out.println(p1.get(i).getValue());
        }
    }

    public void printPlayer2(){
        for(int i=0;i<p2.size();i++) {
            System.out.print(p2.get(i).getSuit() + " ");
            System.out.println(p2.get(i).getValue());
        }
    }
    public void printDeck(){
        for(int i=0;i<list.size();i++) {
            System.out.print(list.get(i).getSuit() + " ");
            System.out.println(list.get(i).getValue());
        }
    }
    /*  GETTERS AND SETTERS  */

    public int getP1winner(){
        return this.p1winner;
    }

    public int getP2winner(){
        return this.p2winner;
    }

    public int getTotalturns(){
        return this.totalturns;
    }
}
