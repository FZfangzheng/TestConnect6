package g02.player;

import static core.board.PieceColor.*;
import static core.game.Move.*;

import java.util.Random;

import core.board.Board;
import core.board.PieceColor;
import core.game.Game;
import core.game.Move;
import core.player.AI;
import core.player.Player;


public class Lucker extends AI {

    @Override
    public Move findMove(Move opponentMove) {
    	Board b = board();
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
		return "Lucker";
	}
}
