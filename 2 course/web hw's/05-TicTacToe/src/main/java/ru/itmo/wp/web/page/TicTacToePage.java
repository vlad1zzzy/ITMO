package ru.itmo.wp.web.page;

import ru.itmo.wp.web.exception.RedirectException;
import ru.itmo.wp.web.page.TicTacToeUtil.Phases;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {

    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        if (state == null)
            newGame(request, view);
        else
            view.put("state", state);
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State();
        view.put("state", state);
        request.getSession().setAttribute("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        String cell = getCell(request);
        int row = cell.charAt(0) - '0';
        int col = cell.charAt(1) - '0';
        State state = (State) request.getSession().getAttribute("state");
        state.makeMove(row, col);
        throw new RedirectException(request.getRequestURI());
    }

    private String getCell(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            if (paramName.startsWith("cell")) {
                return paramName.substring(paramName.length() - 2);
            }
        }
        throw new RuntimeException("Invalid request for next move");
    }

    public static class State {
        private final String[][] cells;
        private Phases phase;
        private boolean crossesMove;
        private int filled;

        private static final int size = 3;
        private static final int inRowToWin = 3;

        private State() {
            this.cells = new String[size][size];
            this.phase = Phases.RUNNING;
            this.crossesMove = true;
            this.filled = 0;
        }

        private void makeMove(int row, int col) {
            if (!phase.equals(Phases.RUNNING) || cells[row][col] != null)
                return;

            cells[row][col] = crossesMove ? "X" : "O";
            filled++;

            int inDiagUpDown = 1 + counter(row, col, -1, 1) + counter(row, col, 1,-1);
            int inDiagDownUp = 1 + counter(row, col, 1, 1) + counter(row, col, -1,-1);
            int inLeftRight = 1 + counter(row, col, 1,0) + counter(row, col, -1,0);
            int inUpDown = 1 + counter(row, col, 0, 1) + counter(row, col, 0,-1);

            if (inDiagDownUp >= inRowToWin || inDiagUpDown >= inRowToWin
                    || inLeftRight >= inRowToWin || inUpDown >= inRowToWin) {
                phase = crossesMove ? Phases.WON_X : Phases.WON_O;
            }

            if (filled == size * size)
                phase = Phases.DRAW;

            invertCrossesMove();
        }

        private int counter(int row, int col, int x, int y){
            int curRow = row + y;
            int curCol = col + x;
            int count = 0;
            while (curRow >= 0 && curRow < size && curCol >= 0 && curCol < size
                    && cells[curRow][curCol] != null
                    && cells[curRow][curCol].equals(cells[row][col])) {
                count++;
                curRow += y;
                curCol += x;
            }
            return count;
        }

        public String[][] getCells() {
            return cells;
        }

        public Phases getPhase() {
            return phase;
        }

        private void changePhase(Phases nextPhase) {
            phase = nextPhase;
        }

        public int getSize() {
            return size;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        private void invertCrossesMove() {
            crossesMove = !crossesMove;
        }
    }
}
