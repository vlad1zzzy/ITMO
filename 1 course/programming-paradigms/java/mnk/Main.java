package mnk;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int result;
        Scanner mnk = new Scanner(System.in);
        System.out.println("Enter number of players: ");
        int players = mnk.nextInt();
        int[] characters = new int[players];
        for (int i = 0; i < players; i++) {
            System.out.println("Player" + (i+1) + "...");
            System.out.println("Choose character: human(1) or random(2)");
            characters[i] = mnk.nextInt();
        }
        System.out.println("Enter count of games: ");
        int c = mnk.nextInt();
        int[][] stat = new int[players][4];
        for (int i = 0; i < players; i++) {
            for (int j = 0; j < 4; j++) {
                stat[i][j] = 0;
            }
        }
        System.out.println("Enter board's size and count in row: ");
        int m = mnk.nextInt();
        int n = mnk.nextInt();
        int k = mnk.nextInt();
        System.out.println("START");
        for (int player1 = 0; player1 < players; player1++) {
            for (int player2 = player1 + 1; player2 < players; player2++) {
                for (int i = 0; i < c; i++) {
                    final Game game = new Game(false,
                           characters[player1] == 1 ? new Human() : new RandomPlayer(m,n),
                            characters[player2] == 1 ? new Human() : new RandomPlayer(m,n));
                    result = game.play(new MNKBoard(m, n, k));
                    Tournament tournament = new Tournament(player1, player2, stat, result);
                    tournament.addInStat();
                }
            }
        }
        System.out.println("Tournament end!");
        System.out.println("You can see results:");
        System.out.println("NO W  DR L SUM");
        for (int i = 0; i < players; i++) {
            System.out.print(i + 1 + "  ");
            for (int j = 0; j < 4; j++) {
                System.out.print(stat[i][j] + "  ");
            }
            System.out.println();
        }

    }
}
