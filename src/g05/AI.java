package g05;

import baseline.player.Board4AI;
import core.board.Board;
import core.board.PieceColor;
import core.game.Game;
import core.game.Move;

import java.util.ArrayList;
import java.util.Random;

import static core.board.PieceColor.EMPTY;
import static core.game.Move.SIDE;

public class AI extends core.player.AI {
    @Override
    public Move findMove(Move opponentMove) {
        if (opponentMove == null) {
            Move move = firstMove();
            board.makeMove(move);
            return move;
        }
        else {
            board.makeMove(opponentMove);
        }
        //��ȡ����
        PieceColor myColor = getColor();
        MyBoard myboard = new MyBoard(board);
        //myboard.draw();
        //board.draw();
        //Ԥ����֣������˱�ʤ��ȡ�ͦ��¼�֦,3��ʾ���Ǽ����������
        Forecast forecastBoard = new Forecast(myboard,myColor,3);
        //��ȡ�������
        int[] index = forecastBoard.generateStep();
        Move move = new Move(index[0], index[1]);
        board.makeMove(move);
        return move;

    }



    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "g05";
    }

    @Override
    public void playGame(Game game) {
        super.playGame(game);
        board = new Board4AI();
    }
    Board board = new Board();
}
