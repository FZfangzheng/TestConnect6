package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;

import java.util.ArrayList;

public class Forecast {
    private PieceColor myChess;
    private Board board;
    private ArrayList<Board_Score> BSS;
    private int pos;
    private int left;
    Forecast(Board board, PieceColor myChess){
        this.board = board;
        this.myChess = myChess;
        this.left=361;
        this.BSS = new ArrayList<>();
    }

    /**
     *
     * @param level 生成格局深度
     */
    public void generateBoard(int level){

    }
    public int[] alphabeta(int depth, int alpha, int beta, int type, Board board){
        int ans = -1000000;
        int[] index = new int[2];
        if(depth<=0){

            index[0]=pos/19;
            index[1]=pos%19;

        }
        else{
            while(left>=0){
                left--;
                move(board);
                int value = -Utiles.getValue(board, this.myChess);
                unmove(board);
                if(value>ans){
                    ans = value;

                }
            }
            index[0]=ans/19;
            index[1]=ans%19;
        }
        return index;
    }
}
