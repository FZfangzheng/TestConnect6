package FZ16020031016;
import core.board.Board;
import core.board.PieceColor;

import java.util.ArrayList;

public class Utiles {
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
}
