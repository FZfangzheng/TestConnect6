package g05.player;

import static core.board.PieceColor.*;
import static core.game.Move.*;

import java.util.Random;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;
import core.player.Player;

/* A player who plays by throwing dice*/
public class AI extends core.player.AI {

    @Override
    public Move findMove(Move opponentMove) {
    	//
		if (opponentMove == null) {
			Move move = firstMove();
			board.makeMove(move);
			return move;
		}
		else {
			board.makeMove(opponentMove);
		}
    	PieceColor myColor = getColor();
    	
    	Board4AI myboard = new Board4AI();
    	myboard.init();
    	for(int i = 0; i < 361; i++)
    	{
    		myboard.board[i / 19][ i % 19] = board.get(i);
    	}
    	
    	Search search = new Search();
    	Step bestStep = search.findBestMove(myColor, myboard);
    	
    	int index1 = bestStep.pos[0] * 19 + bestStep.pos[1];
    	int index2 = bestStep.pos[2] * 19 + bestStep.pos[3];
		Move move = new Move(index1, index2);
		board.makeMove(move);
		return move;
    }

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "G05-JoJo";  //组编号-为自己的AI所取的名字
	}
	Board board = new Board();
}
