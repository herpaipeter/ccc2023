package hu.herpaipeter.chess;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Board {

    private List<List<PositionType>> boardPositions = new ArrayList<>();
    private List<BoardField> figurePositions = new ArrayList<>();
    private List<List<Integer>> posAttackedCounter = new ArrayList<>();

    public Board(List<String> boardString) {
        parseBoard(boardString);
    }

    public int getRowCount() {
        return boardPositions.size();
    }

    public int getColCount() {
        return boardPositions.get(0).size();
    }

    private void parseBoard(List<String> boardString) {
        for (int row = 0; row < boardString.size(); row++) {
            ArrayList<PositionType> positionTypeLine = new ArrayList<>();
            ArrayList<Integer> attackLine = new ArrayList<>();
            for (int col = 0; col < boardString.get(row).length(); col++) {
                switch (boardString.get(row).charAt(col)) {
                    case '.' -> positionTypeLine.add(PositionType.ROOK_PLACE);
                    case 'x' -> positionTypeLine.add(PositionType.QUEEN_PLACE);
                }
                attackLine.add(0);
            }
            boardPositions.add(positionTypeLine);
            posAttackedCounter.add(attackLine);
        }
    }

    public boolean canPlace(int row, int col, PositionType type) {
        return isTheyPlace(row, col, type) && !isInAttack(row, col, type);
    }

    public void addFigure(int row, int col, PositionType type) {
        if (canPlace(row, col, type)) {
            placeFigure(row, col, type);
        }
    }

    private void placeFigure(int row, int col, PositionType type) {
        figurePositions.add(new BoardField(new Position(row, col), type));
        posAttackedCounter.get(row).set(col, 1);
        attackRow(row, col, 1);
        attackCol(row, col, 1);
        if (type == PositionType.QUEEN) {
            attackDiagonal(row, col, 1);
        }
    }

    private void attackDiagonal(int row, int col, int count) {
        int i = row - 1;
        int j = col - 1;
        for (; 0 <= i && 0 <= j; i--, j--) {
            Integer origVal = posAttackedCounter.get(i).get(j);
            posAttackedCounter.get(i).set(j, origVal + count);
        }

        i = row + 1;
        j = col - 1;
        for (; i < posAttackedCounter.size() && 0 <= j; i++, j--) {
            Integer origVal = posAttackedCounter.get(i).get(j);
            posAttackedCounter.get(i).set(j, origVal + count);
        }

        i = row - 1;
        j = col + 1;
        for (; 0 <= i && j < posAttackedCounter.get(i).size(); i--, j++) {
            Integer origVal = posAttackedCounter.get(i).get(j);
            posAttackedCounter.get(i).set(j, origVal + count);
        }

        i = row + 1;
        j = col + 1;
        for (; i < posAttackedCounter.size() && j < posAttackedCounter.get(i).size(); i++, j++) {
            Integer origVal = posAttackedCounter.get(i).get(j);
            posAttackedCounter.get(i).set(j, origVal + count);
        }
    }

    private void attackCol(int row, int col, int count) {
        for (int i = 0; i < posAttackedCounter.size(); i++) {
            Integer originalValue = posAttackedCounter.get(i).get(col);
            if (row != i)
                posAttackedCounter.get(i).set(col, originalValue + count);
        }
    }

    private void attackRow(int row, int col, int count) {
        for (int i = 0; i < posAttackedCounter.get(row).size(); i++) {
            Integer originalValue = posAttackedCounter.get(row).get(i);
            if (col != i)
                posAttackedCounter.get(row).set(i, originalValue + count);
        }
    }

    private boolean isInAttack(int row, int col, PositionType type) {
        return 0 < posAttackedCounter.get(row).get(col);
    }

    private boolean isTheyPlace(int row, int col, PositionType type) {
        return switch (boardPositions.get(row).get(col)) {
            case ROOK_PLACE -> type == PositionType.ROOK_PLACE || type == PositionType.ROOK;
            case QUEEN_PLACE -> type == PositionType.QUEEN_PLACE || type == PositionType.QUEEN;
            default -> throw new IllegalStateException();
        };
    }

    public List<String> toStringList() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < boardPositions.size(); i++) {
            String line = "";
            for (int j = 0; j < boardPositions.get(i).size(); j++) {
                int finalI = i;
                int finalJ = j;
                Optional<BoardField> figure = figurePositions.stream().filter(fp -> fp.position().row() == finalI && fp.position().col() == finalJ).findFirst();
                line += getSign(figure.isPresent() ? figure.get().type() : boardPositions.get(i).get(j));
            }
            result.add(line);
        }
        return result;
//        return boardPositions.stream()
//                .map(line -> line.stream().map(pos -> getSign(pos)).collect(Collectors.joining()))
//                .toList();
    }

    private String getSign(PositionType type) {
        return switch (type) {
            case ROOK -> "R";
            case QUEEN -> "Q";
            case ROOK_PLACE -> ".";
            case QUEEN_PLACE -> "x";
        };
    }

    public List<PossibilityCount> getQueenRowsAscendingByColCount() {
        List<PossibilityCount> results = new ArrayList<>();
        for (int i = 0; i  < boardPositions.size(); i++) {
            long cols = boardPositions.get(i).stream().filter(c -> c == PositionType.QUEEN_PLACE).count();
            results.add(new PossibilityCount(i, (int) cols));
        }
        results.sort(Comparator.comparingInt(PossibilityCount::count));
        return results;
    }

    public List<PossibilityCount> getRookRowsAscendingByColCount() {
        List<PossibilityCount> results = new ArrayList<>();
        for (int i = 0; i  < boardPositions.size(); i++) {
            long cols = boardPositions.get(i).stream().filter(c -> c == PositionType.ROOK_PLACE).count();
            results.add(new PossibilityCount(i, (int) cols));
        }
        results.sort(Comparator.comparingInt(PossibilityCount::count));
        return results;
    }
}
