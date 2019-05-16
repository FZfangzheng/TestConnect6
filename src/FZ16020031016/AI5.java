package FZ16020031016;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;
import core.player.AI;

import java.util.ArrayList;
import java.util.Random;

import static core.board.PieceColor.EMPTY;
import static core.game.Move.SIDE;

public class AI5 extends AI {
    @Override
    public Move findMove(Move opponentMove) {
        Board b = board();
        PieceColor[] p = Road.createMap(b);
        ArrayList<Road> AR =  Road.findRoads(p,777);
        Random rand = new Random();
        while (true) {
            int index1 = rand.nextInt(SIDE * SIDE);
            int index2 = rand.nextInt(SIDE * SIDE);

            if (index1 != index2 && b.get(index1) == EMPTY && b.get(index2) == EMPTY)
                return new Move(index1, index2);
        }
    }



    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "AI5";
    }
}
