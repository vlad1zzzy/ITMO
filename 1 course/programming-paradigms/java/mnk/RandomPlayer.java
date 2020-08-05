package mnk;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;
    private int m;
    private int n;

    private RandomPlayer(final Random random) {
        this.random = random;
    }

    RandomPlayer(final int m, final int n) {
        this(new Random());
        this.m = m;
        this.n = n;
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            String r = Integer.toString(random.nextInt(n));
            String c = Integer.toString(random.nextInt(m));
            final Move move = new Move(r, c, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}
