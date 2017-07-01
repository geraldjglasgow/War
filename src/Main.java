/**
 * Created by Gerald Glasgow on 6/29/17.
 */
public class Main {
    public static void main(String[] args){
        int games = 100; // number of games of war to play
        Game game = new Game(games);
        System.out.println("In " + games + " games an average of " + game.getTotalturns()/games + " turns were taken.");
        System.out.println("Player 1 won " + game.getP1winner() + " times" + ", and Player 2 won " + game.getP2winner() + " times.");
        System.out.printf("%.1f " + " %.1f", (game.getP1winner()/(double)games)*100.0, (game.getP2winner()/(double)games)*100.0);
    }
}
