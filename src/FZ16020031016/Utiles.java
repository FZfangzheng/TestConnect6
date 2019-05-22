package FZ16020031016;
import core.board.Board;
import core.board.PieceColor;

import java.util.ArrayList;

public class Utiles {
    /**
     * 返回棋局评分
     * @param board 棋盘
     * @param myChess 执子颜色
     * @return 返回得分
     */
    static public int getValue(Board board, PieceColor myChess){
        Board_Score BS = new Board_Score(myChess);
        for(int i=0;i<361;i++){
            if(board.get(i)==PieceColor.EMPTY){
                ArrayList<Road> ar = Road.getRoads(board,i);
                for (Road r:ar
                     ) {
                    BS.setRoad(r);
                }
            }
        }
        BS.calcScore();
        return BS.getScore();
    }
    static public int[] stepToInt(Step step){
        return new int[]{1,1,1,1};
    }
}
