package mnk;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, String> SYMBOLS = Map.of(
            Cell.X, " X ",
            Cell.O, " O ",
            Cell.E, " _ "
    );

    private final Cell[][] cells;
    private Cell turn;
    private int m;
    private int n;
    private int k;
    private int count = 0;

    MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private int counter(Move move, int x, int y){
        int row = Integer.parseInt(move.getRow()) + y;
        int col = Integer.parseInt(move.getColumn()) + x;
        int count = 0;
        while (row >= 0 && row < n && col >= 0 && col < m && cells[row][col] == move.getValue()) {
            count++;
            row += y;
            col += x;
        }
        return count;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        count++;
        int currentRow = Integer.parseInt(move.getRow());
        int currentCol = Integer.parseInt(move.getColumn());
        cells[currentRow][currentCol] = move.getValue();

        int inDiagUpDown = 1 + counter(move, -1, 1) + counter(move, 1,-1);
        int inDiagDownUp = 1 + counter(move, 1, 1) + counter(move, -1,-1);
        int inLeftRight = 1 + counter(move, 1,0) + counter(move, -1,0);
        int inUpDown = 1 + counter(move, 0, 1) + counter(move, 0,-1);

        if (inDiagDownUp >= k || inDiagUpDown >= k || inLeftRight >= k || inUpDown >= k)
            return Result.WIN;

        if (count == m * n)
            return Result.DRAW;

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        try {
            int x = Integer.parseInt(move.getRow());
            int y = Integer.parseInt(move.getColumn());
            return 0 <= x && x < n
                    && 0 <= y && y < m
                    && cells[x][y] == Cell.E
                    && turn == getCell();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < m; i++) {
            sb.append(i).append("  ");
        }
        for (int r = 0; r < n; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < m; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
