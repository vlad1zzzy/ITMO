package mnk;

public final class Move {
    private final String row;
    private final String column;
    private final Cell value;

    Move(final String row, final String column, final Cell value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    String getRow() {
        return row;
    }

    String getColumn() {
        return column;
    }

    Cell getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "row = " + row + ", column = " + column + ", value = " + value;
    }
}

