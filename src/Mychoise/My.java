package Mychoise;

import FZ16020031016.MyBoard;
import core.board.Board;
import core.board.PieceColor;
import core.game.Move;
import core.player.AI;
import java.util.Scanner;


public class My extends AI {
    Board board = new Board();
    @Override
    public Move findMove(Move opponentMove) {
        //获取落子
        if (opponentMove == null) {
            Move move = firstMove();
            board.makeMove(move);
            return move;
        }
        else {
            board.makeMove(opponentMove);
        }
        PieceColor myColor = getColor();
        MyBoard myBoard = new MyBoard(board);
        myBoard.draw();
        Scanner input=new Scanner(System.in);
        char x = input.next().charAt(0);
        char y = input.next().charAt(0);

        char x1 = input.next().charAt(0);
        char y1 = input.next().charAt(0);

        Move move = new Move(x, y,x1,y1);
        board.makeMove(move);
        return move;

    }



    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "777";
    }
}
