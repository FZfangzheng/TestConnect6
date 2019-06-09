package Mychoise;

import core.board.Board;
import core.board.PieceColor;
import core.game.Move;
import core.player.AI;
import java.util.Scanner;


public class My extends AI {
    @Override
    public Move findMove(Move opponentMove) {
        //获取落子
        PieceColor myColor = getColor();
        Board b = board();
        Scanner input=new Scanner(System.in);
        char x = input.next().charAt(0);
        char y = input.next().charAt(0);

        char x1 = input.next().charAt(0);
        char y1 = input.next().charAt(0);

        return new Move(x, y,x1,y1);

    }



    @Override
    public String name() {
        // TODO Auto-generated method stub
        return "AI5";
    }
}
