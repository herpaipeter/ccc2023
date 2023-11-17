package hu.herpaipeter.chess;

import java.util.List;

public class QueensAndRooks {

    public List<String> placeFigures(List<String> boardString) {
        Board board = new Board(boardString);
//        for (int j = 0; j < board.getColCount(); j++) {
//            for (int i = 0; i < board.getRowCount(); i++) {
//                if (board.canPlace(i, j, PositionType.QUEEN)) {
//                    board.addFigure(i, j, PositionType.QUEEN);
//                }
//            }
//        }
        board.getQueenRowsAscendingByColCount().forEach(pc -> {
            for (int j = 0; j < board.getColCount(); j++) {
                if (board.canPlace(pc.roworcol(), j, PositionType.QUEEN)) {
                    board.addFigure(pc.roworcol(), j, PositionType.QUEEN);
                }
            }
        });
        board.getRookRowsAscendingByColCount().forEach(pc -> {
            for (int j = 0; j < board.getColCount(); j++) {
                if (board.canPlace(pc.roworcol(), j, PositionType.ROOK)) {
                    board.addFigure(pc.roworcol(), j, PositionType.ROOK);
                }
            }
        });
//        for (int i = 0; i < board.getRowCount(); i++) {
//            for (int j = 0; j < board.getColCount(); j++) {
//                if (board.canPlace(i, j, PositionType.ROOK)) {
//                    board.addFigure(i, j, PositionType.ROOK);
//                }
//            }
//        }
        return board.toStringList();
    }

}
