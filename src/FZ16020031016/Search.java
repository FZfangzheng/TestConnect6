package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;

public class Search {
    /**
     * 必胜格局搜索
     * @param board
     * @param myChess
     * @return
     */
    static public Step mustWin(Board board, PieceColor myChess){
        return new Step(new int[]{-1,-1},new int[]{-1,-1});
    }

}
