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
        if (opponentMove == null) {
            Move move = firstMove();
            board.makeMove(move);
            return move;
        }
        else {
            board.makeMove(opponentMove);
        }
        //获取落子
        PieceColor myColor = getColor();
        MyBoard myboard = new MyBoard(board);
        //myboard.draw();
        //board.draw();
        //预测棋局，包括了必胜获取和αβ剪枝,3表示的是检索最深层数
        Forecast forecastBoard = new Forecast(myboard,myColor,3);
        //获取最佳落子
        int[] index = forecastBoard.generateStep();
        Move move = new Move(index[0], index[1]);
        board.makeMove(move);
        return move;

    }



    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "AI5";
    }

    Board board = new Board();
}
