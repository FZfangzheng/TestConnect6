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

	Board4AI board = new Board4AI();
	Search search = new Search();
	
    @Override
    public Move findMove(Move opponentMove) {
    	if (opponentMove == null) {
			Move move = firstMove();
			board.makeMove(move);
			return move;
		}
		else {
			board.makeMove(opponentMove);
			board.makeMove(opponentMove.index1()/ 19, opponentMove.index1() % 19, PieceColor.WHITE);
			board.makeMove(opponentMove.index2()/ 19, opponentMove.index2() % 19, PieceColor.WHITE);
			board.update();
		}
    	
    	PieceColor myColor = getColor();
    	 	
    	Step bestStep = search.findBestMove(myColor, board);
    	board.makeMove(bestStep.pos[0], bestStep.pos[1], myColor);
    	board.makeMove(bestStep.pos[2], bestStep.pos[3], myColor);
    	board.update();
    	
    	int index1 = bestStep.pos[0] * 19 + bestStep.pos[1];
    	int index2 = bestStep.pos[2] * 19 + bestStep.pos[3];
    	
    	return new Move(index1, index2);
    }
    
    public void makeMove(Step step, PieceColor type)
	{
		if (step.pos[0] != -1)
			board.makeMove(step.pos[0], step.pos[1], type);
		if (step.pos[2] != -1)
			board.makeMove(step.pos[2], step.pos[3], type);
	}
    
    public void move(Step step)
    {
    	Step bestMove = new Step();

    	PieceColor computerSide;
    	
    	if(getColor() == PieceColor.WHITE)
    		computerSide = PieceColor.BLACK;
    	else computerSide = PieceColor.WHITE;
    	
		if (step.pos[0] == -1){
			bestMove.pos[0] = 9;
			bestMove.pos[1] = 9;
			bestMove.pos[2] = -1;
			bestMove.pos[3] = -1;
			computerSide = PieceColor.WHITE;
		}
		else if (step.pos[2] == -1){
			makeMove(step, PieceColor.BLACK);

			for (int i = 0; i < 4; i++) {
				int x1 = step.pos[0] + Config.open[i][0];
				int y1 = step.pos[1] + Config.open[i][1];
				int x2 = step.pos[0] + Config.open[i][2];
				int y2 = step.pos[1] + Config.open[i][3];
				if (Utils.isLegal(x1, y1) && Utils.isLegal(x2, y2)) {
					bestMove.pos[0] = x1;
					bestMove.pos[1] = y1;
					bestMove.pos[2] = x2;
					bestMove.pos[3] = y2;
					break;
				}
			}
			
		}
		else{
			int type = board.getType(computerSide);
			type = type^1;
			if(type == 0)
				computerSide = PieceColor.BLACK;
			else 
				computerSide = PieceColor.WHITE;
			makeMove(step, computerSide);
			bestMove = search.findBestMove(computerSide, board);
		}

		makeMove(bestMove, computerSide);
		board.update();
    }
    
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "G05-JoJo";  //组编号-为自己的AI所取的名字
	}
}
