package FZ16020031016;

import core.board.PieceColor;

public class ChessCount {
    static public int getMy(Road road, PieceColor myChess){
        if(myChess ==  PieceColor.WHITE)
            return road.getWf();
        else
            return road.getBf();
    }
    static  public  int getYour(Road road,PieceColor myChess){
        if(myChess ==  PieceColor.BLACK)
            return road.getWf();
        else
            return road.getBf();
    }
}
