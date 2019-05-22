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
        //获取落子
        PieceColor myColor = getColor();
        Board b = board();

        //预测棋局
        Forecast forecastBoard = new Forecast(b,myColor);
        //获取最佳落子
        int[] index = forecastBoard.generateStep(3);
        return new Move(index[0], index[1]);



    }



    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "AI5";
    }
}
