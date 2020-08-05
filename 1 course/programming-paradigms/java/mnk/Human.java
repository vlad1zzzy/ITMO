package mnk;

import java.io.PrintStream;
import java.util.Scanner;

public class Human implements Player {

    private final PrintStream out;
    private final Scanner in;

    private Human(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    Human() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            final Move move = new Move(in.next(), in.next(), cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}
