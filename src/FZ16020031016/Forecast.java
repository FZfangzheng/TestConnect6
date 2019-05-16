package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;

import java.util.ArrayList;

public class Forecast {
    private PieceColor myChess;
    private Board board;
    private ArrayList<Board_Score> BSS;
    Forecast(Board board, PieceColor myChess){
        this.board = board;
        this.myChess = myChess;
        this.BSS = new ArrayList<>();
    }

    /**
     *
     * @param level 生成格局深度
     */
    public void generateBoard(int level){

    }
    public int[] alphabeta(){
        return null;
    }
}
