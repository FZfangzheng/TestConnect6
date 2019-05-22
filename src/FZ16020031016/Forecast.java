package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import jdk.jshell.execution.Util;

import java.util.ArrayList;

public class Forecast {
    private PieceColor myChess;
    private Board board;
    private ArrayList<Board_Score> BSS;
    private int pos;
    private int left;
    private ArrayList<Step> as;
    private int F_depth;
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
    public int[] generateStep(int level){
        this.F_depth = level;
        Step step = Search.mustWin(this.board,this.myChess);
        //找不到必胜
        if (step.getFirstStep()[0] == -1){
            //进行αβ剪枝搜索最合适
            alphabeta(level,-10000000,10000000,myChess,board);
            return Utiles.stepToInt(this.as.get(0));
        }
        else{
            return Utiles.stepToInt(step);
        }
    }

    public int alphabeta(int depth, int alpha, int beta, PieceColor myChess, Board board) {
        if (depth==0){
            return Utiles.getValue(board,myChess);
        }
        else{
            Move move = new Move();
            move.generateStep(myChess,board);
            //第一轮走法，方便后面取出应该走的位置
            if(depth==this.F_depth){
                this.as = move.getAllStep();
            }
        }
    }
}
